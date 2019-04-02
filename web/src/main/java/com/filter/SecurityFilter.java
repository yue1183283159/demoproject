package com.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 登录权限过滤器
 */
public class SecurityFilter implements Filter
{

	public SecurityFilter()
	{

	}

	public void destroy()
	{

	}

	public void init(FilterConfig fConfig) throws ServletException
	{

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException
	{
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		// session对象是否存在
		HttpSession session = request.getSession(false);
		if (session == null)
		{
			response.sendRedirect(request.getContextPath() + "noAuth.html");
			return;
		} else
		{
			String user = (String) session.getAttribute("user");
			if (user == null)
			{
				response.sendRedirect(request.getContextPath() + "noAuth.html");
				return;
			}
		}

		chain.doFilter(request, response);

	}

}
