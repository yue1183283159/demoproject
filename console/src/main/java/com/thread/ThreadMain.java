package com.thread;

public class ThreadMain
{
	// 全局对象,保证wait 和notify使用的锁对象是同一个。不能使用基本类型，必须是对象，引用类型
	public static String objLock = "objtLock";

	public static void main(String[] args)
	{
		// Task task = new Task();
		// //只有一个试衣间，如果不控制，就会出现多个人在同一时间使用同一个试衣间的情况
		// Thread thread = new Thread(task, "zhangsan");
		// thread.start();
		//
		// Thread thread2 = new Thread(task, "lisi");
		// thread2.start();

		// single thread, order run
		// Connection connection = new Connection();
		// connection.connect();
		// LoginService loginService = new LoginService();
		// loginService.login();

		ConnectionThread connectionThread = new ConnectionThread("ConnectThread");
		connectionThread.start();

		LoginTread loginTread = new LoginTread("LoginThread");
		loginTread.start();
		
		ConnectionThread connectionThread2 = new ConnectionThread("ConnectThread2");
		connectionThread2.start();

		LoginTread loginTread2 = new LoginTread("LoginThread2");
		loginTread2.start();
	}

}

class ConnectionThread extends Thread
{
	public ConnectionThread(String name)
	{
		super(name);
	}

	@Override
	public void run()
	{
		Connection connection = new Connection();
		connection.connect();
	}

}

class LoginTread extends Thread
{
	public LoginTread(String name)
	{
		super(name);
	}

	@Override
	public void run()
	{
		LoginService loginService = new LoginService();
		try
		{
			loginService.login();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
