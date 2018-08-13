package cn.com.bluemoon.admin.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import cn.com.bluemoon.admin.domain.model.AdminSysMenu;
import cn.com.bluemoon.admin.domain.model.AdminSysRoleMenu;
import cn.com.bluemoon.admin.domain.vo.SysMenuVo;
import cn.com.bluemoon.admin.domain.vo.SysRoleMenuKeyVo;
import cn.com.bluemoon.admin.domain.vo.SysRoleMenuVo;
import cn.com.bluemoon.admin.mapper.AdminSysMenuMapper;
import cn.com.bluemoon.admin.mapper.AdminSysRoleMenuMapper;
import cn.com.bluemoon.admin.service.MenuService;
import cn.com.bluemoon.admin.service.RoleMenuService;
import cn.com.bluemoon.admin.utils.IAppConstants;
import cn.com.bluemoon.admin.utils.IconCls;
import cn.com.bluemoon.utils.copy.BeanCopyUtils;
import cn.com.bluemoon.utils.string.StringUtil;

@Service("menuService")
@Transactional
public class MenuServiceImpl implements MenuService {
	
	@Autowired
	private AdminSysMenuMapper sysMenuMapper;
	@Autowired
	private RoleMenuService roleMenuService;
	
	

	@Override
	public int addMenu(SysMenuVo po) {
		AdminSysMenu entity = new AdminSysMenu();
		BeanCopyUtils.copy(po, entity);
		sysMenuMapper.insert(entity);
		return entity.getMenuId().intValue();
	}
	
	@Override
	public List<SysMenuVo> queryMenus(String menuType, String parentId) {
		
		Example example = new Example(AdminSysMenu.class);
		Criteria criteria = example.createCriteria();
		if(!StringUtils.isEmpty(menuType)){
			criteria.andEqualTo("menuType", menuType);
		}
		if(!StringUtils.isEmpty(parentId)){
			criteria.andEqualTo("parentId", parentId);
		}
		List<AdminSysMenu> list = sysMenuMapper.selectByExample(example);
		
		List<SysMenuVo> result = BeanCopyUtils.copys(list, SysMenuVo.class);
		return result;
	}

	@Override
	public List<Map> getMenuTreeNode(int level, int parentId) {
		List<SysMenuVo>treeNodes= getSysMenus((level+1),parentId);//前端传的level少1
		List<Map> nodeList = new ArrayList<Map>();
		//构造应用List
		for(SysMenuVo sysMenu : treeNodes){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("id", sysMenu.getMenuId());
			map.put("code",sysMenu.getMenuCode());
			map.put("text", sysMenu.getMenuLabel());
			map.put("pid", parentId);
			map.put("menuLevel", sysMenu.getMenuLevel());
			map.put("menuSeq", sysMenu.getMenuSeq());
			map.put("parentsId", sysMenu.getParentsId());
			map.put("parentsCode", sysMenu.getParentsCode());
			map.put("menuType", sysMenu.getMenuType());
			if(sysMenu.getIfleaf().equals("1")){//叶子节点
				map.put("type", IAppConstants.TYPE_MENU);
				map.put("iconCls", IconCls.MENU);
				map.put("isLeaf", true);
				map.put("expanded", false);
				
			}else{
				map.put("type", IAppConstants.TYPE_MENUGROUP);
				map.put("iconCls", IconCls.MENU_ROOT);
				map.put("isLeaf", false);
				map.put("expanded", false);
			}
			nodeList.add(map);
		}
		return nodeList;
	}

	@Override
	public void deleteMenu(boolean isLeaf,String menuSeq) {
		
		Example example = new Example(AdminSysMenu.class);
		Criteria criteria = example.createCriteria();
		if(isLeaf){
			criteria.andEqualTo("menuSeq", menuSeq);
		} else {
			criteria.andLike("menuSeq", menuSeq+"%");
		}
		sysMenuMapper.deleteByExample(example);
	}

