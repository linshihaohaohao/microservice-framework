package cn.com.bluemoon.admin.service;

import java.util.Date;
import java.util.List;

import cn.com.bluemoon.admin.domain.model.AdminSysLogInfo;



/**
 * 日志操作类
 * @author mij
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
