package cn.com.bluemoon.admin.service;

import java.util.List;
import java.util.Map;

import cn.com.bluemoon.admin.domain.vo.SysMenuVo;
import cn.com.bluemoon.admin.domain.vo.SysRoleMenuVo;

public interface MenuService {
	public void deleteMenu(boolean isLeaf,String menuSeq);
	public int addMenu(SysMenuVo po);
	public List<Map<String, Object>> getMenuRoot();
	public List<Map> getMenuTreeNode(int level,int parentId);
	public List<SysMenuVo> queryMenus(String menuType,String parentId);
	public SysMenuVo getMenuInfo(String menuId);
	public boolean checkMenuCode(String menuCode);
	public void updateMenu(SysMenuVo menuPO);

	public List<SysMenuVo> queryMenuDataById (Integer parentId,String conditionJson,String searchKeyWord );

	public void delMenuDataById(Integer menuId);
}
