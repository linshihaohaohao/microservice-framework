package cn.com.bluemoon.demo.user.controller.api;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.com.bluemoon.demo.common.exception.AssertUtil;
import cn.com.bluemoon.demo.user.common.response.TokenGetResponse;
import cn.com.bluemoon.demo.user.model.DemoUser;
import cn.com.bluemoon.demo.user.service.DemoUserService;
import cn.com.bluemoon.demo.user.service.DemoUserTokenService;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class DemoUserController {

	private static Logger log = (Logger) LoggerFactory.getLogger(DemoUserController.class);
	
	@Autowired
	private DemoUserService userService;
	@Autowired
	private DemoUserTokenService tokenService;
	
	@ResponseBody
	@RequestMapping(value = "/regist", method = RequestMethod.POST)
	public TokenGetResponse regist(HttpServletRequest request, @RequestBody JSONObject jsonObj) {
		String account = jsonObj.has("account") ? jsonObj.getString("account") : "";
		AssertUtil.hasLength(account, 1101, "非法请求参数");
		String password = jsonObj.has("password") ? jsonObj.getString("password") : "";
		AssertUtil.hasLength(password, 1101, "非法请求参数");
		
		DemoUser user = userService.getUserByAccount(account);
		AssertUtil.isNull(user, 2201, "该账号已存在");
		
		user = new DemoUser();
		user.setAccount(account);
		user.setPassword(password);
		Long userId = userService.addUser(user);
		AssertUtil.notNull(userId, 1201, "数据库写入异常");
		
		String token = tokenService.createUserToken(userId);
		AssertUtil.notNull(token, 1201, "数据库写入异常");
		
		TokenGetResponse response = new TokenGetResponse(true, 0, "请求成功");
		response.setToken(token);
		return response;
	}
}
