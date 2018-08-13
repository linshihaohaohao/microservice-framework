package cn.com.bluemoon.admin.service;

import java.util.List;
import java.util.Map;

import cn.com.bluemoon.admin.domain.vo.SysUserRoleVo;


/**
 * 
 * 用户角色接口类
 *
 */
public interface UserRoleService {
	
	/**
	 * 通过角色ID获取角色用户
	 * @param roleId
	 * @return
	 */
	public List<SysUserRoleVo> getUserRoleListByRoleId(String roleId);
	
	/**
	 * 通过用户ID获取角色用户
	 * @param userId
	 * @return
	 */
	public List<SysUserRoleVo> getUserRoleListByUserId(String userId);
	
	/**
	 * 通过角色ID获取角色用户
	 * @param roleId,userId
	 * @return
	 */
	public SysUserRoleVo getUserRoleByRoleIdAndUserId(String roleId,String userId);
	
	/**
	 * 通过用户ID和角色ID删除绑定关系
	 * @param userId
	 * @param roleId
	 * @return
	 */
	public int delUserRole(String userId, String roleId);
	
	public int delUserRoleByRoleIds(List<String> roleIds);
	
	/**
	 * 保存角色人员
	 * @param entity
	 * @return
	 */
	public int saveEntity(SysUserRoleVo entity);
	
	/**
	 * <p>DEscription:查询已授权给用户的角色</p>
	 * @param operatorId 
	 * @throws Exception
	 */
	public List<Map<String,String>>  getAuthorizedRoleList(String userId) throws Exception;
	/**
	 * <p>DEscription:查询未授权给用户的角色</p>
	 * @param operatorId 
	 * @throws Exception
	 */
	public List<Map<String,String>>  getUnauthorizedRoleList(String userId) throws Exception;
	
	/**
	 * <p>Description:查询已授权的角色,但是要排除由sysadmin授权的角色</p>
	 * @param map
	 * @return
	 */
	public List<Map<String, String>> getAuthorizedRoleListThroughFilter(
			Map<String, String> map);

	public int addUserRole(SysUserRoleVo rolePO);

	public int removeUserRole(String operatorId, String roleId);
	
	public int deleteUserRoleByOperatorId(String operatorId) throws Exception;

	

	
}
