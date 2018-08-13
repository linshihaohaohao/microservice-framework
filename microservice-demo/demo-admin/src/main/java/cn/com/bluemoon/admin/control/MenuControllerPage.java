package cn.com.bluemoon.admin.control;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.bluemoon.admin.domain.vo.MallMenuTreeVo;
import cn.com.bluemoon.admin.domain.vo.SysMenuVo;
import cn.com.bluemoon.admin.domain.vo.SysRoleMenuVo;
import cn.com.bluemoon.admin.domain.vo.MenuTreeVo;
import cn.com.bluemoon.admin.domain.vo.UserInfo;
import cn.com.bluemoon.admin.service.AuthService;
import cn.com.bluemoon.admin.service.MenuService;
import cn.com.bluemoon.admin.service.RoleMenuService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Created by lsh on 2017/8/16.
 */
@Controller
public class MenuControllerPage {
    @Autowired
    private MenuService menuService;
    @Autowired
	private RoleMenuService roleMenuService;
    @Autowired
    private AuthService authService;

    @RequestMapping(value = "/addMenuForm", method = RequestMethod.GET)
    public String addMenuForm() {
        return "menu/menuForm";
    }
    
    @RequestMapping(value = "/menuForm", method = RequestMethod.GET)
    public String toMenuForm() {
        return "menu/menuIndex";
    }

    @ResponseBody
	@RequestMapping(value="/saveRoleMenu",method=RequestMethod.POST)
	public Map<String, Object> saveRoleMenu(HttpServletRequest request,@RequestBody JSONObject jsonObject){
    	String responseMsg = "网络繁忙，请稍后再试";
		int responseCode = -1;
		Boolean isSuccess = Boolean.FALSE;
		Map<String, Object> result = new HashMap<String, Object>();
    	String newRoleMenu = jsonObject.getString("newRoleMenu");
    	UserInfo user = (UserInfo) request.getSession().getAttribute("user");
		String roleId=jsonObject.getString("roleId");
		String userName=jsonObject.getString("userName");
		roleMenuService.deleteRoleMenuByRoleId(roleId);//删除原来关系
		if(newRoleMenu != null && !"".equals(newRoleMenu)){
			String[] menus = newRoleMenu.split(",");
			for(int i=0;i<menus.length;i++){
				String[] menu = menus[i].split(":");
				if("0".equals(menu[0])){
					continue;
				}
				SysRoleMenuVo roleMenu=new SysRoleMenuVo();
				roleMenu.setRoleId(roleId);
				roleMenu.setMenuId(menu[0]);
				roleMenu.setMenuCode(menu[1]);
				if(user != null){
					roleMenu.setCreateUser(user.getRealName());
				}else{
					roleMenu.setCreateUser(userName);
				}
				roleMenu.setCreateTime(new Date());
				roleMenuService.saveRoleMenu(roleMenu);
			}
		}
		responseMsg ="保存成功";
		responseCode = 0;
		isSuccess = Boolean.TRUE;
		result.put("responseMsg", responseMsg);
		result.put("responseCode", responseCode);
		result.put("isSuccess", isSuccess);
        return result;
	}

    @ResponseBody
    @RequestMapping(value = "/addMenu", method = RequestMethod.POST)
    public String addMenu(SysMenuVo menuPO, @RequestParam Integer parentId) {
        JSONObject jsonObject = new JSONObject();
        if (parentId != null) {
            menuPO.setParentsId(parentId);
            if (parentId.equals(0)) {
                menuPO.setIfleaf("0");
            } else {
                menuPO.setIfleaf("1");
            }
            SysMenuVo vo = menuService.getMenuInfo(parentId + "");
            if (vo.getMenuLevel().equals(0)) {
                menuPO.setMenuLevel(1);
            } else if (vo.getMenuLevel().equals(1)) {
                menuPO.setMenuLevel(2);
            } else if (vo.getMenuLevel().equals(2)) {
                menuPO.setMenuLevel(3);
            }
        } else {
            return "";
        }
        if (menuPO.getMenuId() != null) {
            menuService.updateMenu(menuPO);
            jsonObject.put("IsSucceeded", true);
            return jsonObject.toString();
        }
        menuPO.setMenuType("pc");
        menuPO.setParentsCode("pcRoot");
        menuPO.setAppCode("mall_common");
        int id = menuService.addMenu(menuPO);
        //跟新menuSeq
        menuPO.setMenuId(id);

        menuPO.setMenuSeq("." + menuPO.getParentsId() + "." + String.valueOf(id) + ".");

        menuService.updateMenu(menuPO);

        jsonObject.put("IsSucceeded", true);
        return jsonObject.toString();
    }

