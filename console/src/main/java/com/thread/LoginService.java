package com.thread;

public class LoginService
{
	public void login() throws Exception
	{
		String threadName = Thread.currentThread().getName();
		long time = System.currentTimeMillis();
		System.out.println(threadName + " " + time + " start login.");
		// if can't connect, wait(wait must in synchronized block)
		if (!Connection.isConnected)
		{
			synchronized (ThreadMain.objLock)
			{
				ThreadMain.objLock.wait();
			}
		}
		if (Connection.isConnected)
		{
			System.out.println(threadName + " login success.");
		} else
		{
			System.out.println(threadName + " login failed.");
		}
	}
}
