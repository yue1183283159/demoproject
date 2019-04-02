package com.common.sso;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.client.HttpClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ClientController {

	@RequestMapping("")
	public String login(HttpServletRequest request) {
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		//经过base64加密的密码
		//HttpClient 发送登录数据到认证中心进行认证登录
		return "";
	}
}
