package cn.com.bluemoon.admin.service;

import cn.com.bluemoon.admin.domain.model.AdminSysLogInfo;



/**
 * 日志操作类
 * @author linshihao
 *
 */
public interface LogService {

	/**
	 * 保存操作日志
	 * @param logInfo
	 * @return
	 */
	public int saveSysOperateLog(AdminSysLogInfo logInfo);

	
}
