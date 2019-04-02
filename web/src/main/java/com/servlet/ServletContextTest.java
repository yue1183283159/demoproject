package com.servlet;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author:Administrator
 * @date:2018年8月19日
 */
public class ServletContextTest extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//代表整个web应用的对象，一个web应用只有一个ServletContext,保存web应用的环境信息。
		//this.getServletConfig().getServletContext()
		ServletContext context = this.getServletContext();
		context.setAttribute("count", 1);
		context.getAttribute("count");
		//context.getContextPath();
		//context.getRequestDispatcher("/url").forward(request, response);
		//context.getSessionCookieConfig();
		//context.getServerInfo();		
		
		//全局参数 context-param
		System.out.println(context.getInitParameter("url"));
		
		//servlet中的参数，init-param
		System.out.println(this.getInitParameter("s-url"));
		System.out.println(this.getServletConfig().getInitParameter("s-url"));

		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
