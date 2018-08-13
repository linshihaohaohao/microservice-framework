package cn.com.bluemoon.admin.service;

import java.util.List;
import java.util.Map;

import cn.com.bluemoon.admin.domain.vo.SysRoleVo;
import cn.com.bluemoon.admin.domain.vo.MallSysUserDelegRoleVo;
import cn.com.bluemoon.admin.domain.vo.SysUserRoleVo;

/**
 * 
 * 角色接口类
 *
 */
public interface RoleService {
	
	/**
	 * <p>Description:根据查询条件查询角色信息集合</p>
	 * AuthService.java
	 * @param roleName 
	 * @param roleCode 
	 * @return List<MallSysRole>
	 * @throws Exception 
	 */
	public List<SysRoleVo> getRoleListByExample(String roleCode, String roleName,int pageNo,int pageSize,String sortOrder) throws Exception;
	
	/**
	 * <p>Description:根据查询条件查询角色信息集合总数</p>
	 * AuthService.java
	 * @param roleName 
	 * @param roleCode 
	 * @return List<MallSysRole>
	 * @throws Exception 
	 */
	public int getRoleListByExampleCount(String roleCode, String roleName) throws Exception;
	
	/**
	 * <p>Description:根据角色编码,验证角色是否存在 </p>
	 * AuthService.java
	 * @param roleCode 
	 * @return boolean
	 * @throws Exception 
	 */
	public boolean checkRoleExist(String roleCode) throws Exception;
	
	/**
	 * <p>Description:新增角色 </p>
	 * @param role
	 * @return
	 * @throws Exception
	 */
	public boolean addRole(SysRoleVo role) throws Exception;
	
	/**
	 * <p>Description:新增角色 </p>
	 * @param role
	 * @return
	 * @throws Exception
	 */
	public boolean updateRole(SysRoleVo role) throws Exception;
	
	/**
	 * <p>DEscription:删除指定角色(物理删除)</p>
	 * @param listIds
	 * @throws Exception
	 */
	public void deleteRoleByIds(List<String> listIds) throws Exception;
}