	public List<Map<String, Object>> getMenuRoot(){
		List<Map<String, Object>> rootList = new ArrayList<Map<String, Object>>();
		List<SysMenuVo> treeFirst =new ArrayList<SysMenuVo>() ;
		treeFirst.addAll(getSysMenus(0,-1));
		treeFirst.addAll(getSysMenus(1,0));//查询level=1 parent=0的节点
		//↓↓↓↓↓↓↓↓↓↓↓↓将level=1的节点查出来↓↓↓↓↓↓↓↓↓↓↓
		for(SysMenuVo sysMenu : treeFirst){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("id", sysMenu.getMenuId());
			map.put("pId", sysMenu.getParentsId());
			map.put("name", sysMenu.getMenuLabel());
			map.put("menuCode", sysMenu.getMenuCode());
			rootList.add(map);
			List<SysMenuVo> treeFirst2 =new ArrayList<SysMenuVo>();
			treeFirst2.addAll(getSysMenus(2,sysMenu.getMenuId()));
			for(SysMenuVo sysMenuVo :  treeFirst2){
				Map<String,Object> map2 = new HashMap<String,Object>();
				map2.put("id", sysMenuVo.getMenuId());
				map2.put("pId", sysMenuVo.getParentsId());
				map2.put("name", sysMenuVo.getMenuLabel());
				map2.put("menuCode", sysMenu.getMenuCode());
				rootList.add(map2);
			}
		}
		return rootList;
	}
	
	private List<SysMenuVo> getSysMenus( int menuLevel,int parentsId){
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("menuLevel", menuLevel);
		params.put("parentsId", parentsId);
		List<SysMenuVo> treeList = new ArrayList<SysMenuVo>();
		treeList.addAll(sysMenuMapper.getMenuTreeWithoutRole(params));
		return treeList;
	}

	@Override
	public void updateMenu(SysMenuVo menuPO) {
		AdminSysMenu entity = new AdminSysMenu();
		BeanCopyUtils.copy(menuPO, entity);
		entity.setMenuId(menuPO.getMenuId().longValue());
		sysMenuMapper.updateByPrimaryKeySelective(entity);	
	}

	@Override
	public boolean checkMenuCode(String menuCode) {
		
		Example example = new Example(AdminSysMenu.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("menuCode", menuCode);
		return sysMenuMapper.selectCountByExample(example)==0?false:true;
	}

	@Override
	public SysMenuVo getMenuInfo(String menuId) {
		AdminSysMenu entity = sysMenuMapper.selectByPrimaryKey(Long.valueOf(menuId));
		if (null != entity) {
			return BeanCopyUtils.copy(entity, SysMenuVo.class);
		}
		return null;
	}

    @Override
    public List<SysMenuVo> queryMenuDataById(Integer parentId,String conditionJson,String searchKeyWord ){
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("parentsId", parentId);
        if(StringUtil.isNotEmpty(conditionJson)){
            if(conditionJson.equals("menuCode")){
                params.put("menuCode",searchKeyWord);
            }
            if(conditionJson.equals("menuLabel")){
                params.put("menuCode",searchKeyWord);
            }
        }
        List<SysMenuVo> treeList = new ArrayList<SysMenuVo>();
        treeList.addAll(sysMenuMapper.queryMenuDataById(params));
        return treeList;
    }
    
    @Override
    public void delMenuDataById(Integer menuId) {
        SysMenuVo mallSysMenuVo =  getMenuInfo(menuId  + "");
        if (mallSysMenuVo.getIfleaf().equals("0")) {//父
            List<SysMenuVo> mallSysMenuVos  =   queryMenuDataById(menuId,null,null);
            if(mallSysMenuVos.size()> 0 ){
                for(SysMenuVo mallSysMenuVo1 : mallSysMenuVos){
                	sysMenuMapper.deleteByPrimaryKey(mallSysMenuVo1.getMenuId());
                    //删除关系
                	roleMenuService.deleteRoleMenuByMenuId(mallSysMenuVo1.getMenuId()+"");
                }
            }

        }else if(mallSysMenuVo.getIfleaf().equals("1")) {//子
        	sysMenuMapper.deleteByPrimaryKey(menuId.longValue());
        	roleMenuService.deleteRoleMenuByMenuId(menuId+"");
        }

    }
}
