package cn.com.bluemoon.admin.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.bluemoon.admin.domain.vo.MallSysUserDelegRoleExampleVo;
import cn.com.bluemoon.admin.domain.vo.MallSysUserDelegRoleVo;
import cn.com.bluemoon.admin.domain.vo.SysUserRoleVo;
import cn.com.bluemoon.admin.mapper.MallSysUserDelegRoleMapper;
import cn.com.bluemoon.admin.service.UserDelegRoleService;

@Service("userDelegRoleService")
@Transactional
public class UserDelegRoleServiceImpl implements UserDelegRoleService{

//	@Autowired
//	private MallSysUserRoleMapper userRoleMapper;
	@Autowired
	private MallSysUserDelegRoleMapper userDelegRoleMapper;
	
	@Override
	public List<Map<String, String>> getAuthorizedRoleListForDelegation(
			String userId) {
		return userDelegRoleMapper.getAuthorizedRoleListForDelegation(userId);
	}

	@Override
	public List<Map<String, String>> getUnauthorizedRoleListForDelegation(
			String userId) {
		return userDelegRoleMapper.getUnauthorizedRoleListForDelegation(userId);
	}

	@Override
	public void addUserRoleForDelegation(MallSysUserDelegRoleVo rolePO) {
		userDelegRoleMapper.insert(rolePO);
	}

	@Override
	public void removeUserRoleForDelegation(String operatorId, String roleId) {
		MallSysUserDelegRoleVo key=new MallSysUserDelegRoleVo();
		key.setUserId(operatorId);
		key.setRoleId(roleId);
		userDelegRoleMapper.deleteByPrimaryKey(key);
	}

	@Override
	public List<Map<String, String>> getUnauthorizedRoleListForProxyAuthor(
			String userId, String currentUserId) {
		return userDelegRoleMapper.getUnauthorizedRoleListForProxyAuthor(userId,currentUserId);
	}

	@Override
	public void addRoleByExistDelegation(String operatorId) {
		MallSysUserDelegRoleExampleVo example = new MallSysUserDelegRoleExampleVo();
		MallSysUserDelegRoleExampleVo.Criteria criteria = example.createCriteria();
		criteria.andUserIdEqualTo(operatorId);
		int count = userDelegRoleMapper.countByExample(example);
//		if(count > 0){	//已拥有代理权限
//			MallSysUserRoleExampleVo exampleRole = new MallSysUserRoleExampleVo();
//			MallSysUserRoleExampleVo.Criteria criteriaRole = exampleRole.createCriteria();
//			criteriaRole.andUserIdEqualTo(operatorId);
//			criteriaRole.andRoleCodeEqualTo("mall_erp_perm_deleg");
//			int countRole = userRoleMapper.countByExample(exampleRole);
//			if(countRole < 1){	//还未赋予 下放权限的角色
//				MallSysUserRoleVo rolePO = new MallSysUserRoleVo();
//				rolePO.setCreateTime(new Date());
//				rolePO.setCreateUser("0");
//				rolePO.setRoleCode("mall_erp_perm_deleg");
//				rolePO.setRoleId("5122");
//				rolePO.setUserId(operatorId);
//				userRoleMapper.replaceInto(rolePO);
//			}
//		}else{	//无代理权限
//			MallSysUserRoleExampleVo exampleRole = new MallSysUserRoleExampleVo();
//			MallSysUserRoleExampleVo.Criteria criteriaRole = exampleRole.createCriteria();
//			criteriaRole.andUserIdEqualTo(operatorId);
//			criteriaRole.andRoleCodeEqualTo("mall_erp_perm_deleg");
//			int countRole = userRoleMapper.countByExample(exampleRole);
//			if(countRole > 0){	//存在下放角色权限
//				userRoleMapper.deleteByExample(exampleRole);
//			}
//		}
	}
}
