package cn.com.bluemoon.redis.service;

import java.util.Set;

public interface RedisService {

	/**
	 * 设置缓存值
	 * @param key
	 * @param value
	 * @return
	 */
	boolean set(String key, String value);

	/**
	 * 设置缓存值并设置有效时间
	 * @param key
	 * @param value
	 * @param liveTime
	 * @return
	 */
	boolean set(String key, String value, long liveTime);

	/**
	 * 设置缓存对象
	 * @param key
	 * @param value
	 * @return
	 */
	boolean set(String key, Object value);

	/**
	 * 设置缓存对象并设置有效时间
	 * @param key
	 * @param value
	 * @param liveTime
	 * @return
	 */
	boolean set(String key, Object value, long liveTime);

	/**
	 * 查询缓存值
	 * @param key
	 * @return
	 */
	String get(String key);

	/**
	 * 查询缓存对象
	 * @param key
	 * @return
	 */
	Object getObj(String key);

	/**
	 * 通过正则匹配缓存keys
	 * @param pattern
	 * @return
	 */
	Set<String> getKeys(String pattern);

	/**
	 * 删除缓存
	 * @param key
	 * @return
	 */
	Long del(String key);

	/**
	 * 判断缓存是否存在
	 * @param key
	 * @return
	 */
	Boolean exists(String key);

}
