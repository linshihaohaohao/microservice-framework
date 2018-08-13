package cn.com.bluemoon.demo.user.controller.manager;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.com.bluemoon.demo.user.common.dto.DemoUserQueryDto;
import cn.com.bluemoon.demo.user.common.dto.DemoUserUpdateDto;
import cn.com.bluemoon.demo.user.model.DemoUser;
import cn.com.bluemoon.demo.user.service.DemoUserQueryService;
import cn.com.bluemoon.demo.user.service.DemoUserService;

@RestController
@RequestMapping("/UserManager")
public class DemoUserManagerController {

	@Autowired
	private DemoUserQueryService userQueryService;
	@Autowired
	private DemoUserService userService;
	
	@ResponseBody
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public JSONObject list(DemoUserQueryDto dto) {
		List<DemoUser> list = userQueryService.getListByPage(dto);
		int total = userQueryService.getListCount(dto);
		
		JSONObject jsonobj = new JSONObject();
        JSONArray rows = new JSONArray();
        if (list != null && list.size() > 0)
        {
            rows = JSONArray.fromObject(list);
        }
        jsonobj.put("total", total);
        jsonobj.put("rows", rows);
        return jsonobj;
	}
	
	@ResponseBody
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public JSONObject update(DemoUserUpdateDto dto) {
		JSONObject jsonobj = new JSONObject();
		
		DemoUser user = userService.getUserById(dto.getId());
		
		boolean flag = false;
		
		if (null != user) {
			flag = userService.updateUserIntroduction(user, dto.getIntroduction());
		}
		
        if (flag) {
            jsonobj.put("isSuccess", true);
            jsonobj.put("msg", "操作成功");
        } else {
            jsonobj.put("isSuccess", false);
            jsonobj.put("msg", "保存失败");
        }

        return jsonobj;
	}
}
