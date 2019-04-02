package com.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class MyContextListener implements ServletContextListener
{
	SysService sysService = new SysService();

	public void contextInitialized(ServletContextEvent e)
	{
		System.out.println("context对象创建了");
		sysService.initTable();
		System.out.println("初始化成功");
	}

	public void contextDestroyed(ServletContextEvent e)
	{
		System.out.println("context对象销毁了");
		sysService.clearTable();
		System.out.println("清除成功");
	}
}

class SysService
{
	public void initTable()
	{
		System.out.println("Created system tables..");
	}

	public void clearTable()
	{
		System.out.println("Cleared the system tables");
	}
}
