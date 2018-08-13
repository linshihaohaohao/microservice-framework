package cn.com.bluemoon.admin.control;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.common.collect.Lists;

import cn.com.bluemoon.admin.domain.vo.UserInfo;
import cn.com.bluemoon.admin.service.AuthService;
import cn.com.bluemoon.admin.service.LogService;
import cn.com.bluemoon.admin.utils.ZTreeNode;
import cn.com.bluemoon.service.user.service.SsoService;
import net.sf.json.JSONObject;

@Controller
public class UserController {
	
	@Autowired
	private AuthService authService;
	
	/*	@Autowired
	private RedisService redisService;*/
	@Reference(timeout=30000)
	private SsoService ssoService;

    @RequestMapping(value = "/toLogin",method = RequestMethod.GET)
    public String toLogin(HttpServletRequest request){
    	String isPre = request.getParameter("isPre");
    	if(isPre != null && !"".equals(isPre)){
    		request.setAttribute("isPre", isPre);
    	}
        return "login";
    }
    
	@ResponseBody
	@RequestMapping(value = "/logout",method = RequestMethod.POST)
	public Map<String, Object> logout(HttpSession session){
		Map<String,Object> result = new HashMap<String, Object>();
		Boolean isSuccess = Boolean.TRUE;
		String responseCode = "0";
		String responseMsg = "注销成功";
		
		session.invalidate();
		
		result.put("isSuccess", isSuccess);
		result.put("responseCode", responseCode);
		result.put("responseMsg", responseMsg);
		return result;
	}

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> login(HttpServletRequest request, @RequestBody JSONObject jsonObject){
    	HttpSession session = request.getSession();
    	String responseMsg = "网络繁忙，请稍后再试";
		int responseCode = -1;
		Boolean isSuccess = Boolean.FALSE;
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("responseMsg", responseMsg);
		result.put("responseCode", responseCode);
		result.put("isSuccess", isSuccess);
		String account = jsonObject.has("account")?jsonObject.getString("account"):"";
		String password = jsonObject.has("password")?jsonObject.getString("password"):"";
		if( "".equals(account) ){
			result.put("responseMsg", "用户名不能为空");
			result.put("responseCode", 2202);
			return result;
		}
		
		if( "".equals(password) ){
			result.put("responseMsg", "密码不能为空");
			result.put("responseCode", 2203);
			return result;
		}
		try {
			JSONObject obj=new JSONObject();
			obj.put("account",account );
			obj.put("password", password);
			String res=ssoService.ssoLogin(jsonObject);
			JSONObject param = JSONObject.fromObject(res);
			
			if( (Boolean)param.get("isSuccess") == true ){
				//用户信息存入session
				UserInfo user = new UserInfo();
				String userStr = ssoService.getUserInfo(jsonObject);
				JSONObject userJson = JSONObject.fromObject(userStr);
				user = (UserInfo) JSONObject.toBean(userJson.getJSONObject("user"), UserInfo.class);
				session.setAttribute("user", user);
				//角色信息存入session
				Set<String> roleSet = authService.getRoleListByUserId(user.getAccount());
				session.setAttribute("roles", roleSet == null ? new HashSet<Integer>():roleSet);
				String roleCode = authService.getRoleCodeList(user.getAccount());
				session.setAttribute("roleCode", roleCode);
			}
			result.put("isSuccess", param.get("isSuccess"));
			result.put("responseMsg", param.get("responseMsg"));
			result.put("responseCode", param.get("responseCode"));
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
        return result;
    }
    
    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String toIndex(HttpServletRequest request){
    	UserInfo userInfo = (UserInfo)request.getSession().getAttribute("user");
    	if(userInfo == null){
    		return "login";
    	}
        return "template/index";
    }
    
    @RequestMapping(value = "/dishBoard",method = RequestMethod.GET)
    public String toDishBoard(){
        return "template/dishboard";
    }

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String toList(){
        return "template/list";
    }

    @RequestMapping(value = "/add",method = RequestMethod.GET)
    public String toAdd(){
        return "template/add";
    }
    @RequestMapping(value = "/addForm",method = RequestMethod.GET)
    public String toaddForm(){
        return "template/addForm";
    }
    @RequestMapping(value = "/query",method = RequestMethod.GET)
    public String toQuery(){
        return "template/query";
    }
    @RequestMapping(value = "/update",method = RequestMethod.GET)
    public String toUpdate(){
        return "template/update";
    }
    @RequestMapping(value = "/list1",method = RequestMethod.GET)
    public String list1(){
        return "template/list2";
    }
    @RequestMapping(value = "/toListJson1",method = RequestMethod.GET)
    @ResponseBody
    public String toListJson1(){
        String json = "{ \"total\": 100, \"rows\": [ {\"id\": 1, \"lastname\": \"tianzh\", \"state\": \"1\",  \"username\": \"虎哥\"}," +
                " { \"id\": 2, \"lastname\": \"tianzh\", \"username\": \"虎哥1\", \"state\": \"2\" }," +
                "    {\"id\": 3,\"lastname\": \"tianzh\",\"username\": \"虎哥5\",\"state\": \"0\" }," +
                "    { \"id\": 4, \"lastname\": \"tianzh\", \"username\": \"虎哥4\", \"state\": \"0\"}," +
                "    { \"id\": 5,\"lastname\": \"tianzh\",\"username\": \"虎哥3\",\"state\": \"0\" }," +
                "    { \"id\": 6, \"lastname\": \"tianzh\",\"username\": \"虎哥1\",\"state\": \"0\"}," +
                "    {\"id\": 7,\"lastname\": \"tianzh\",\"username\": \"虎哥\",\"state\": \"1\"}," +
                "    {\"id\": 8,\"lastname\": \"tianzh\",\"username\": \"虎哥7\", \"state\": \"0\"}," +
                "    {\"id\": 9,\"lastname\": \"tianzh\",\"username\": \"虎哥6\",\"state\": \"1\"}," +
                "    {\"id\": 10,\"lastname\": \"tianzh\",\"username\": \"虎哥5\",\"state\": \"1\"}," +
                "    {\"id\": 11,\"lastname\": \"tianzh\",\"username\": \"虎哥4\",\"state\": \"1\"}," +
                "    {\"id\": 12, \"lastname\": \"tianzh\",\"username\": \"虎哥3\",\"state\": \"1\"}," +
                "    {\"id\": 13,\"lastname\": \"tianzh\",\"username\": \"虎哥2\",\"state\": 1}," +
                "    {\"id\": 14, \"lastname\": \"tianzh\",\"username\": \"虎哥1\", \"state\": \"0\"}" +
                "  ]" +
                "}";
        JSONObject jsonobj=JSONObject.fromObject(json);//将字符串转化成json对象

        return jsonobj.toString();
    }
    @RequestMapping(value = "/toListJson",method = RequestMethod.POST)
    @ResponseBody
    public String toListJson(){
        String json = "{ \"total\": 100, \"rows\": [ {\"id\": 1, \"lastname\": \"tianzh\", \"state\": \"1\",  \"username\": \"虎哥\"}," +
                " { \"id\": 2, \"lastname\": \"tianzh\", \"username\": \"虎哥1\", \"state\": \"2\" }," +
                "    {\"id\": 3,\"lastname\": \"tianzh\",\"username\": \"虎哥5\",\"state\": \"0\" }," +
                "    { \"id\": 4, \"lastname\": \"tianzh\", \"username\": \"虎哥4\", \"state\": \"0\"}," +
                "    { \"id\": 5,\"lastname\": \"tianzh\",\"username\": \"虎哥3\",\"state\": \"0\" }," +
                "    { \"id\": 6, \"lastname\": \"tianzh\",\"username\": \"虎哥1\",\"state\": \"0\"}," +
                "    {\"id\": 7,\"lastname\": \"tianzh\",\"username\": \"虎哥\",\"state\": \"1\"}," +
                "    {\"id\": 8,\"lastname\": \"tianzh\",\"username\": \"虎哥7\", \"state\": \"0\"}," +
                "    {\"id\": 9,\"lastname\": \"tianzh\",\"username\": \"虎哥6\",\"state\": \"1\"}," +
                "    {\"id\": 10,\"lastname\": \"tianzh\",\"username\": \"虎哥5\",\"state\": \"1\"}," +
                "    {\"id\": 11,\"lastname\": \"tianzh\",\"username\": \"虎哥4\",\"state\": \"1\"}," +
                "    {\"id\": 12, \"lastname\": \"tianzh\",\"username\": \"虎哥3\",\"state\": \"1\"}," +
                "    {\"id\": 13,\"lastname\": \"tianzh\",\"username\": \"虎哥2\",\"state\": 1}," +
                "    {\"id\": 14, \"lastname\": \"tianzh\",\"username\": \"虎哥1\", \"state\": \"0\"}" +
                "  ]" +
                "}";
        JSONObject jsonobj=JSONObject.fromObject(json);//将字符串转化成json对象

        return jsonobj.toString();
    }
//    @ResponseBody
//    @RequestMapping(params = "method=listUsers")
//    public Object listUsers(UserForm form) {
//        int totalCount = userService.getTotalCount(form);
//        if(totalCount > 0){
//            BasePageResult<User> result = new BasePageResult<User>();
//            form.setBeginNum(((form.getPageindex()-1)*form.getPageSize()));
//            form.setEndNum((form.getPageindex()*form.getPageSize()));
//            List<User> user = userService.getUserList2(form);
//            result.setTotalCount(totalCount);
//            result.setResult(user);
//            return result;
//        }
//        return null;
//    }
    @RequestMapping(value = "/jsTree",method = RequestMethod.GET)
    public String toJsTree(){
        return "template/tree";
    }

    @RequestMapping(value = "/treeList",method = RequestMethod.GET)
    @ResponseBody
    public String treeList(){
        ZTreeNode zTreeNode = new ZTreeNode();
        JSONObject  jsonObject  = new JSONObject();
        List<ZTreeNode> zTreeNodes = Lists.newArrayList();
        zTreeNode.setId(0);
        zTreeNode.setName("父节点");
        zTreeNode.setpId(0);
        zTreeNode.setFlag(1);
        zTreeNode.setOpen(true);
        zTreeNodes.add(zTreeNode);

        zTreeNode = new ZTreeNode();
        zTreeNode.setId(1);
        zTreeNode.setName("次父节点1");
        zTreeNode.setpId(0);
        zTreeNode.setFlag(1);
        zTreeNode.setOpen(true);
        zTreeNodes.add(zTreeNode);

        zTreeNode = new ZTreeNode();
        zTreeNode.setId(2);
        zTreeNode.setName("子节点1");
        zTreeNode.setpId(1);
        zTreeNode.setFlag(1);
        zTreeNode.setOpen(true);
        zTreeNodes.add(zTreeNode);

        zTreeNode = new ZTreeNode();
        zTreeNode.setId(3);
        zTreeNode.setName("子节点2");
        zTreeNode.setpId(1);
        zTreeNode.setFlag(1);
        zTreeNode.setOpen(true);
        zTreeNodes.add(zTreeNode);

        zTreeNode = new ZTreeNode();
        zTreeNode.setId(4);
        zTreeNode.setName("次父节点2");
        zTreeNode.setpId(0);
        zTreeNode.setFlag(1);
        zTreeNode.setOpen(true);
        zTreeNodes.add(zTreeNode);

        zTreeNode = new ZTreeNode();
        zTreeNode.setId(5);
        zTreeNode.setName("子节点3");
        zTreeNode.setpId(4);
        zTreeNode.setFlag(1);
        zTreeNode.setOpen(true);
        zTreeNodes.add(zTreeNode);

        jsonObject.put("zTreeNodes",zTreeNodes);
        return jsonObject.toString();
    }

    @RequestMapping(value = "/edit",method = RequestMethod.GET)
    public String edit(){
        return "template/uEditor";
    }
}
