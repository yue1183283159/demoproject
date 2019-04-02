package com.common.sso;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/usercenter/")
public class UserLoginCenterController {

	@Autowired
	private IUserService userServiceImpl;

	@RequestMapping("/login")
	@ResponseBody
	public String doLogin(@RequestParam("u") String username, @RequestParam("p") String password) {
		// 有ticket返回，表明登录成功，没有表明登录失败
		// 客户端拿到ticket的之后，可以认为登录成功，可以存入session中。访问其他资源的时候，可检测该ticket是否在session中
		// 当退出登录的时候，要通知中心，将ticket从redis中移除。
		String ticket = userServiceImpl.login(username, password);

		return ticket;
	}

	//
	@RequestMapping("logincheck")
	public void loginCheck(String clientUrl, String ticket) {
		userServiceImpl.checkLogin(ticket);
	}

	@RequestMapping("logout")
	public String logout(String ticket) {
		userServiceImpl.removeTicket(ticket);
		return "";
	}
}
