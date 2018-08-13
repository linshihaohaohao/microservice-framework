package cn.com.bluemoon.admin.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import cn.com.bluemoon.admin.domain.model.AdminSysRole;
import cn.com.bluemoon.admin.domain.model.AdminSysUserRole;
import cn.com.bluemoon.admin.domain.vo.SysRoleVo;
import cn.com.bluemoon.admin.domain.vo.SysUserRoleKeyVo;
import cn.com.bluemoon.admin.domain.vo.SysUserRoleVo;
import cn.com.bluemoon.admin.mapper.AdminSysRoleMapper;
import cn.com.bluemoon.admin.mapper.AdminSysUserRoleMapper;
import cn.com.bluemoon.admin.service.UserRoleService;
import cn.com.bluemoon.utils.copy.BeanCopyUtils;
import cn.com.bluemoon.utils.string.StringUtil;

@Service("userRoleService")
@Transactional
public class UserRoleServiceImpl implements UserRoleService {

	@Autowired
	private AdminSysRoleMapper roleMapper;
	@Autowired
	private AdminSysUserRoleMapper userRoleMapper;
	
	@Override
	public List<SysUserRoleVo> getUserRoleListByRoleId(String roleId) {
		
		List<SysUserRoleVo> userRoleList = null;
		Example example = new Example(AdminSysUserRole.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("roleId", roleId);
		List<AdminSysUserRole> list = userRoleMapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			userRoleList = new ArrayList<SysUserRoleVo>();
			for (AdminSysUserRole temp : list) {
				SysUserRoleVo vo = BeanCopyUtils.copy(temp, SysUserRoleVo.class);
				vo.setRoleId(temp.getRoleId().toString());
				userRoleList.add(vo);
			}
		}
		return userRoleList;
	}
	
	@Override
	public List<SysUserRoleVo> getUserRoleListByUserId(String userId) {
		
		List<SysUserRoleVo> userRoleList = null;
		Example example = new Example(AdminSysUserRole.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("userId", userId);
		List<AdminSysUserRole> list = userRoleMapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			userRoleList = new ArrayList<SysUserRoleVo>();
			for (AdminSysUserRole temp : list) {
				SysUserRoleVo vo = BeanCopyUtils.copy(temp, SysUserRoleVo.class);
				vo.setRoleId(temp.getRoleId().toString());
				userRoleList.add(vo);
			}
		}
		return userRoleList;
	}

	public SysUserRoleVo getUserRoleByRoleIdAndUserId(String roleId,String userId){
		
		Example example = new Example(AdminSysUserRole.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("userId", userId);
		criteria.andEqualTo("roleId", roleId);
		List<AdminSysUserRole> list = userRoleMapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			AdminSysUserRole temp = list.get(0);
			SysUserRoleVo vo = BeanCopyUtils.copy(temp, SysUserRoleVo.class);
			vo.setRoleId(temp.getRoleId().toString());
			return vo;
		}
		
		return null;
	}
	
	@Override
	public int delUserRole(String userId, String roleId) {
		
		Example example = new Example(AdminSysUserRole.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("userId", userId);
		criteria.andEqualTo("roleId", roleId);
		return userRoleMapper.deleteByExample(example);
	}
	
	@Override
	public int delUserRoleByRoleIds(List<String> roleIds) {
		Example example = new Example(AdminSysUserRole.class);
		Criteria criteria = example.createCriteria();
		criteria.andIn("roleId", roleIds);
		return userRoleMapper.deleteByExample(example);
	}

	@Override
	public int saveEntity(SysUserRoleVo entity) {
		if(StringUtil.isNotEmpty(entity.getUserId()) && StringUtil.isNotEmpty(entity.getRoleId())){
			if(StringUtil.isEmpty(entity.getRoleCode())){
				
				AdminSysRole role = roleMapper.selectByPrimaryKey(Long.valueOf(entity.getRoleId()));
				SysRoleVo vo = BeanCopyUtils.copy(role, SysRoleVo.class);
				
				entity.setRoleCode(vo.getRoleCode());
			}
			
			AdminSysUserRole record = BeanCopyUtils.copy(entity, AdminSysUserRole.class);
			record.setRoleId(Long.valueOf(entity.getRoleId()));
			return userRoleMapper.insert(record);
			
		}
		return 0;
	}

	@Override
	public int addUserRole(SysUserRoleVo rolePO) {
		AdminSysUserRole record = BeanCopyUtils.copy(rolePO, AdminSysUserRole.class);
		record.setRoleId(Long.valueOf(rolePO.getRoleId()));
		return userRoleMapper.insert(record);
	}

	@Override
	public int removeUserRole(String operatorId, String roleId) {
		SysUserRoleKeyVo key=new SysUserRoleKeyVo();
		key.setUserId(operatorId);
		key.setRoleId(Long.valueOf(roleId));
		return userRoleMapper.deleteByPrimaryKey(key);
	}
	
	@Override
	public int deleteUserRoleByOperatorId(String operatorId) throws Exception{
		Example example = new Example(AdminSysUserRole.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("userId", operatorId);
		return userRoleMapper.deleteByExample(example);
	}

	@Override
	public List<Map<String,String>> getAuthorizedRoleList(String userId) throws Exception {
		return userRoleMapper.getAuthorizedRoleList(userId);
	}

	@Override
	public List<Map<String, String>> getUnauthorizedRoleList(String userId) throws Exception {
		return userRoleMapper.getUnauthorizedRoleList(userId);
	}
	
	@Override
	public List<Map<String, String>> getAuthorizedRoleListThroughFilter(
			Map<String, String> map) {
		return userRoleMapper.getAuthorizedRoleListThroughFilter(map);
	}
}
