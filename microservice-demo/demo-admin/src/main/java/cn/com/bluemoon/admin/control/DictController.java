package cn.com.bluemoon.admin.control;

import java.util.ArrayList;
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

import cn.com.bluemoon.admin.domain.vo.SysDictEntryVo;
import cn.com.bluemoon.admin.domain.vo.SysDictInfoVo;
import cn.com.bluemoon.admin.domain.vo.SysDictTypeVo;
import cn.com.bluemoon.admin.domain.vo.UserInfo;
import cn.com.bluemoon.admin.service.DictService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 
 * @author Ace
 *
 */
@Controller
@RequestMapping("/dict")
public class DictController{
	
	@Autowired
	private DictService dictService;
	
	@RequestMapping(value = "/dictList",method = RequestMethod.GET)
    public String dictList(HttpSession session){
        return "template/dictList";
    }
	
    @RequestMapping(value = "/dictTypeAdd",method = RequestMethod.GET)
    public String dictTypeAdd(HttpSession session){
    	UserInfo userInfo = (UserInfo)session.getAttribute("user");
    	if(userInfo == null){
    		return "login";
    	}
        return "template/dictTypeAdd";
    }
    
    @RequestMapping(value = "/toDictTypeUpdate",method = RequestMethod.GET)
    public String toDictTypeUpdate(HttpSession session){
    	UserInfo userInfo = (UserInfo)session.getAttribute("user");
    	if(userInfo == null){
    		return "login";
    	}
        return "template/dictTypeUpdate";
    }
    
    @RequestMapping(value = "/toDictEntryList",method = RequestMethod.GET)
    public String toDictEntryList(HttpSession session){
    	UserInfo userInfo = (UserInfo)session.getAttribute("user");
    	if(userInfo == null){
    		return "login";
    	}
        return "template/dictEntryList";
    }
	
    @RequestMapping(value = "/dictEntryAdd",method = RequestMethod.GET)
    public String dictEntryAdd(HttpSession session){
        return "template/dictEntryAdd";
    }
    
    @RequestMapping(value = "/toDictEntryUpdate",method = RequestMethod.GET)
    public String toDictEntryUpdate(HttpSession session){
    	UserInfo userInfo = (UserInfo)session.getAttribute("user");
    	if(userInfo == null){
    		return "login";
    	}
        return "template/dictEntryUpdate";
    }
    
    
	@ResponseBody
	@RequestMapping(value="/getDictTypeList", method=RequestMethod.POST)
	public String getDictTypeList(HttpServletRequest request, @RequestBody JSONObject jsonObject) throws Exception{
		String dicttypeid = jsonObject.has("dicttypeid")?jsonObject.getString("dicttypeid"):null;
		String dicttypename = jsonObject.has("dicttypename")?jsonObject.getString("dicttypename"):null;
		int offset = Integer.parseInt(jsonObject.getString("offset"));
		int pageSize = Integer.parseInt(jsonObject.getString("pageSize"));
		String sortOrder = jsonObject.getString("sortOrder");
		String sortName = jsonObject.getString("sortName");
		List<SysDictTypeVo> list = new ArrayList<SysDictTypeVo>();
		int total = 0;
		list = dictService.getDictTypeList(dicttypeid, dicttypename, offset, pageSize,sortName,sortOrder);
    	JSONArray ja = new JSONArray();
    	if(list != null && list.size() > 0){
    		for(int i=0;i<list.size();i++){
				ja.add(list.get(i));
			}
    		total = dictService.getDictTypeCount(dicttypeid, dicttypename);
    	}
		JSONObject jsonobj= new JSONObject();
		jsonobj.put("total", total);
		jsonobj.put("rows", ja);
    	return jsonobj.toString();
    }
	
	@ResponseBody
	@RequestMapping(value="/getDictEntryList", method=RequestMethod.POST)
	public String getDictEntryList(HttpServletRequest request, @RequestBody JSONObject jsonObject) throws Exception{
		String dicttypeid = jsonObject.has("dicttypeid")?jsonObject.getString("dicttypeid"):null;
		int offset = Integer.parseInt(jsonObject.getString("offset"));
		int pageSize = Integer.parseInt(jsonObject.getString("pageSize"));
		String sortOrder = jsonObject.getString("sortOrder");
		String sortName = jsonObject.getString("sortName");
		List<SysDictEntryVo> list = new ArrayList<SysDictEntryVo>();
		int total = 0;
		System.out.println("----------------dicttypeid:"+dicttypeid);
		list = dictService.getDictEntryList(dicttypeid, offset, pageSize, sortName, sortOrder);
    	JSONArray ja = new JSONArray();
    	if(list != null && list.size() > 0){
    		for(int i=0;i<list.size();i++){
				ja.add(list.get(i));
			}
    		total = dictService.getDictEntryCount(dicttypeid);
    	}
		JSONObject jsonobj= new JSONObject();
		jsonobj.put("total", total);
		jsonobj.put("rows", ja);
    	return jsonobj.toString();
    }
	
