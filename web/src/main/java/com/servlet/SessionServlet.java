package com.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author:Administrator
 * @date:2018年8月19日
 */
public class SessionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//如果服务器内部有为当前客户端服务的session，就会直接拿过来使用，如果没有对应的session，就会创建一个session。
		HttpSession session = request.getSession();
		//request.getSession(false); //如果有session，就直接使用；如果没有对应的session，返回null，不创建新的
		session.setAttribute("username", "张三");
		System.out.println(session.getAttribute("username"));
		session.removeAttribute("username");
		
		session.setMaxInactiveInterval(60*30);//30分钟，单位是秒
		
		//设置JSESSIONID的时间，不会随浏览器关闭而丢失。每次请求时将其保存在cookie中，并设置时间返回存到客户端。
		Cookie cookie=new Cookie("JSESSIONID", session.getId());
		cookie.setMaxAge(60*60*24*30);//保存一个月
		response.addCookie(cookie);
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
