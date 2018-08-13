package cn.com.bluemoon.admin.service;

import java.util.List;

import cn.com.bluemoon.admin.domain.vo.SysRoleMenuVo;

public interface RoleMenuService {

	public String getMenuListByRoleId(String roleId);
	public void deleteRoleMenuByKey(String roleId, String menuId);
	public void deleteRoleMenuByRoleId(String roleId);
	public void deleteRoleMenuByRoleIds(List<String> roleIds);
	public void saveRoleMenu(SysRoleMenuVo roleMenu);
	public void deleteRoleMenuByMenuId(String menuId);
	
}
