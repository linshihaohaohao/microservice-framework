package cn.com.bluemoon.admin.service;

import java.util.HashSet;
import java.util.List;

import cn.com.bluemoon.admin.domain.vo.MallMenuTreeVo;
import cn.com.bluemoon.admin.domain.vo.SysMenuVo;
import cn.com.bluemoon.admin.domain.vo.MenuTreeVo;
import cn.com.bluemoon.admin.domain.vo.MenuTreeVo.MenuTreeNode;



/**
 * 
 * <p>Title:AuthService.java</p>
 * <p>Description:权限角色相关的service</p>
 */
public interface AuthService {
	
	/**
	 * <p>Description:根据人员编号获取当前用的所有角色</p>
	 * AuthService.java
	 * return:List<MallSysRole>
	 */
	public HashSet<String> getRoleListByUserId(String userId) throws Exception;
	
	/**
	 * 
	 * <p>Description:根据人员编号获取当前所有角色的rolecode</p>
	 * AuthService.java
	 * return:String
	 */
	public String getRoleCodeList( String userId ) throws Exception;
	
	/**
	 * <p>Description:根绝角色编号和菜单的登记、父菜单ID获取菜单列表</p>
	 * AuthService.java
	 * return:MenuTree
	 */
	public List<SysMenuVo> getMenuTree(List<String> roles, int menuLevel, int parentsId) throws Exception;
	
	/**
	 * <p>Description:获取当前用户的菜单树</p>
	 * AuthService.java
	 * return:List<MenuTreeNode>
	 */
	public MenuTreeVo getUserMenuTree(String userId,boolean isShowMenu) throws Exception;
	
	/**
	 * <p>Description:菜单信息实体类转换为菜单节点树</p>
	 * AuthService.java
	 * return:MenuTreeNode
	 */
	public MenuTreeNode convertMenuTreeNode(SysMenuVo mallSysMenu) throws Exception;
	
	/**
	 * <p>Description:检查当前url权限是否是当前用户角色所拥有</p>
	 * AuthService.java
	 * return:boolean
	 */
	public boolean checkUrlAuth(HashSet<Integer> roles, String url) throws Exception;

    /**
     * 初始化wdTree结构
     * @param userId
     * @return
     * @throws Exception
     */
	public List<MallMenuTreeVo> queryMenuData(String userId)throws Exception;
	
}
