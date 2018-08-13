package cn.com.bluemoon.demo.user.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.bluemoon.demo.user.mapper.DemoUserMapper;
import cn.com.bluemoon.demo.user.model.DemoUser;
import cn.com.bluemoon.demo.user.service.DemoUserService;
import cn.com.bluemoon.utils.encrypt.MD5Implementor;

@Service
public class DemoUserServiceImpl implements DemoUserService{

	private static Logger log = (Logger) LoggerFactory.getLogger(DemoUserServiceImpl.class);
	
	@Autowired
	private DemoUserMapper userMapper;
	
	@Override
	@Transactional
	public Long addUser(DemoUser user) {
		String md5Password = MD5Implementor.MD5Encode(user.getPassword());
		user.setPassword(md5Password);
		user.setValidFlag(1);
		user.setSignInTime(new Date());
		
		int flag = userMapper.insert(user);
		if (flag > 0) {
			return user.getId();
		}
		return null;
	}
	
	@Override
	public DemoUser getUserById(Long userId) {
		return userMapper.selectByPrimaryKey(userId);
	}
	
	@Override
	public DemoUser getUserByAccount(String account) {
		DemoUser example = new DemoUser();
		example.setAccount(account);
		return userMapper.selectOne(example);
	}
	
	@Override
	public boolean checkPassword(DemoUser user, String password) {
		String md5Password = MD5Implementor.MD5Encode(password);
		if (null != user && user.getPassword().equalsIgnoreCase(md5Password)) {
			return true;
		}
		return false;
	}
	
	@Override
	@Transactional
	public boolean updateUserIntroduction(DemoUser user, String introduction) {
		if (null != user) {
			user.setIntroduction(introduction);
			int flag = userMapper.updateByPrimaryKey(user);
			if (flag > 0) {
				return true;
			}
		}
		return false;
	}
}
