package com.thread;

public class Connection
{
	static boolean isConnected = false;

	public void connect()
	{
		try
		{
			String threadName = Thread.currentThread().getName();
			long time = System.currentTimeMillis();
			System.out.println(threadName + " " + time + " start connect");
			Thread.currentThread().sleep(1000);
			isConnected = true;
			time = System.currentTimeMillis();
			System.out.println(threadName + " " + time + " connected.");
			
		} catch (Exception e)
		{
			// TODO: handle exception
		}finally {
			synchronized (ThreadMain.objLock)
			{
				//让执行this.wait的线程继续执行
				ThreadMain.objLock.notify();
			}
		}
	}
}
