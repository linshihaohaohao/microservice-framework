package cn.com.bluemoon.demo.user.service;

import java.util.List;

import cn.com.bluemoon.demo.user.common.dto.DemoUserQueryDto;
import cn.com.bluemoon.demo.user.model.DemoUser;

public interface DemoUserQueryService {

	/**
	 * 
	 * @param query
	 * @return
	 */
	public List<DemoUser> getListByPage(DemoUserQueryDto query);

	/**
	 * 
	 * @param query
	 * @return
	 */
	int getListCount(DemoUserQueryDto query);
}
