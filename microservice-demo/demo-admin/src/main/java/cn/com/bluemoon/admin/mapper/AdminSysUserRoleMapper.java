package cn.com.bluemoon.admin.mapper;

import java.util.List;
import java.util.Map;

import cn.com.bluemoon.admin.domain.model.AdminSysUserRole;
import cn.com.bluemoon.admin.domain.vo.SysUserRoleVo;
import tk.mybatis.mapper.common.Mapper;

public interface AdminSysUserRoleMapper extends Mapper<AdminSysUserRole> {
	/**
	 * 获取未授权的角色
	 * @param user_id
	 * @return
	 */
	List<Map<String, String>> getUnauthorizedRoleList(String user_id);

	/**
	 * 获取已授权的角色
	 * @param user_id
	 * @return
	 */
	List<Map<String, String>> getAuthorizedRoleList(String user_id);

	/**
	 * 插入或者更新授权记录
	 * 
	 * @param sysUserRole
	 */
	void replaceInto(SysUserRoleVo sysUserRole);

	/**
	 * <p>
	 * Description:查询已授权的角色,但是要排除由sysadmin授权的角色
	 * </p>
	 * 
	 * @param map
	 * @return
	 */
	List<Map<String, String>> getAuthorizedRoleListThroughFilter(
			Map<String, String> map);
}