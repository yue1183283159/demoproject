package com.servlet;

import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author:Administrator
 * @date:2018年8月12日
 */
public class TestRequestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//
		StringBuffer requestUrl = request.getRequestURL();
		String ip = request.getRemoteAddr();
		request.getRemoteHost();
		String userAgent = request.getHeader("User-Agent");

		//
		// String id = request.getParameter("id");
		Map<String, String[]> paramMap = request.getParameterMap();
		Enumeration<String> paramNames = request.getParameterNames();
		String[] values = request.getParameterValues("like");

		System.out.println(paramMap);
		System.out.println(paramNames);

		// 通用的方式是，手动编码获取的参数来解决乱码
		String username = request.getParameter("username");
		// 将乱码逆向编码转回二进制数组
		byte[] bytes = username.getBytes("iso8859-1");
		// 手动编码，将二进制数组转换成字符串
		username = new String(bytes, "utf-8");
		// 这种方式对get、post提交的参数都可以解决乱码问题。
		System.out.println(username);

		// 如果发送的请求方式是post提交，可以通过request提供的方法来接解决乱码
		request.setCharacterEncoding("utf-8");
		// 通知服务器使用utf-8来接收请求实体内容中的数据
		// 由于get提交的参数在请求资源路径后面拼接，不在实体内容中，这种方式不起作用。
		String uname = request.getParameter("username");
		System.out.println(uname);

		response.getWriter().append("Served at: ").append(request.getContextPath());

		// 请求转发。转发前后，request对象是同一个，转发前后地址栏不会发生变化，只能是同一个web应用内部资源的跳转。
		// 不可以是不同的web应用或是不同的虚拟主机资源之间的跳转。
		request.getRequestDispatcher("/url").forward(request, response);

		// 域对象：如果一个对象，具有一个可以被访问的范围，在这个范围内，利用自身的map集合，可以实现资源的共享
		request.setAttribute("name", username);
		String loginUser = (String) request.getAttribute("name");
		
		Enumeration<String> names = request.getAttributeNames();
		while (names.hasMoreElements()) {
			String n = (String) names.nextElement();
			System.out.println(n);
		}
		//域对象 生命周期-一次请求开始创建request对象到相应结束request请求
		//作用范围：整个请求链中。功能：整个范围内实现资源共享
		
		
		//请求包含：服务器内部资源合并的效果。
		request.getRequestDispatcher("/url").include(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
