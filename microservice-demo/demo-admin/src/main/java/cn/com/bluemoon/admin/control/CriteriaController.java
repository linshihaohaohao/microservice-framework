package cn.com.bluemoon.admin.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.bluemoon.admin.domain.vo.UserInfo;
import cn.com.bluemoon.common.org.vo.StaffInfo;
import cn.com.bluemoon.service.common.service.EmpService;

import com.alibaba.dubbo.config.annotation.Reference;

@Controller
@RequestMapping("/criteria")
public class CriteriaController {
	
	@Reference(timeout=30000,check=false)
	private EmpService empService;
	
	@ResponseBody
	@RequestMapping(value="/query", method=RequestMethod.POST)
	public String queryCriteria(HttpServletRequest request,@RequestBody JSONObject jsonObject) throws Exception{
		String operatorId = jsonObject.has("operatorId")?jsonObject.getString("operatorId"):null;
		String responseMsg = "网络繁忙，请稍后再试";
		int responseCode = -1;
		Boolean isSuccess = Boolean.FALSE;
		Map<String, Object> result = new HashMap<String, Object>();
		JSONArray ja = new JSONArray();
		int total = 0;
		try {
			UserInfo user = (UserInfo) request.getSession().getAttribute("user");
			if (user != null) {
				JSONObject search = new JSONObject();
				search.put("queryStr", operatorId);
				Map<String,Object> userResult = empService.searchEmpInfo(search);
				responseCode = (Integer)userResult.get("responseCode");
				if(responseCode == 0){
					@SuppressWarnings("unchecked")
					List<StaffInfo> stList = (List<StaffInfo>)userResult.get("data");
					if(stList != null && stList.size() > 0){
						total = stList.size();
						for(StaffInfo vo : stList){
							ja.add(vo);
						}
					}
				}
				responseMsg = userResult.get("responseMsg").toString();
				isSuccess = (Boolean)userResult.get("isSuccess");
			} else {
				responseMsg = "请先登录";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		result.put("responseMsg", responseMsg);
		result.put("responseCode", responseCode);
		result.put("isSuccess", isSuccess);
		JSONObject jsonobj= new JSONObject();
		jsonobj.put("total", total);
		jsonobj.put("rows", ja);
		jsonobj.put("responseMsg", responseMsg);
		jsonobj.put("responseCode", responseCode);
		jsonobj.put("isSuccess", isSuccess);
		return jsonobj.toString();
	}
}

