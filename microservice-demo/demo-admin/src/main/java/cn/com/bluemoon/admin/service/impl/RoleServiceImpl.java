package cn.com.bluemoon.admin.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import com.github.pagehelper.PageHelper;

import cn.com.bluemoon.admin.domain.model.AdminSysRole;
import cn.com.bluemoon.admin.domain.vo.SysRoleVo;
import cn.com.bluemoon.admin.domain.vo.MallSysUserDelegRoleExampleVo;
import cn.com.bluemoon.admin.domain.vo.MallSysUserDelegRoleVo;
import cn.com.bluemoon.admin.domain.vo.SysUserRoleKeyVo;
import cn.com.bluemoon.admin.domain.vo.SysUserRoleVo;
import cn.com.bluemoon.admin.mapper.AdminSysRoleMapper;
import cn.com.bluemoon.admin.mapper.MallSysUserDelegRoleMapper;
import cn.com.bluemoon.admin.service.RoleMenuService;
import cn.com.bluemoon.admin.service.RoleService;
import cn.com.bluemoon.admin.service.UserRoleService;
import cn.com.bluemoon.utils.copy.BeanCopyUtils;

@Service("roleService")
@Transactional
public class RoleServiceImpl implements RoleService {
	@Autowired
	private AdminSysRoleMapper roleMapper;
	
	@Autowired
	private UserRoleService userRoleService;
	@Autowired
	private RoleMenuService roleMenuService;

	public List<SysRoleVo> getRoleListByExample(String roleCode,String roleName,int pageNo,int pageSize,String sortOrder)throws Exception {
		
		List<SysRoleVo> roleList = null;
		
		Example example = new Example(AdminSysRole.class);
		Criteria criteria = example.createCriteria();
		if (!StringUtils.isEmpty(roleCode)) {
			criteria.andLike("roleCode","%"+roleCode+"%");
		}
		if (!StringUtils.isEmpty(roleName)) {
			criteria.andLike("roleName","%"+roleName+"%");
		}
		int offset = pageNo * pageSize;
		RowBounds rowBounds = new RowBounds(offset, pageSize);
		List<AdminSysRole> list = roleMapper.selectByExampleAndRowBounds(example, rowBounds);
		
		if (list != null && list.size() > 0) {
			roleList = new ArrayList<SysRoleVo>();
			for (AdminSysRole temp : list) {
				SysRoleVo vo = BeanCopyUtils.copy(temp, SysRoleVo.class);
				vo.setRoleId(temp.getRoleId().toString());
				roleList.add(vo);
			}
		}
		return roleList;
	}
	
	public int getRoleListByExampleCount(String roleCode,String roleName)throws Exception {
		
		Example example = new Example(AdminSysRole.class);
		Criteria criteria = example.createCriteria();
		if (!StringUtils.isEmpty(roleCode)) {
			criteria.andLike("roleCode","%"+roleCode+"%");
		}
		if (!StringUtils.isEmpty(roleName)) {
			criteria.andLike("roleName","%"+roleName+"%");
		}
		return roleMapper.selectCountByExample(example);
	}

	@Override
	public boolean checkRoleExist(String roleCode) throws Exception {
		
		Example example = new Example(AdminSysRole.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("roleCode", roleCode);
		int count = roleMapper.selectCountByExample(example);
		
		return count > 0 ? true:false;
	}

	@Override
	public boolean addRole(SysRoleVo role) throws Exception {
		
		AdminSysRole entity = BeanCopyUtils.copy(role, AdminSysRole.class);
		int num = roleMapper.insert(entity);
		
		return num>0 ?true:false;
	}

	@Override
	public boolean updateRole(SysRoleVo role) throws Exception {
		
		AdminSysRole entity = BeanCopyUtils.copy(role, AdminSysRole.class);
		entity.setRoleId(Long.valueOf(role.getRoleId()));
		int num = roleMapper.updateByPrimaryKey(entity);
		
		return num>0 ?true:false;
	}

	@Override
	public void deleteRoleByIds(List<String> listIds) throws Exception{
		
		Example example = new Example(AdminSysRole.class);
		Criteria criteria = example.createCriteria();
		criteria.andIn("roleId", listIds);
		roleMapper.deleteByExample(example);
		
		//删除关系
		userRoleService.delUserRoleByRoleIds(listIds);
		roleMenuService.deleteRoleMenuByRoleIds(listIds);
	}

	
}
