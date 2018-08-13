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
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.bluemoon.admin.domain.vo.MallSysUserDelegRoleVo;
import cn.com.bluemoon.admin.domain.vo.SysUserRoleVo;
import cn.com.bluemoon.admin.domain.vo.UserInfo;
import cn.com.bluemoon.admin.service.RoleService;
import cn.com.bluemoon.admin.service.UserDelegRoleService;
import cn.com.bluemoon.admin.service.UserRoleService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 *	用户角色
 */
@Controller
@RequestMapping("/userRole")
public class UserRoleController {

	@Autowired
	private UserRoleService userRoleService;
	@Autowired
	private UserDelegRoleService userDelegRoleService;
	
	
    @RequestMapping(value = "/userRoleList",method = RequestMethod.GET)
    public String userRoleList(HttpSession session){
    	UserInfo userInfo = (UserInfo)session.getAttribute("user");
    	if(userInfo == null){
    		return "login";
    	}
        return "template/userRoleList";
    }
    
    @RequestMapping(value = "/toSelectUserRoleList",method = RequestMethod.GET)
    public String toSelectUserRoleList(HttpSession session,HttpServletRequest request){
    	UserInfo userInfo = (UserInfo)session.getAttribute("user");
    	if(userInfo == null){
    		return "login";
    	}
    	String operatorId = request.getParameter("operatorId");
    	request.setAttribute("operatorId", operatorId);
        return "template/selectUserRoleList";
    }
    
    @ResponseBody
	@RequestMapping(value="/getRoleList", method=RequestMethod.POST)
	public String getRoleList(HttpServletRequest request) throws Exception{
    	String operatorId = request.getParameter("operatorId");
    	List<Map<String, String>> autorList = new ArrayList<Map<String, String>>();
    	List<Map<String, String>> unautorList = new ArrayList<Map<String, String>>();
    	if("sysadmin".equals(operatorId)){
			operatorId="0";
		}
    	autorList = userRoleService.getAuthorizedRoleList(operatorId);
    	JSONArray ja = new JSONArray();
    	if(autorList != null && autorList.size() > 0){
    		for(int i=0;i<autorList.size();i++){
				autorList.get(i).put("isAutor", "1");
				ja.add(autorList.get(i));
			}
    	}
    	unautorList = userRoleService.getUnauthorizedRoleList(operatorId);
    	if(unautorList != null && unautorList.size() > 0){
    		for(int i=0;i<unautorList.size();i++){
				unautorList.get(i).put("isAutor", "0");
				ja.add(unautorList.get(i));
			}
    	}
		JSONObject jsonobj= new JSONObject();
		jsonobj.put("rows", ja);
    	return jsonobj.toString();
    }
	
