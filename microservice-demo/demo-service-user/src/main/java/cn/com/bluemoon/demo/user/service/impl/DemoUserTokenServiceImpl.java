package cn.com.bluemoon.demo.user.service.impl;

import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.bluemoon.demo.user.mapper.DemoUserTokenMapper;
import cn.com.bluemoon.demo.user.model.DemoUserToken;
import cn.com.bluemoon.demo.user.service.DemoUserTokenService;
import cn.com.bluemoon.redis.service.RedisService;
import cn.com.bluemoon.utils.encrypt.MD5Implementor;

@Service
public class DemoUserTokenServiceImpl implements DemoUserTokenService {

	private static Logger log = (Logger) LoggerFactory.getLogger(DemoUserTokenServiceImpl.class);

	@Autowired
	private DemoUserTokenMapper tokenMapper;
	@Autowired
	private RedisService redisService;

	private static final String TOKEN_KEY = "demo_user_token";
	// token有效小时数
	private static final int TOKEN_LIVE_HOURS = 24 * 7;
	
	@Transactional
	@Override
	public String createUserToken(Long userId) {
		Date now = new Date();
		String token = MD5Implementor.MD5Encode(userId + now.getTime() + "");
		
		DemoUserToken tokenObj = new DemoUserToken();
		tokenObj.setUserId(userId);
		tokenObj.setToken(token);
		tokenObj.setCreateTime(now);
		tokenObj.setLastRefreshTime(now);
		tokenObj.setValidFlag(1);
		
		int flag = tokenMapper.insert(tokenObj);
		if (flag > 0) {
			//token入库后，放入redis控制时效
			redisService.set(TOKEN_KEY + token, tokenObj, 60*60*TOKEN_LIVE_HOURS);
			return token;
		}
		return null;
	}
	
	@Transactional
	@Override
	public void invalidateUserToken(String token) {
		DemoUserToken tokenObj = this.getValidUserToken(token);
		if (null != tokenObj) {
			tokenObj.setValidFlag(0);
			int flag = tokenMapper.updateByPrimaryKey(tokenObj);
			if (flag > 0) {
				redisService.del(TOKEN_KEY + tokenObj.getToken());
			}
		}
	}
	
	@Override
	public Long getUserIdByToken(String token) {
		DemoUserToken tokenObj = this.getValidUserToken(token);
		if (null != tokenObj) {
			this.refreshUserToken(tokenObj);
			return tokenObj.getUserId();
		}
		return null;
	}
	
	private DemoUserToken findUserTokenFromRedis(String token) {
		DemoUserToken tokenObj = null;	
		try {
			Object obj = redisService.getObj(TOKEN_KEY + token);
			if (null != obj) {
				tokenObj = (DemoUserToken) obj;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return tokenObj;
	}
	
	public DemoUserToken getValidUserToken(String token) {
		//先从redis中查找
		DemoUserToken tokenObj = this.findUserTokenFromRedis(token);	
		if (null != tokenObj)
			return tokenObj;
		
		//redis中查找不到，则从数据库查找
		DemoUserToken example = new DemoUserToken();
		example.setToken(token);
		example.setValidFlag(1);
		tokenObj = tokenMapper.selectOne(example);
		if (null != tokenObj) {
			//将最后刷新时间+刷新有效时间与当前时间作对比
			Calendar calendar=Calendar.getInstance();   
			calendar.setTime(tokenObj.getLastRefreshTime()); 
			calendar.add(Calendar.HOUR, TOKEN_LIVE_HOURS);
			Date validEndTime = calendar.getTime();
			if (validEndTime.before(new Date())) {
				return null;
			}
		}
		return tokenObj;
	}
	
	@Transactional
	public void refreshUserToken(DemoUserToken tokenObj) {
		tokenObj.setLastRefreshTime(new Date());
		int flag = tokenMapper.updateByPrimaryKey(tokenObj);
		if (flag > 0) {
			//token入库后，刷新redis控制时效
			redisService.set(TOKEN_KEY + tokenObj.getToken(), tokenObj, 60*60*TOKEN_LIVE_HOURS);
		}	
	}
}
