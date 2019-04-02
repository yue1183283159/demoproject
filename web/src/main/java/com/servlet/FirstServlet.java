package com.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class FirstServlet extends HttpServlet {
	//用来定义序列化的版本号
	private static final long serialVersionUID = 1L;
	
	/**
	 *当浏览器发送来请求访问FirstSerlvet时，如果请求方式是get，就调用doGet方法；如果是post提交就调用doPost方法
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-mm-dd hh:MM:ss");
		response.getWriter().append("Served at: ")
							.append(request.getContextPath())
							.append("   "+ sdf.format(new Date()));
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

//public class FirstServlet implements Servlet {
//
//	@Override
//	public void destroy() {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public ServletConfig getServletConfig() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public String getServletInfo() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public void init(ServletConfig arg0) throws ServletException {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void service(ServletRequest arg0, ServletResponse arg1) throws ServletException, IOException {
//		// TODO Auto-generated method stub
//
//	}
//}