    /**
	 * <p>Description:授权或者取消授权</p>
	 * return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = "/updateUserRole", method = RequestMethod.POST)
	public Map<String, Object> updateUserRole(HttpServletRequest request,@RequestBody List<String> listIds) throws Exception {
		String responseMsg = "网络繁忙，请稍后再试";
		int responseCode = -1;
		Boolean isSuccess = Boolean.FALSE;
		Map<String, Object> result = new HashMap<String, Object>();
		UserInfo user = (UserInfo) request.getSession().getAttribute("user");
		String operatorId=request.getParameter("operatorId");
		try {
			if("sysadmin".equals(operatorId)){
				operatorId="0";
			}
			userRoleService.deleteUserRoleByOperatorId(operatorId);
			if(listIds != null && listIds.size() > 0){
				for(int i=0;i<listIds.size();i++){
					String ids[] = listIds.get(i).split(":");
					SysUserRoleVo vo = new SysUserRoleVo();
					vo.setRoleId(ids[0]);
					vo.setCreateUser(user.getAccount());
					vo.setUserId(operatorId);
					vo.setRoleCode(ids[1]);
					vo.setCreateTime(new Date());
					userRoleService.addUserRole(vo);
				}
			}
			responseCode = 0;
			responseMsg = "成功";
			isSuccess = Boolean.TRUE;
		} catch (Exception e) {
			e.printStackTrace();
		}
		result.put("responseMsg", responseMsg);
		result.put("responseCode", responseCode);
		result.put("isSuccess", isSuccess);
		return result;
	}
    
	/**
	 * <p>Description:授权或者取消授权</p>
	 * return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = "/updateUserRoleOld", method = RequestMethod.POST)
	public void updateUserRoleOld(HttpServletRequest request,@RequestBody JSONObject params) throws Exception {
		JSONArray roles=JSONArray.fromObject(params.getString("roles"));
		for(int i=0;i<roles.size();i++){
			JSONObject role=roles.getJSONObject(i);
			String op=role.getString("op");
			String operatorId=params.getString("operatorId");
			if("sysadmin".equals(operatorId)){
				operatorId="0";
			}
			String roleId=role.getString("id");
			String roleCode=role.getString("code");
			if("add".equals(op)){//授权
				SysUserRoleVo rolePO=new SysUserRoleVo();
				rolePO.setUserId(operatorId);
				rolePO.setRoleId(roleId);
				rolePO.setRoleCode(roleCode);
				UserInfo user = (UserInfo) request.getSession().getAttribute("user");
				String currentUserId = user.getAccount();
//				rolePO.setCreateUser("0");
				rolePO.setCreateUser(currentUserId);
				rolePO.setCreateTime(new Date());
				userRoleService.addUserRole(rolePO);
			}else{//取消授权
				userRoleService.removeUserRole(operatorId,roleId);
			}
		}
		return;
	}
	/**
	 * <p>Description:查询已授权的角色</p>
	 * return:Map<String,Object>
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = "/getAuthorizedRoleList", method = RequestMethod.POST)
	public List<Map<String, String>> getAuthorizedRoleList(HttpServletRequest request,@RequestBody JSONObject params) throws Exception {
		String operatorId=params.getString("operatorId");
		if("sysadmin".equals(operatorId)){
			operatorId="0";
		}
		return userRoleService.getAuthorizedRoleList(operatorId);
	}
	/**
	 * <p>Description:查询未授权授权的角色</p>
	 * RoleController.java
	 * return:Map<String,Object>
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = "/getUnauthorizedRoleList", method = RequestMethod.POST)
	public List<Map<String, String>> getUnauthorizedRoleList(HttpServletRequest request,@RequestBody JSONObject params) throws Exception {
		String operatorId=params.getString("operatorId");
		if("sysadmin".equals(operatorId)){
			operatorId="0";
		}
		return userRoleService.getUnauthorizedRoleList(operatorId);
	}
	
	/**
	 * 针对卡券推送，考勤打卡对用户授权
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/addUserRole", method = RequestMethod.POST)
	public HashMap<String, Object> addUserRole(@RequestBody JSONObject jsonObject) throws Exception {
		HashMap<String, Object> resultMap = new HashMap<String , Object>();
		try {
			JSONObject role = jsonObject.getJSONObject("role");
			JSONObject emp = jsonObject.getJSONObject("emp");
			//获取角色信息和人员信息
			String operatorId = emp.getString("empCode");
			if("sysadmin".equals(operatorId)){
				operatorId="0";
			}
			String roleId=role.getString("roleId");
			String roleCode=role.getString("roleCode");
			//授权
			SysUserRoleVo rolePO=new SysUserRoleVo();
			rolePO.setUserId(operatorId);
			rolePO.setRoleId(roleId);
			rolePO.setRoleCode(roleCode);
			rolePO.setCreateUser("0");
			rolePO.setCreateTime(new Date());
			userRoleService.addUserRole(rolePO);
			resultMap.put("responseMsg", "请求成功");
			resultMap.put("responseCode", 0);
			resultMap.put("isSuccess", Boolean.TRUE);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("responseMsg", "网络繁忙，请稍后重试");
			resultMap.put("responseCode", -1);
			resultMap.put("isSuccess", Boolean.FALSE);
		}
		
		return resultMap;
	}
	
	/**
	 * <p>Description:查询已授权的角色</p>
	 * return:Map<String,Object>
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = "/getAuthorizedRoleListForDelegation", method = RequestMethod.POST)
	public List<Map<String, String>> getAuthorizedRoleListForDelegation(HttpServletRequest request,@RequestBody JSONObject params) throws Exception {
		String operatorId=params.getString("operatorId");
		if("sysadmin".equals(operatorId)){
			operatorId="0";
		}
		return userDelegRoleService.getAuthorizedRoleListForDelegation(operatorId);
	}
	
	/**
	 * <p>Description:查询未授权授权的角色</p>
	 * RoleController.java
	 * return:Map<String,Object>
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = "/getUnauthorizedRoleListForDelegation", method = RequestMethod.POST)
	public List<Map<String, String>> getUnauthorizedRoleListForDelegation(HttpServletRequest request,@RequestBody JSONObject params) throws Exception {
		String operatorId=params.getString("operatorId");
		if("sysadmin".equals(operatorId)){
			operatorId="0";
		}
		return userDelegRoleService.getUnauthorizedRoleListForDelegation(operatorId);
	}
	
	/**
	 * <p>Description:授权或者取消授权</p>
	 * return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = "/updateUserRoleForDelegation", method = RequestMethod.POST)
	public void updateUserRoleForDelegation(HttpServletRequest request,@RequestBody JSONObject params) throws Exception {
		JSONArray roles=JSONArray.fromObject(params.getString("roles"));
		String operatorId=params.getString("operatorId");
		for(int i=0;i<roles.size();i++){
			JSONObject role=roles.getJSONObject(i);
			String op=role.getString("op");
			if("sysadmin".equals(operatorId)){
				operatorId="0";
			}
			String roleId=role.getString("id");
			String roleCode=role.getString("code");
			if("add".equals(op)){//授权
				MallSysUserDelegRoleVo rolePO=new MallSysUserDelegRoleVo();
				rolePO.setUserId(operatorId);
				rolePO.setRoleId(roleId);
				rolePO.setRoleCode(roleCode);
				UserInfo user = (UserInfo) request.getSession().getAttribute("user");
				String currentUserId = user.getAccount();
				rolePO.setCreateUser(currentUserId);
				rolePO.setCreateTime(new Date());
				userDelegRoleService.addUserRoleForDelegation(rolePO);
			}else{//取消授权
				userDelegRoleService.removeUserRoleForDelegation(operatorId,roleId);
			}
		}
		//根据该用户是否有代理授权，给该用户新增下放权限的角色,或者取消下放角色权限
		userDelegRoleService.addRoleByExistDelegation(operatorId);
		return;
	}
	
	/**
	 * <p>Description:查询代理授权还未授权的角色</p>
	 * RoleController.java
	 * return:Map<String,Object>
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = "/getUnauthorizedRoleListForProxyAuthor", method = RequestMethod.POST)
	public List<Map<String, String>> getUnauthorizedRoleListForProxyAuthor(HttpServletRequest request,@RequestBody JSONObject params) throws Exception {
		String operatorId=params.getString("operatorId");
		UserInfo user = (UserInfo) request.getSession().getAttribute("user");
		String currentUserId = user.getAccount();
		if("sysadmin".equals(operatorId)){
			operatorId="0";
		}
		return userDelegRoleService.getUnauthorizedRoleListForProxyAuthor(operatorId,currentUserId);
	}
	
	/**
	 * <p>Description:查询已授权的角色,但是要排除由sysadmin授权的角色,并且只展示自己授权的角色</p>
	 * return:Map<String,Object>
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = "/getAuthorizedRoleListThroughFilter", method = RequestMethod.POST)
	public List<Map<String, String>> getAuthorizedRoleListThroughFilter(HttpServletRequest request,@RequestBody JSONObject params) throws Exception {
		UserInfo user = (UserInfo) request.getSession().getAttribute("user");
		String operationUser  = user.getAccount();
		String operatorId=params.getString("operatorId");
		if("sysadmin".equals(operatorId)){
			operatorId="0";
		}
		Map<String,String> map = new HashMap<String,String>();
		map.put("operatorId", operatorId);
		map.put("exceptUserId", "0");
		map.put("exceptUserCode", "sysadmin");
		map.put("operationUser", operationUser);
		return userRoleService.getAuthorizedRoleListThroughFilter(map);
	}
}
