package com.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//@WebServlet("/Account")
public class AccountServlet extends BaseServlet
{
	private static final long serialVersionUID = 1L;

	public AccountServlet()
	{
		super();
	}

	public void login(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		String name = request.getParameter("name");
		String passwd = request.getParameter("pwd");
		//AccountService accountService = new AccountService();
		//boolean result = accountService.login(name, EncryUtil.md5Password(passwd));
		boolean result=true;
		if (result)
		{
			HttpSession session = request.getSession();
			session.setAttribute("username", name);
			response.sendRedirect(request.getContextPath() + "/main.jsp");
		} else
		{
			request.setAttribute("loginMsg", "Login failed. Please login again.");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		}

	}

	public void register(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		String name = request.getParameter("name");
		String passwd = request.getParameter("pwd");
		String gender = request.getParameter("gender");
		String email = request.getParameter("email");
		SysUser user = new SysUser();

		user.setName(name);
		user.setPwd(passwd);
		user.setGender(gender);
		user.setEmail(email);
		user.setBirthDate("2007-09-23");
		//AccountService accountService = new AccountService();
		//int result = accountService.addUser(user);
		int result=1;
		if (result > 0)
		{
			request.setAttribute("registerMsg", "Register successed. Please login.");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
			// return "r:" + request.getContextPath() + "/login.jsp";
		} else
		{
			request.getRequestDispatcher("/register.jsp").forward(request, response);
		}
		// return "r:" + request.getContextPath() + "/register.jsp";
	}

	public void getUserList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		//AccountService accountService = new AccountService();
		//List<SysUser> userList = accountService.getSysUsers();
		List<String> userList=new ArrayList<>();
		userList.add("张三");
		userList.add("李四");
		userList.add("王五");
		
		request.setAttribute("userList", userList);
		request.getRequestDispatcher("/userlist.jsp").forward(request, response);
	}

}