    @RequestMapping(value = "/getMenuDataInfo")
    @ResponseBody
    public String getMenuDataInfo(@RequestParam Integer menuId, HttpServletRequest request) {
        SysMenuVo mallSysMenuVo = menuService.getMenuInfo(menuId + "");
        JSONObject jsonObject = new JSONObject();
        if (mallSysMenuVo != null) {
            jsonObject.put("menuId", menuId);
            jsonObject.put("menuCode", mallSysMenuVo.getMenuCode());
            jsonObject.put("menuName", mallSysMenuVo.getMenuName());
            jsonObject.put("parentId", mallSysMenuVo.getParentsId());
            jsonObject.put("displayOrder", mallSysMenuVo.getDisplayOrder());
            jsonObject.put("menuLabel", mallSysMenuVo.getMenuLabel());
            jsonObject.put("functionPath", mallSysMenuVo.getFunctionPath());
            jsonObject.put("functionDesc", mallSysMenuVo.getFunctionDesc());
        }
        return jsonObject.toString();
    }

    @ResponseBody
    @RequestMapping(value = "/getMenuData")
    public Map<String, Object> getMenuData(HttpSession session) {
        String responseMsg = "网络繁忙，请稍后再试";
        int responseCode = -1;
        Boolean isSuccess = Boolean.FALSE;
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("responseMsg", responseMsg);
        result.put("responseCode", responseCode);
        result.put("isSuccess", isSuccess);
        try {
            UserInfo user = new UserInfo();
            user = (UserInfo) session.getAttribute("user");
            if (user != null) {
                List<MenuTreeVo.MenuTreeNode> treeNodes = null;
                MenuTreeVo tree = authService.getUserMenuTree(user.getAccount(),false);
                if (tree != null) {
                    treeNodes = tree.getMenuTreeRootNodeList();
                }

                result.put("treeNodes", treeNodes);
                result.put("responseMsg", "请求成功");
                result.put("responseCode", 0);
                result.put("isSuccess", Boolean.TRUE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/queryMenuData")
    public List<MallMenuTreeVo> queryMenuData(HttpSession session) {

        try {
            UserInfo user = new UserInfo();
            user = (UserInfo) session.getAttribute("user");
            if (user != null) {
                List<MallMenuTreeVo> mallMenuTreeVos = authService.queryMenuData(null);
                return mallMenuTreeVos;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @ResponseBody
    @RequestMapping(value = "/queryMenuDataById")
    public List<SysMenuVo> queryMenuDataById(@RequestParam(required = false) Integer parentId,
                                                 @RequestParam(required = false) String conditionJson,
                                                 @RequestParam(required = false) String searchKeyWord) {
        if (parentId == null) {
            return null;
        }
        try {
            List<SysMenuVo> mallMenuTreeVos = menuService.queryMenuDataById(parentId, conditionJson, searchKeyWord);
            return mallMenuTreeVos;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @ResponseBody
    @RequestMapping(value = "/delMenuDataById")
    public String delMenuDataById(@RequestParam(required = false) Integer menuId,HttpSession session) {
        JSONObject jsonObject = new JSONObject();
        //需要获取角色ID
        UserInfo user = new UserInfo();
        user = (UserInfo) session.getAttribute("user");
        if (user == null){
            jsonObject.put("Fail", false);
            return jsonObject.toString();
        }

        if (menuId == null) {
            jsonObject.put("Fail", false);
            return jsonObject.toString();
        }
        try {
            menuService.delMenuDataById(menuId);
            jsonObject.put("IsSucceeded", true);
        } catch (Exception e) {
            e.printStackTrace();
            jsonObject.put("Fail", false);
        }
        return jsonObject.toString();
    }
    
    @ResponseBody
	@RequestMapping(value="/getRoleMenuTreeNode",method=RequestMethod.GET)
	public List<Map<String, Object>> getRoleMenuTreeNode(HttpServletRequest request){
		//String[] collapse={"pcRoot","tappRoot"};
		List<Map<String, Object>> treeNodeList=new ArrayList<Map<String, Object>>();
		treeNodeList.addAll(menuService.getMenuRoot());//生成根节点和加载第一级
		List<Map<String, Object>> temp=new ArrayList<Map<String, Object>>();
		temp.clear();
		temp.addAll(treeNodeList);
		String roleId=request.getParameter("roleId");
		if(null!=roleId){
			String menuRole=roleMenuService.getMenuListByRoleId(roleId);//2012,1231,123
			for(Map<String, Object> node:treeNodeList){
				String nodeId=node.get("id").toString();
				if(menuRole.contains(nodeId)){
					node.put("checked", true);//有权限 叶子节点才授权，不然父节点被checked 子节点也会被checked
					node.put("open", true);//展开
				}else{
					node.put("checked", false);//无权限
					if("-1".equals(node.get("pId").toString())){//根节点
						node.put("open", true);//根节点永远展开
					}else{
						node.put("open", false);//其他无权限的就不展开
					}
				}
			}
		}
		return treeNodeList;
	}
}
