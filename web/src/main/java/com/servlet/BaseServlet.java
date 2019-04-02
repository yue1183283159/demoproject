package com.servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BaseServlet extends HttpServlet
{

	private static final long serialVersionUID = 1L;

	public BaseServlet()
	{
		super();
	}

	@Override
	public void init()
	{
		System.out.println("servlet context was inited.");
		String connStr = this.getServletContext().getInitParameter("connectionString");
		String user = this.getServletContext().getInitParameter("user");
		String pwd = this.getServletContext().getInitParameter("pwd");

		System.out.println("url:" + connStr);
		System.out.println("user:" + user);
		System.out.println("password:" + pwd);
	}

	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");

		String methodName = request.getParameter("method");
		if (methodName == null || methodName.trim().isEmpty())
		{
			throw new RuntimeException("Missing method parameter, can't execute.");
		}
		Class c = this.getClass();
		Method method = null;
		try
		{
			method = c.getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);

		} catch (Exception e)
		{
			throw new RuntimeException("The " + methodName + "(HttpServletRequest,HttpServletResponse) doesn't exist.");
		}

		String text = null;
		try
		{
			text = (String) method.invoke(this, request, response);
		} catch (Exception e)
		{
			System.out.println("The " + methodName + "(HttpServletRequest,HttpServletResponse) inner throw exception");
			throw new RuntimeException(e);
		}

		if (text == null || text.trim().isEmpty())
		{
			return;
		}
		if (!text.contains(":"))
		{
			request.getRequestDispatcher(text).forward(request, response);
		} else
		{
			int index = text.indexOf(":");
			String bz = text.substring(0, index);
			String path = text.substring(index + 1);
			if (bz.equalsIgnoreCase("r"))
			{
				response.sendRedirect(path);
			} else if (bz.equalsIgnoreCase("f"))
			{
				request.getRequestDispatcher(path).forward(request, response);
			} else
			{
				throw new RuntimeException("can't arrive the " + path);
			}
		}

	}

}
