package com.servlet;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author:Administrator
 * @date:2018年8月19日
 */
public class ForwardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Enumeration<String> aNames = request.getAttributeNames();
		while (aNames.hasMoreElements()) {
			String aname = (String) aNames.nextElement();
			System.out.println(request.getAttribute(aname));
			/*if(!aname.contains("forward")) 
			{
				System.out.println(aname);
			}	*/		
		}

		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
