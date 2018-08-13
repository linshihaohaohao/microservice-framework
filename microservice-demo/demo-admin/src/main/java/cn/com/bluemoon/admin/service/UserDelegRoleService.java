package cn.com.bluemoon.admin.service;

import java.util.List;
import java.util.Map;

import cn.com.bluemoon.admin.domain.vo.MallSysUserDelegRoleVo;

/**
 *	用户代理角色
 */
public interface UserDelegRoleService {

	/**
	 * 查询已赋予授权代理给当前用户的角色
	 * @param operatorId
	 * @return
	 */
	public List<Map<String, String>> getAuthorizedRoleListForDelegation(
			String operatorId);
	
	/**
	 * 查询未赋予授权代理给当前用户的角色
	 * @param operatorId
	 * @return
	 */
	public List<Map<String, String>> getUnauthorizedRoleListForDelegation(
			String operatorId);
	
	/**
	 * 赋予授权代理某角色给当前用户
	 * @param rolePO
	 */
	public void addUserRoleForDelegation(MallSysUserDelegRoleVo rolePO);
	
	/**
	 * 取消赋予授权代理某角色给当前用户
	 * @param operatorId
	 * @param roleId
	 */
	public void removeUserRoleForDelegation(String operatorId, String roleId);
	
	/**
	 * 查询代理授权 可以授权但是还没有授权的角色
	 * @param operatorId
	 * @param currentUserId
	 * @return
	 */
	public List<Map<String, String>> getUnauthorizedRoleListForProxyAuthor(
			String userId, String currentUserId);
	
	/**
	 * 根据该用户是否有代理授权，给该用户新增下放权限的角色,或者取消下放角色权限
	 * @param operatorId
	 */
	public void addRoleByExistDelegation(String operatorId);
}
