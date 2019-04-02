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
public class ServletMain extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setAttribute("name", "101");
		request.setAttribute("age", 23);

		/*Enumeration<String> aNames = request.getAttributeNames();
		while (aNames.hasMoreElements()) {
			String aname = (String) aNames.nextElement();
			System.out.println(aname);
		}*/

		request.getRequestDispatcher("/ForwardServlet").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
