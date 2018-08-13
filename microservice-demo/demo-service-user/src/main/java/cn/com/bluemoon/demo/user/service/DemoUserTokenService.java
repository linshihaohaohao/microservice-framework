package cn.com.bluemoon.demo.user.service;

public interface DemoUserTokenService {

	/**
	 * 创建token
	 * @param userId
	 * @return
	 */
	String createUserToken(Long userId);

	/**
	 * 注销token
	 * @param token
	 */
	void invalidateUserToken(String token);

	/**
	 * 用token换取userId（检查token有效性）
	 * @param token
	 * @return
	 */
	Long getUserIdByToken(String token);

}
