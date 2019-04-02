package com.listener;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class MyRequestListener implements ServletRequestListener
{
	public void requestInitialized(ServletRequestEvent e)
	{
		//System.out.println("request object was created.");
		HttpServletRequest request = (HttpServletRequest) e.getServletRequest();
		String ip = request.getRemoteHost();
		HttpSession session = request.getSession(true);
		session.setAttribute("ip", ip);
	}

	public void requestDestroyed(ServletRequestEvent e)
	{
		//System.out.println("request object was destroyed.");
	}
}
