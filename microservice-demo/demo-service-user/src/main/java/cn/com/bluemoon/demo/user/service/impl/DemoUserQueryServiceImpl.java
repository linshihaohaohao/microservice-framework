package cn.com.bluemoon.demo.user.service.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import tk.mybatis.mapper.util.StringUtil;
import cn.com.bluemoon.demo.user.common.dto.DemoUserQueryDto;
import cn.com.bluemoon.demo.user.mapper.DemoUserMapper;
import cn.com.bluemoon.demo.user.model.DemoUser;
import cn.com.bluemoon.demo.user.service.DemoUserQueryService;

@Service
public class DemoUserQueryServiceImpl implements DemoUserQueryService{

	private static Logger log = (Logger) LoggerFactory.getLogger(DemoUserQueryServiceImpl.class);
	
	@Autowired
	private DemoUserMapper userMapper;
	
	@Override
	public List<DemoUser> getListByPage(DemoUserQueryDto query) {
		Example example = new Example(DemoUser.class);
		Criteria criteria = example.createCriteria();
		if (StringUtil.isNotEmpty(query.getAccount())) {
			criteria.andLike("account", "%" + query.getAccount() + "%");
		}
		RowBounds rowBounds = new RowBounds(Integer.valueOf(query.getOffset()), Integer.valueOf(query.getPageSize()));
		return userMapper.selectByExampleAndRowBounds(example, rowBounds);
	}
	
	@Override
	public int getListCount(DemoUserQueryDto query) {
		Example example = new Example(DemoUser.class);
		Criteria criteria = example.createCriteria();
		if (StringUtil.isNotEmpty(query.getAccount())) {
			criteria.andLike("account", "%" + query.getAccount() + "%");
		}
		return userMapper.selectCountByExample(example);
	}
}
