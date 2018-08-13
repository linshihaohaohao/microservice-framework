package cn.com.bluemoon.admin.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.util.StringUtil;

import com.google.common.collect.Lists;

import cn.com.bluemoon.admin.domain.vo.MallMenuTreeVo;
import cn.com.bluemoon.admin.domain.vo.SysMenuVo;
import cn.com.bluemoon.admin.domain.vo.SysUserRoleVo;
import cn.com.bluemoon.admin.domain.vo.MenuTreeVo;
import cn.com.bluemoon.admin.domain.vo.MenuTreeVo.MenuTreeNode;
import cn.com.bluemoon.admin.mapper.AdminSysMenuMapper;
import cn.com.bluemoon.admin.service.AuthService;
import cn.com.bluemoon.admin.service.UserRoleService;

/**
 * 
 * <p>Title:AuthServiceImpl.java</p>
 * <p>Description:角色权限相关接口的实现类</p>
 */
@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
	private UserRoleService userRoleService;
	
	@Autowired
	private AdminSysMenuMapper sysMenuMapper;
	
	@Override
	public HashSet<String> getRoleListByUserId(String userId)
			throws Exception {
		try{
			if( userId == null || "".equals(userId) ){
				return null;
			}else{			
				List<SysUserRoleVo> list = userRoleService.getUserRoleListByUserId(userId);
				if( null != list && list.size() > 0 ){
					HashSet<String> roleList = new HashSet<String>();
					for( int i = 0; i < list.size(); i++ ){
						roleList.add(list.get(i).getRoleId());
					}
					return roleList;
				}else {
					return null;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public String getRoleCodeList( String userId ){
		if( userId == null || "".equals(userId) ){
			return null;
		}else{
			List<SysUserRoleVo> list = userRoleService.getUserRoleListByUserId(userId);
			if( null != list && list.size() > 0 ){
				String roleCode = "";
				for( int i = 0; i < list.size(); i++ ){
					String code = list.get(i).getRoleCode();
					//如果当前参数中不包含该code
					if( roleCode.indexOf(code) == -1 ){
						roleCode += code + ",";
					}
				}
				//如果字符串最后一个字符是","，则去掉
				if( roleCode.endsWith(",")){
					roleCode.substring(0, roleCode.length()-1);
				}
				return roleCode;
			}else {
				return null;
			}
		}
	}

	@Override
	public List<SysMenuVo> getMenuTree(List<String> roles, int menuLevel, int parentsId) throws Exception {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("roles", roles);
		params.put("menuLevel", menuLevel);
		params.put("parentsId", parentsId);
		
		//不同应用区分菜单
		List<String> appCode=new ArrayList<String>();
		appCode.add("mall_wash");
		appCode.add("mall_common");
		params.put("appCode", appCode);
		List<SysMenuVo> treeList = sysMenuMapper.getMenuTree(params);
		if(treeList == null){
			treeList = new ArrayList<SysMenuVo>();
		}
		return treeList;
	}

    /**
     *
     * @param userId
     * @param isShowMenu  当true时 显示没有字应用页面的菜单  false 时不显示
     * @return
     * @throws Exception
     */
	@Override
	public MenuTreeVo getUserMenuTree(String userId,boolean isShowMenu) throws Exception {
		List<String> roles = null;
		if (StringUtil.isNotEmpty(userId)) {
			HashSet<String> roleSet = getRoleListByUserId(userId);
			if (null != roleSet) {
				roles = new ArrayList<String>();
				Iterator<String> roleList = roleSet.iterator();
				while( roleList.hasNext() ){
					roles.add(roleList.next());
				}
			}
		}
		//1.构建一颗空的菜单树
		MenuTreeVo tree = new MenuTreeVo();
		//2.先获取第一层所有的菜单信息
		List<SysMenuVo> treeFirst = getMenuTree(null, 1, 0);
		if ( treeFirst.size() == 0 ){
			return null;
		}else{
			for( SysMenuVo mallSysMenu : treeFirst ){
				//3.循环第一层菜单，获取是否有子菜单信息
				//第一层菜单，只有当第一层的菜单有子菜单是才会添加第一层的菜单信息到菜单树中
				int menuId = mallSysMenu.getMenuId();//第一层菜单编号
				MenuTreeNode menuTreeNode = new MenuTreeNode();
				menuTreeNode = convertMenuTreeNode(mallSysMenu);
				//获取都是应用功能的菜单
				List<SysMenuVo> treeSecond =	getMenuTree(roles, 2, menuId);
				if( treeSecond.size() > 0 ){
					//如果第一层菜单下面的菜单信息不为空
					//4.先添加第一层菜单信息到菜单树结构
					for( SysMenuVo mallSysMenu2 : treeSecond ){
						//获取每一个子菜单，循环在向下获取子孙菜单
						List<SysMenuVo> treeThird = getMenuTree(roles, 3, mallSysMenu2.getMenuId());
						MenuTreeNode menuTreeNodeSecond = convertMenuTreeNode(mallSysMenu2);
						if( treeThird.size() > 0 ){
							//如果第二层下面的菜单信息不为空
							//则添加第二层才菜单树信息
							for( SysMenuVo mallSysMenu3 : treeThird){
								//最下一层的菜单信息
								MenuTreeNode menuTreeNodeThird = convertMenuTreeNode(mallSysMenu3);
								//在第二层菜单中添加第三层的菜单信息
								menuTreeNodeSecond.addChildMenuTreeNode(menuTreeNodeThird);
							}
						}
						//在第一层的菜单中添加第二层的菜单信息
						menuTreeNode.addChildMenuTreeNode(menuTreeNodeSecond);
					}
				}
				//获取第二层是子菜单项，非应用功能的菜单
				List<SysMenuVo> treeNodeSecond = getMenuTree(null, 2, menuId);
				//是否有第二层子菜单并且有第三层应用功能菜单
				Boolean hasThirdNode = false;
				//如果第二层下面的跟菜单不为空
				if( treeNodeSecond.size() > 0 ){
					//向下获取第三层的菜单信息，如果第三层菜单信息不为空，则添加到菜单树
					for( SysMenuVo mallSysMenu2 : treeNodeSecond ){
						//获取第二层跟菜单的子菜单
						List<SysMenuVo> treeNodeThird = getMenuTree(roles, 3, mallSysMenu2.getMenuId());
						MenuTreeNode menuTreeNodeGenSecond = convertMenuTreeNode(mallSysMenu2);
						if( treeNodeThird.size() > 0 ){
							hasThirdNode = true;
							//如果第二层下面的菜单信息不为空
							//则添加第二层才菜单树信息
							for( SysMenuVo mallSysMenu3 : treeNodeThird){
								//最下一层的菜单信息
								MenuTreeNode menuTreeNodeGenThird = convertMenuTreeNode(mallSysMenu3);
								//在第二层菜单中添加第三层的菜单信息
								menuTreeNodeGenSecond.addChildMenuTreeNode(menuTreeNodeGenThird);
							}
							//在第一层的菜单中添加第二层的菜单信息
							menuTreeNode.addChildMenuTreeNode(menuTreeNodeGenSecond);
						}
					}
				}
				//当有而应用功能菜单或者三级子菜单的时候才添加菜单选项
                if(isShowMenu){
                    //最后将子菜单树结构添加到菜单数
                    tree.addMenuTreeRootNode(menuTreeNode);
                }else {
                    if( treeSecond.size() > 0 || hasThirdNode == true ){
                        //最后将子菜单树结构添加到菜单数
                        tree.addMenuTreeRootNode(menuTreeNode);
                    }
                }

			}
		}
		return tree;
	}

	public MenuTreeNode convertMenuTreeNode(SysMenuVo mallSysMenu)
			throws Exception {
		MenuTreeNode menuTreeNode = new MenuTreeNode();
		menuTreeNode.setMenuId(mallSysMenu.getMenuId());
		menuTreeNode.setMenuCode(mallSysMenu.getMenuCode());
		menuTreeNode.setMenuName(mallSysMenu.getMenuName());
		menuTreeNode.setMenuLabel(mallSysMenu.getMenuLabel());
		menuTreeNode.setIfleaf(Integer.parseInt(mallSysMenu.getIfleaf()));
		menuTreeNode.setMenuLevel(mallSysMenu.getMenuLevel());
		menuTreeNode.setDisplayOrder(mallSysMenu.getDisplayOrder());
		menuTreeNode.setFunctionPath(mallSysMenu.getFunctionPath());
		menuTreeNode.setFunctionDesc(mallSysMenu.getFunctionDesc());
		menuTreeNode.setMenuType(mallSysMenu.getMenuType());
		menuTreeNode.setMenuSeq(mallSysMenu.getMenuSeq());
		menuTreeNode.setParentsCode(mallSysMenu.getParentsCode());
		if( mallSysMenu.getParentsId() == null ){
			menuTreeNode.setParentsId("");
		}else{
			menuTreeNode.setParentsId(mallSysMenu.getParentsId().toString());
		}
		
		menuTreeNode.setAppCode(mallSysMenu.getAppCode());
		return menuTreeNode;
	}

	@Override
	public boolean checkUrlAuth(HashSet<Integer> roleSet, String url)
			throws Exception {
		if( roleSet.size() == 0 ){
			return false;
		}
		if( url == null || "".equals(url) ){
			return false;
		}
		List<Integer> roles = new ArrayList<Integer>();
		//封装菜单树
		Iterator<Integer> roleList = roleSet.iterator();
		while( roleList.hasNext() ){
			roles.add(roleList.next());
		}
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("roles", roles);
		params.put("functionPath", url);
		/*//int count = mallSysMenuMapper.checkUrlAuth(params);
		if( count > 0 ){
			return true;
		}*/
		return false;
	}

    @Override
    public List<MallMenuTreeVo> queryMenuData(String userId) throws Exception {
        List<MallMenuTreeVo> mallMenuTreeVoJson = Lists.newArrayList();
        List<MallMenuTreeVo> mallMenuTreeJson = Lists.newArrayList();
        List<MenuTreeVo.MenuTreeNode> treeNodes = null;

        MenuTreeVo tree = getUserMenuTree(userId,true);
        if( tree != null ){
            treeNodes = tree.getMenuTreeRootNodeList();
            for(MenuTreeVo.MenuTreeNode menuTreeNode :treeNodes){
                List<MallMenuTreeVo> mallMenuTreeVos = Lists.newArrayList();
                MallMenuTreeVo mallMenuTreeVo = new MallMenuTreeVo();

                initMallMenuTree(mallMenuTreeVo,menuTreeNode);

                if (menuTreeNode.getChildrenMenuTreeNodeList() != null && menuTreeNode.getChildrenMenuTreeNodeList().size()> 0){
                    mallMenuTreeVo.setHasChildren(true);
                    for(MenuTreeVo.MenuTreeNode menuTreeNode1  : menuTreeNode.getChildrenMenuTreeNodeList()){
                        MallMenuTreeVo mallMenuTreeVo1 = new MallMenuTreeVo();

                        initMallMenuTree(mallMenuTreeVo1,menuTreeNode1);

                        mallMenuTreeVo1.setHasChildren(false);
                        mallMenuTreeVo1.setChildNodes(Lists.<MallMenuTreeVo>newArrayList());
                        mallMenuTreeVos.add(mallMenuTreeVo1);
                    }
                    mallMenuTreeVo.setChildNodes(mallMenuTreeVos);
                }
                mallMenuTreeVoJson.add(mallMenuTreeVo);
            }
        }
        //获得得顶级菜单
        List<SysMenuVo> treeFirst = getMenuTree(null, 0, -1);
        MallMenuTreeVo mallMenuTreeVo = new MallMenuTreeVo();
        if(mallMenuTreeVoJson.size() > 0){
            SysMenuVo mallSysMenuVo =treeFirst.get(0);
            MenuTreeNode menuTreeNode = new MenuTreeNode();
            menuTreeNode = convertMenuTreeNode(mallSysMenuVo);
            initMallMenuTree(mallMenuTreeVo,menuTreeNode);
            mallMenuTreeVo.setHasChildren(true);
            mallMenuTreeVo.setImg("fa fa-desktop");
            mallMenuTreeVo.setChildNodes(mallMenuTreeVoJson);
        }
        mallMenuTreeJson.add(mallMenuTreeVo);
        return  mallMenuTreeJson;
    }

    private void initMallMenuTree(MallMenuTreeVo mallMenuTreeVo,MenuTreeVo.MenuTreeNode menuTreeNode){
        mallMenuTreeVo.setId(menuTreeNode.getMenuId()+"");
        mallMenuTreeVo.setText(menuTreeNode.getMenuLabel());
        mallMenuTreeVo.setValue(menuTreeNode.getMenuId()+"");
        mallMenuTreeVo.setParentnodes(menuTreeNode.getParentsId());
        mallMenuTreeVo.setShowcheck(false);
        mallMenuTreeVo.setIsexpand(true);
        mallMenuTreeVo.setComplete(true);
    }


}
