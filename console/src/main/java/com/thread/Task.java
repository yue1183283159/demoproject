package com.thread;

public class Task implements Runnable
{

	// 锁，给运行这个run方法的this加锁
	// 有个线程开始运行这个方法计数器加1，运行完减1
	// 为0时，别的线程才能执行run方法
	// 对象锁 synchronized同步

//	@Override
//	public synchronized void run()
//	{
//		try
//		{
//			String threadName = Thread.currentThread().getName();
//			long time = System.currentTimeMillis() / 1000;
//			System.out.println(threadName + " choose cloth.");
//
//			System.out.println(threadName + " try on cloth");
//			Thread.currentThread().sleep(5000);
//			time = System.currentTimeMillis() / 1000;
//			System.out.println(threadName + " end try on cloth.");
//
//		} catch (Exception e)
//		{
//			e.printStackTrace();
//		}
//
//	}

	@Override
	public void run()
	{
		try
		{
			String threadName = Thread.currentThread().getName();
			long time = System.currentTimeMillis() / 1000;
			System.out.println(threadName + " " + time + " choose cloth.");
			synchronized (this)
			{
				System.out.println(threadName + " " + time + " try on cloth");
				Thread.currentThread().sleep(5000);
				time = System.currentTimeMillis() / 1000;
				System.out.println(threadName + " " + time + " end try on cloth.");
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		}

	}

}