	@ResponseBody
	@RequestMapping(value="/saveDictType")
	public Map<String, Object> saveDictType(@RequestBody JSONObject jsonObject){
		String responseMsg = "网络繁忙，请稍后再试";
		int responseCode = -1;
		Boolean isSuccess = Boolean.FALSE;
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("responseMsg", responseMsg);
		result.put("responseCode", responseCode);
		result.put("isSuccess", isSuccess);
		
		String dicttypeid = jsonObject.has("dicttypeid")?jsonObject.getString("dicttypeid"):"";
		String dicttypename = jsonObject.has("dicttypename")?jsonObject.getString("dicttypename"):"";
		String type = jsonObject.has("type")?jsonObject.getString("type"):"";
		
		if( "".equals(dicttypeid) || dicttypeid == null || "".equals(dicttypename) || dicttypename == null 
				|| type == null || "".equals(type) ){
			result.put("responseMsg", "非法参数");
			result.put("responseCode", 2202);
			return result;
		}
		
		try {
			if( "add".equals(type) ){
				//先检查是否已经存在该业务字典
				List<SysDictInfoVo> dictList = dictService.getDictInfoByTypePc(dicttypeid, 1);
				int count = dictService.getDictTypeCount(dicttypeid, null);
				if((dictList==null || dictList.size() == 0) && count == 0){
					SysDictTypeVo mallSysDictType = new SysDictTypeVo();
					mallSysDictType.setDicttypeid(dicttypeid);
					mallSysDictType.setDicttypename(dicttypename);
					if( dictService.addDictType(mallSysDictType) == true ){
						result.put("responseMsg", "请求成功");
						result.put("responseCode", 0);
						result.put("isSuccess", Boolean.TRUE);
						return result;
					}else{
						result.put("responseMsg", "保存失败");
						result.put("responseCode", 1201);
					}
				}else{
					result.put("responseMsg", "记录已经存在");
					result.put("responseCode", 2202);
				}
			}else{
				//编辑
				SysDictTypeVo mallSysDictType = new SysDictTypeVo();
				mallSysDictType.setDicttypeid(dicttypeid);
				mallSysDictType.setDicttypename(dicttypename);
				if( dictService.updateDictType(mallSysDictType) ){
					result.put("responseMsg", "请求成功");
					result.put("responseCode", 0);
					result.put("isSuccess", Boolean.TRUE);
					return result;
				}else{
					result.put("responseMsg", "保存失败");
					result.put("responseCode", 1201);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			result.put("responseMsg", "保存失败");
			result.put("responseCode", 1202);
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value="/deleteDictType")
	public Map<String, Object> deleteDictType(@RequestBody JSONObject jsonObject){
		String responseMsg = "网络繁忙，请稍后再试";
		int responseCode = -1;
		Boolean isSuccess = Boolean.FALSE;
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("responseMsg", responseMsg);
		result.put("responseCode", responseCode);
		result.put("isSuccess", isSuccess);
		
		String dicttypeid = jsonObject.has("DICTTYPEID")?jsonObject.getString("DICTTYPEID"):"";
		
		if( "".equals(dicttypeid) || dicttypeid == null ){
			result.put("responseMsg", "非法参数");
			result.put("responseCode", 2202);
			return result;
		}
		try {
			SysDictTypeVo mallSysDictType = new SysDictTypeVo();
			mallSysDictType.setDicttypeid(dicttypeid);
			List<SysDictTypeVo> list = new ArrayList<SysDictTypeVo>();
			list.add(mallSysDictType);
			dictService.deleteDictType(list);
			
			result.put("responseMsg", "请求成功");
			result.put("responseCode", 0);
			result.put("isSuccess", Boolean.TRUE);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value="/saveDictEntry")
	public Map<String, Object> saveDictEntry(HttpServletRequest request){
		String responseMsg = "网络繁忙，请稍后再试";
		int responseCode = -1;
		Boolean isSuccess = Boolean.FALSE;
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("responseMsg", responseMsg);
		result.put("responseCode", responseCode);
		result.put("isSuccess", isSuccess);
		String type = request.getParameter("dicttype") != null?request.getParameter("dicttype"):"";
		
		if( type == null || "".equals(type) ){
			result.put("responseMsg", "非法参数");
			result.put("responseCode", 2202);
			return result;
		}
		
		try {
			SysDictEntryVo mallSysDictEntry = new SysDictEntryVo();
			mallSysDictEntry.setDictid(request.getParameter("dictid"));
			mallSysDictEntry.setDictname(request.getParameter("dictname"));
			mallSysDictEntry.setDicttypeid(request.getParameter("dicttypeid"));
			mallSysDictEntry.setStatus(Integer.valueOf(request.getParameter("status")));
			mallSysDictEntry.setSortno(Integer.valueOf(request.getParameter("sortno")));
			if( "add".equals(type) ){
				//检查新增的业务字典项是否已经存在
				if( dictService.checkDictEntry(mallSysDictEntry.getDicttypeid(), mallSysDictEntry.getDictid())){
					result.put("responseMsg", "业务字典项已经存在");
					result.put("responseCode", 2202);
				}else{
					if( dictService.addDictEntry(mallSysDictEntry) ){
						result.put("responseMsg", "请求成功");
						result.put("responseCode", 0);
						result.put("isSuccess", Boolean.TRUE);
						return result;
					}else{
						result.put("responseMsg", "保存失败");
						result.put("responseCode", 1201);
					}
				}
			}else{
				//修改
				if( dictService.updateDictEntry(mallSysDictEntry) ){
					result.put("responseMsg", "请求成功");
					result.put("responseCode", 0);
					result.put("isSuccess", Boolean.TRUE);
					return result;
				}else{
					result.put("responseMsg", "保存失败");
					result.put("responseCode", 1201);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value="/deleteDictEntry")
	public Map<String, Object> deleteDictEntry(@RequestBody JSONObject jsonObject){
		String responseMsg = "网络繁忙，请稍后再试";
		int responseCode = -1;
		Boolean isSuccess = Boolean.FALSE;
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("responseMsg", responseMsg);
		result.put("responseCode", responseCode);
		result.put("isSuccess", isSuccess);
		String dicttypeid = jsonObject.getString("DICTTYPEID");
		String dictid = jsonObject.getString("dictid");
		try {
			SysDictEntryVo vo = new SysDictEntryVo();
			vo.setDictid(dictid);
			vo.setDicttypeid(dicttypeid);
			List<SysDictEntryVo> list = new ArrayList<SysDictEntryVo>() ;
			list.add(vo);
			dictService.deleteDictEntry(list);
			
			result.put("responseMsg", "请求成功");
			result.put("responseCode", 0);
			result.put("isSuccess", Boolean.TRUE);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			result.put("responseMsg", "删除失败");
			result.put("responseCode", 1201);
		}
		
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value="/reloadDictCache")
	public Map<String, Object> reloadDictCache(@RequestBody JSONObject jsonObject){
		String responseMsg = "网络繁忙，请稍后再试";
		int responseCode = -1;
		Boolean isSuccess = Boolean.FALSE;
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("responseMsg", responseMsg);
		result.put("responseCode", responseCode);
		result.put("isSuccess", isSuccess);
		
		String dicttypeid = jsonObject.has("DICTTYPEID")?jsonObject.getString("DICTTYPEID"):"";
		
		if( "".equals(dicttypeid) || dicttypeid == null ){
			result.put("responseMsg", "非法参数");
			result.put("responseCode", 2202);
			return result;
		}
		
		try {
			
			if ( dictService.reloadDictCache(dicttypeid) ){
				result.put("responseMsg", "请求成功");
				result.put("responseCode", 0);
				result.put("isSuccess", Boolean.TRUE);
				return result;
			}else{
				result.put("responseMsg", "刷新缓存失败");
				result.put("responseCode", 2202);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value="/getDictEntryListToSelect", method=RequestMethod.POST)
	public String getDictEntryListToSelect(HttpServletRequest request) throws Exception{
		String dicttypeid = request.getParameter("dicttypeid");
		List<SysDictEntryVo> list = new ArrayList<SysDictEntryVo>();
		int responseCode = -1;
		JSONArray ja = new JSONArray();
		if(dicttypeid != null && !"".equals(dicttypeid)){
			list = dictService.getDictEntryListToSelect(dicttypeid);
	    	if(list != null && list.size() > 0){
	    		for(int i=0;i<list.size();i++){
					ja.add(list.get(i));
				}
	    		responseCode = 0;
	    	}
		}
		JSONObject jsonobj= new JSONObject();
		jsonobj.put("entryList", ja);
		jsonobj.put("responseCode", responseCode);
    	return jsonobj.toString();
    }

}
