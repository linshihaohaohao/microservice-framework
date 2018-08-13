package cn.com.bluemoon.demo.user.service;

import cn.com.bluemoon.demo.user.model.DemoUser;

public interface DemoUserService {

	/**
	 * 添加用户
	 * @param user
	 * @return
	 */
	Long addUser(DemoUser user);

	/**
	 * 用id查找用户
	 * @param userId
	 * @return
	 */
	DemoUser getUserById(Long userId);

	/**
	 * 用账号查找用户
	 * @param account
	 * @return
	 */
	DemoUser getUserByAccount(String account);

	/**
	 * 校验密码
	 * @param user
	 * @param password
	 * @return
	 */
	boolean checkPassword(DemoUser user, String password);

	/**
	 * 更新用户个人介绍
	 * @param user
	 * @param introduction
	 * @return
	 */
	boolean updateUserIntroduction(DemoUser user, String introduction);

}
