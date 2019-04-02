package com.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author:Administrator
 * @date:2018年8月19日
 */
public class TestResponseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//response.setStatus(200);
		//response.setHeader("Context-Type", "text/html;charset=utf-8");
		response.setContentType("text/html;charset=utf-8");
		//通知浏览器使用utf-8编码来接收数据。
		response.setCharacterEncoding("utf-8");
		
		ServletOutputStream out= response.getOutputStream();
		out.write("哈喽".getBytes());
		
		//response.getWriter().write("哈喽");
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	
		//重定向,服务器发送两次请求，对应两次响应，request对象不是同一个。
		response.setStatus(302);
		response.setHeader("location", request.getContextPath()+"/index.html");
		response.sendRedirect(request.getContextPath()+"/index.html");
		//定时刷新
		response.getWriter().write("3秒之后将会跳转到主页。。。。");
		response.setHeader("refresh", "3,url="+request.getContextPath()+"/index.html");
		
		
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
