package cn.com.bluemoon.admin.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.bluemoon.admin.domain.model.AdminSysLogInfo;
import cn.com.bluemoon.admin.mapper.AdminSysLogInfoMapper;
import cn.com.bluemoon.admin.service.LogService;


@Service(value="logService")
public class LogServiceImpl implements LogService {
	
	@Autowired
	private AdminSysLogInfoMapper sysLogInfoMapper;
	
	protected Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public int saveSysOperateLog(AdminSysLogInfo logInfo) {
		return sysLogInfoMapper.insert(logInfo);
	}
}
