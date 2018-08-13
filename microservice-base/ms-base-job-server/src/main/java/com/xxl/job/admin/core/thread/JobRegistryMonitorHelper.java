package com.xxl.job.admin.core.thread;

import com.xxl.job.admin.core.enums.ExecutorFailStrategyEnum;
import com.xxl.job.admin.core.model.XxlJobGroup;
import com.xxl.job.admin.core.model.XxlJobInfo;
import com.xxl.job.admin.core.model.XxlJobRegistry;
import com.xxl.job.admin.core.route.ExecutorRouteStrategyEnum;
import com.xxl.job.admin.core.schedule.XxlJobDynamicScheduler;
import com.xxl.job.core.biz.ExecutorBiz;
import com.xxl.job.core.biz.model.JobRegistryParam;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.enums.ExecutorBlockStrategyEnum;
import com.xxl.job.core.enums.RegistryConfig;
import com.xxl.job.core.glue.GlueTypeEnum;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * job registry instance
 * @author xuxueli 2016-10-02 19:10:24
 */
public class JobRegistryMonitorHelper {
	private static Logger logger = LoggerFactory.getLogger(JobRegistryMonitorHelper.class);

	private static JobRegistryMonitorHelper instance = new JobRegistryMonitorHelper();
	public static JobRegistryMonitorHelper getInstance(){
		return instance;
	}

	private Thread registryThread;
	private volatile boolean toStop = false;
	public void start(){
		registryThread = new Thread(new Runnable() {
			@Override
			public void run() {
				while (!toStop) {
					try {
						// auto registry group
						List<XxlJobGroup> groupList = XxlJobDynamicScheduler.xxlJobGroupDao.findByAddressType(0);
						if (CollectionUtils.isNotEmpty(groupList)) {

							// remove dead address (admin/executor)
							XxlJobDynamicScheduler.xxlJobRegistryDao.removeDead(RegistryConfig.DEAD_TIMEOUT);

							// fresh online address (admin/executor)
							HashMap<String, List<String>> appAddressMap = new HashMap<String, List<String>>();
							List<XxlJobRegistry> list = XxlJobDynamicScheduler.xxlJobRegistryDao.findAll(RegistryConfig.DEAD_TIMEOUT);
							if (list != null) {
								for (XxlJobRegistry item: list) {
									if (RegistryConfig.RegistType.EXECUTOR.name().equals(item.getRegistryGroup())) {
										String appName = item.getRegistryKey();
										List<String> registryList = appAddressMap.get(appName);
										if (registryList == null) {
											registryList = new ArrayList<String>();
										}

										if (!registryList.contains(item.getRegistryValue())) {
											registryList.add(item.getRegistryValue());
										}
										appAddressMap.put(appName, registryList);
									}
								}
							}

							// fresh group address
							for (XxlJobGroup group: groupList) {
								List<String> registryList = appAddressMap.get(group.getAppName());
								String addressListStr = null;
								if (CollectionUtils.isNotEmpty(registryList)) {
									Collections.sort(registryList);
									addressListStr = StringUtils.join(registryList, ",");
								}
								group.setAddressList(addressListStr);
								XxlJobDynamicScheduler.xxlJobGroupDao.update(group);
							}
							
							//auto registry job
							for (XxlJobGroup group: groupList) {
								List<String> registryList = appAddressMap.get(group.getAppName());
								if (null != registryList) {
									for (String address : registryList) {
										ExecutorBiz executorBiz = XxlJobDynamicScheduler.getExecutorBiz(address);
										ConcurrentHashMap<String, JobRegistryParam> jobHandlers = executorBiz.getJobHandlers();
										for (JobRegistryParam jobHandler : jobHandlers.values()) {
											List<XxlJobInfo> result = XxlJobDynamicScheduler.xxlJobInfoDao.getJobsByGroupAndHandler(group.getId(), jobHandler.getName());
											if (null == result || result.size() < 1) {
												// add in db
												XxlJobInfo job = new XxlJobInfo();
												job.setJobGroup(group.getId());
												job.setJobCron(jobHandler.getCron());
												job.setJobDesc(jobHandler.getDesc());
												job.setAuthor(jobHandler.getOwner());
												job.setAlarmEmail(jobHandler.getEmail());
												job.setExecutorHandler(jobHandler.getName());
												
												job.setExecutorBlockStrategy(ExecutorBlockStrategyEnum.SERIAL_EXECUTION.name());
												job.setExecutorFailStrategy(ExecutorFailStrategyEnum.FAIL_ALARM.name());
												job.setExecutorRouteStrategy(ExecutorRouteStrategyEnum.FAILOVER.name());
												job.setGlueType(GlueTypeEnum.BEAN.name());
												XxlJobDynamicScheduler.xxlJobInfoDao.save(job);
												
												// add in quartz
												if (job.getId() > 0) {
													String qz_group = String.valueOf(job.getJobGroup());
											        String qz_name = String.valueOf(job.getId());
											        try {
											            XxlJobDynamicScheduler.addJob(qz_name, qz_group, job.getJobCron());
											            XxlJobDynamicScheduler.pauseJob(qz_name, qz_group);
											        } catch (SchedulerException e) {
											            logger.error(e.getMessage(), e);
											            try {
											            	XxlJobDynamicScheduler.xxlJobInfoDao.delete(job.getId());
											                XxlJobDynamicScheduler.removeJob(qz_name, qz_group);
											            } catch (SchedulerException e1) {
											                logger.error(e.getMessage(), e1);
											            }
											        }
												}
											}
										}
									}
								}		
							}
														
						}
					} catch (Exception e) {
						logger.error("job registry instance error:{}", e);
					}
					try {
						TimeUnit.SECONDS.sleep(RegistryConfig.BEAT_TIMEOUT);
					} catch (InterruptedException e) {
						logger.error("job registry instance error:{}", e);
					}
				}
			}
		});
		registryThread.setDaemon(true);
		registryThread.start();
	}

	public void toStop(){
		toStop = true;
		// interrupt and wait
		registryThread.interrupt();
		try {
			registryThread.join();
		} catch (InterruptedException e) {
			logger.error(e.getMessage(), e);
		}
	}

}
