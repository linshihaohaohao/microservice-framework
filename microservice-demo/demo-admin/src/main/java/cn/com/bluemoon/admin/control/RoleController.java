package cn.com.bluemoon.admin.control;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;

import cn.com.bluemoon.admin.domain.vo.SysRoleVo;
import cn.com.bluemoon.admin.domain.vo.SysUserRoleVo;
import cn.com.bluemoon.admin.domain.vo.UserInfo;
import cn.com.bluemoon.admin.service.RoleService;
import cn.com.bluemoon.admin.service.UserRoleService;
import cn.com.bluemoon.common.crmbp.vo.StaffDetailInfo;
import cn.com.bluemoon.service.common.service.EmpService;
import cn.com.bluemoon.utils.random.SerialNo;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/role")
public class RoleController {
	public static Logger log = LoggerFactory.getLogger(RoleController.class);

	@Autowired
	private RoleService roleService;
	@Autowired
	private UserRoleService userRoleService;
	/*@Reference(timeout=30000)
	private SsoService ssoService;*/
	@Reference(timeout=30000,check=false)
	private EmpService empService;

	private SysRoleVo sysRole;

	/**
	 * <p>验证此角色编码是否已存在</p>
	 * @param roleCode
	 * @return boolean
	 */
	private boolean isExistRoleCode(String roleCode) {
		boolean isRoleExist = Boolean.TRUE;
		try {
			isRoleExist = roleService.checkRoleExist(roleCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isRoleExist;
	}

    @RequestMapping(value = "/roleList",method = RequestMethod.GET)
    public String roleList(HttpSession session){
    	UserInfo userInfo = (UserInfo)session.getAttribute("user");
    	if(userInfo == null){
    		return "login";
    	}
        return "template/roleList";
    }
    
    @RequestMapping(value = "/roleAdd",method = RequestMethod.GET)
    public String roleAdd(HttpSession session){
    	UserInfo userInfo = (UserInfo)session.getAttribute("user");
    	if(userInfo == null){
    		return "login";
    	}
        return "template/roleAdd";
    }
    
    @RequestMapping(value = "/toRoleUpdate",method = RequestMethod.GET)
    public String toRoleUpdate(HttpSession session){
    	UserInfo userInfo = (UserInfo)session.getAttribute("user");
    	if(userInfo == null){
    		return "login";
    	}
        return "template/roleUpdate";
    }
    
    @RequestMapping(value = "/toRoleMenuAdd",method = RequestMethod.GET)
    public String toRoleMenuAdd(HttpServletRequest request,HttpSession session){
    	UserInfo userInfo = (UserInfo)session.getAttribute("user");
    	if(userInfo == null){
    		return "login";
    	}
    	String roleId = request.getParameter("roleId");
    	request.setAttribute("roleId",roleId);
        return "template/addRoleMenu";
    }
    
    @RequestMapping(value = "/toRoleUserAdd",method = RequestMethod.GET)
    public String toRoleUserAdd(HttpServletRequest request,HttpSession session){
    	UserInfo userInfo = (UserInfo)session.getAttribute("user");
    	if(userInfo == null){
    		return "login";
    	}
    	String roleId = request.getParameter("roleId");
    	request.setAttribute("roleId",roleId);
        return "template/addRoleUser";
    }
    
    @RequestMapping(value = "/toConfigUser",method = RequestMethod.GET)
    public String toConfigUser(HttpSession session){
    	UserInfo userInfo = (UserInfo)session.getAttribute("user");
    	if(userInfo == null){
    		return "login";
    	}
        return "template/configUser";
    }
    
    @ResponseBody
    @RequestMapping(value = "/getRoleUserList")
    public String getRoleUserList(HttpServletRequest request,HttpSession session){
    	String roleId = request.getParameter("roleId");
    	//参数校验
    	List<SysUserRoleVo> list = userRoleService.getUserRoleListByRoleId(roleId);
		if(list != null && list.size()>0){
			for (SysUserRoleVo entity : list) {
				JSONObject json = new JSONObject();
				json.put("staffNum", entity.getUserId());
				try {
					Map<String,Object> userResult = empService.getEmpDetailInfo(json);
					int responseCode = (Integer)userResult.get("responseCode");
					if(responseCode == 0){
						StaffDetailInfo sd = (StaffDetailInfo)userResult.get("data");
						if(sd != null){
							entity.setUserName(sd.getStaffName());
						}
						
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		JSONObject jsonobj = new JSONObject();
		JSONArray rows = new JSONArray();
		if(list != null && list.size() > 0){
			rows = JSONArray.fromObject(list);
		}
		jsonobj.put("rows", rows);
	    return jsonobj.toString();
    }
    
    @ResponseBody
    @RequestMapping(value = "/getUserInfo")
    public String getUserInfo(HttpServletRequest request, HttpSession session){
    	JSONObject jsonobj = new JSONObject();
    	String userId = request.getParameter("userId");
    	//参数校验
		JSONObject json = new JSONObject();
		json.put("staffNum", userId);
		try {
			Map<String,Object> userResult = empService.getEmpDetailInfo(json);
			int responseCode = (Integer)userResult.get("responseCode");
			if(responseCode == 0){
				StaffDetailInfo sd = (StaffDetailInfo)userResult.get("data");
				if(sd != null ){
					jsonobj.put("isSuccess", true);
					jsonobj.put("user", sd);
				}else{
					jsonobj.put("isSuccess", false);
					jsonobj.put("msg", userResult.get("responseMsg"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonobj.toString();
    }
    
    @ResponseBody
    @RequestMapping(value = "/saveRoleUser")
    public String saveRoleUser(HttpServletRequest request, HttpSession session){
    	JSONObject jsonobj = new JSONObject();
    	UserInfo userInfo = (UserInfo)session.getAttribute("user");
    	String roleId = request.getParameter("roleId");
    	String staffNum = request.getParameter("staffNum");
    	//参数校验
		JSONObject json = new JSONObject();
		json.put("staffNum", staffNum);
		try {
			Map<String,Object> userResult = empService.getEmpDetailInfo(json);
			boolean isSuccess = (Boolean)userResult.get("isSuccess");
			jsonobj.put("isSuccess", isSuccess);
			if(isSuccess){
				SysUserRoleVo ur = userRoleService.getUserRoleByRoleIdAndUserId(roleId,staffNum);
				if(ur != null){
					jsonobj.put("isSuccess", false);
					jsonobj.put("msg", "该用户已存在");
					return jsonobj.toString();
				}
				StaffDetailInfo sd = (StaffDetailInfo)userResult.get("data");
				if(sd != null){
					SysUserRoleVo entity = new SysUserRoleVo();
					entity.setCreateUser(userInfo.getAccount());
					entity.setCreateTime(new Date());
					entity.setRoleId(roleId);
					entity.setUserId(staffNum);
					userRoleService.saveEntity(entity);
					jsonobj.put("user", sd);
				}else{
					jsonobj.put("msg", "数据保存失败");
				}
			}else{
				jsonobj.put("msg", "数据保存失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	    return jsonobj.toString();
    }
    
    @ResponseBody
    @RequestMapping(value = "/delUserInfo")
    public String delUserInfo(HttpServletRequest request, HttpSession session){
    	JSONObject jsonobj = new JSONObject();
    	String roleId = request.getParameter("roleId");
    	String userId = request.getParameter("userId");
    	
		int res = userRoleService.removeUserRole(userId, roleId);
		if(res > 0 ){
			jsonobj.put("isSuccess", true);
		}else{
			jsonobj.put("isSuccess", false);
		}
	    return jsonobj.toString();
    }
    
	/**
	 * <p>Description:查询角色信息</p>
	 * RoleController.java
	 * return:Map<String,Object>
	 */
	@ResponseBody
	@RequestMapping(value = "/queryRoleInfo", method = RequestMethod.POST)
	public String queryRoleInfo(HttpServletRequest request, @RequestBody JSONObject jsonObject) {
		String roleCode = jsonObject.has("roleCode")?jsonObject.getString("roleCode"):null;
		String roleName = jsonObject.has("roleName")?jsonObject.getString("roleName"):null;
		int offset = Integer.parseInt(jsonObject.getString("offset"));
		int pageSize = Integer.parseInt(jsonObject.getString("pageSize"));
		String sortOrder =  jsonObject.getString("sortOrder");
		String responseMsg = "网络繁忙，请稍后再试";
		int responseCode = -1;
		Boolean isSuccess = Boolean.FALSE;
		Map<String, Object> result = new HashMap<String, Object>();
		List<SysRoleVo> roleInfo = new ArrayList<SysRoleVo>();
		JSONArray ja = new JSONArray();
		int total = 0;
		try {
			UserInfo user = (UserInfo) request.getSession().getAttribute("user");
			if (user != null) {
				roleInfo = roleService.getRoleListByExample(roleCode, roleName,offset,pageSize,sortOrder);
				total = roleService.getRoleListByExampleCount(roleCode, roleName);
				if(roleInfo != null && roleInfo.size() > 0){
					for(SysRoleVo vo : roleInfo){
						ja.add(vo);
					}
				}
				responseMsg = "请求成功!";
				responseCode = 0;
				isSuccess = Boolean.TRUE;
			} else {
				responseMsg = "请先登录";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		result.put("responseMsg", responseMsg);
		result.put("responseCode", responseCode);
		result.put("isSuccess", isSuccess);
		result.put("roleInfo", roleInfo);
		JSONObject jsonobj= new JSONObject();
		jsonobj.put("total", total);
		jsonobj.put("rows", ja);
		return jsonobj.toString();
	}

	/**
	 * <p>Description:根据角色编码验证角色是否已经存在</p>
	 * RoleController.java
	 * return:Map<String,Object>
	 */
	@ResponseBody
	@RequestMapping(value = "/checkRoleExist", method = RequestMethod.POST)
	public Map<String, Object> checkRoleExist(HttpServletRequest request, @RequestBody
	JSONObject params) {
		String roleCode = (params.containsKey("roleCode") ? (String) params.get("roleCode") : "");
		String responseMsg = "网络繁忙，请稍后再试";
		int responseCode = -1;
		Boolean isSuccess = Boolean.FALSE;
		Boolean isRoleExist = Boolean.TRUE;
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			UserInfo user = (UserInfo) request.getSession().getAttribute("user");
			if (user != null) {
				if (!StringUtils.isEmpty(roleCode)) {
					isRoleExist = isExistRoleCode(roleCode);
					if (isRoleExist) {
						responseMsg = "此角色编码已存在!";
					} else {
						responseMsg = "此角色编码可用!";
					}
					responseCode = 0;
					isSuccess = Boolean.TRUE;
				} else {
					isRoleExist = Boolean.TRUE;
					responseMsg = "角色编码为空";
				}
			} else {
				responseMsg = "请先登录";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		result.put("responseMsg", responseMsg);
		result.put("responseCode", responseCode);
		result.put("isSuccess", isSuccess);
		result.put("isRoleExist", isRoleExist);
		return result;
	}

	/**
	 * <p>Description:新增角色信息</p>
	 * RoleController.java
	 * return:Map<String,Object>
	 */
	@ResponseBody
	@RequestMapping(value = "/addRole", method = RequestMethod.POST)
	public Map<String, Object> addRole(HttpServletRequest request, @RequestBody
	JSONObject roleObject) {
		String responseMsg = "网络繁忙，请稍后再试";
		int responseCode = -1;
		Boolean isSuccess = Boolean.FALSE;
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("responseMsg", responseMsg);
		result.put("responseCode", responseCode);
		result.put("isSuccess", isSuccess);
		try {
			sysRole = (SysRoleVo) JSONObject.toBean(roleObject, SysRoleVo.class);
			String roleCode = sysRole.getRoleCode();
			String roleName = sysRole.getRoleName();
			if (StringUtils.isEmpty(roleCode) || StringUtils.isEmpty(roleName)) {
				responseMsg = "角色编码或者角色名称为空";
				result.put("responseMsg", responseMsg);
				return result;
			}
			UserInfo user = (UserInfo) request.getSession().getAttribute("user");
			if (user != null) {
				boolean isExistRoleCode = isExistRoleCode(roleCode);
				if (isExistRoleCode) {
					responseMsg = "角色编码已存在";
					result.put("responseMsg", responseMsg);
					return result;
				}
				sysRole.setRoleId(SerialNo.getUNID());
				sysRole.setCreateUser(user.getAccount());
				sysRole.setCreateTime(new Date());
				roleService.addRole(sysRole);
				result.put("responseMsg", "添加角色信息成功");
				result.put("responseCode", 0);
				result.put("isSuccess", Boolean.TRUE);
				return result;
			} else {
				result.put("responseMsg", "请登录后操作");
				return result;
			}
		} catch (Exception e) {
			result.put("responseMsg", "数据操作异常");
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * <p>Description:新增角色信息</p>
	 * RoleController.java
	 * return:Map<String,Object>
	 */
	@ResponseBody
	@RequestMapping(value = "/updateRole", method = RequestMethod.POST)
	public Map<String, Object> updateRole(HttpServletRequest request, @RequestBody
	Map<String, String> params) {
		String roleCode = params.get("roleCode");
		String roleName = params.get("roleName");
		String roleDesc = params.get("roleDesc");
		String roleId = params.get("roleId");
		String oldRoleCode = params.get("oldRoleCode");//旧编码

		String responseMsg = "网络繁忙，请稍后再试";
		int responseCode = -1;
		Boolean isSuccess = Boolean.FALSE;
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("responseMsg", responseMsg);
		result.put("responseCode", responseCode);
		result.put("isSuccess", isSuccess);
		if (StringUtils.isEmpty(roleCode) || StringUtils.isEmpty(roleName)) {
			responseMsg = "角色编码或者角色名称为空";
			result.put("responseMsg", responseMsg);
			return result;
		}
		try {
			UserInfo user = (UserInfo) request.getSession().getAttribute("user");
			if (user != null) {
				if (!roleCode.equals(oldRoleCode)) { //如果更新的编码和之前的一直，则不校验是否已存在的编码
					boolean isExistRoleCode = isExistRoleCode(roleCode);
					if (isExistRoleCode) {
						responseMsg = "角色编码已存在";
						result.put("responseMsg", responseMsg);
						return result;
					}
				}
				SysRoleVo sysRole = new SysRoleVo();
				sysRole.setRoleId(roleId);
				sysRole.setRoleCode(roleCode);
				sysRole.setRoleName(roleName);
				sysRole.setRoleDesc(roleDesc);
				sysRole.setCreateUser(user.getAccount());
				sysRole.setCreateTime(new Date());
				roleService.updateRole(sysRole);
				responseMsg = "更新角色信息成功";
				responseCode = 0;
				isSuccess = Boolean.TRUE;
			} else {
				responseMsg = "请先登录";
			}
		} catch (Exception e) {
			responseMsg = "更新角色信息异常";
			e.printStackTrace();
		}
		result.put("responseMsg", responseMsg);
		result.put("responseCode", responseCode);
		result.put("isSuccess", isSuccess);
		return result;
	}

	/**
	 * <p>Description:删除选中角色信息(物理删除)</p>
	 * RoleController.java
	 * return:Map<String,Object>
	 */
	@ResponseBody
	@RequestMapping(value = "/delRole", method = RequestMethod.POST)
	public Map<String, Object> delRoleByIds(HttpServletRequest request, @RequestBody
	List<String> listIds) {
		String responseMsg = "网络繁忙，请稍后再试";
		int responseCode = -1;
		Boolean isSuccess = Boolean.FALSE;
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			UserInfo user = (UserInfo) request.getSession().getAttribute("user");
			if (user != null) {
				roleService.deleteRoleByIds(listIds);
				responseMsg = "删除角色信息成功";
				responseCode = 0;
				isSuccess = Boolean.TRUE;
			} else {
				responseMsg = "请先登录";
			}
		} catch (Exception e) {
			responseMsg = "删除角色信息异常";
			e.printStackTrace();
		}
		result.put("responseMsg", responseMsg);
		result.put("responseCode", responseCode);
		result.put("isSuccess", isSuccess);
		return result;
	}

	public SysRoleVo getSysRole() {
		return sysRole;
	}

	public void setSysRole(SysRoleVo sysRole) {
		this.sysRole = sysRole;
	}

}