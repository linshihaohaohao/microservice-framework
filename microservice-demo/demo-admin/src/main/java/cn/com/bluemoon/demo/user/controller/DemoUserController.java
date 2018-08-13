package cn.com.bluemoon.demo.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/demoUser")
public class DemoUserController {

	@RequestMapping(value = "/list", method = RequestMethod.GET)
    public String userList(String username){
        return "demo/user/userList";
    }
	
	@RequestMapping(value = "/update", method = RequestMethod.GET)
    public String userUpdate() {
        return "demo/user/userUpdate";
    }
}
