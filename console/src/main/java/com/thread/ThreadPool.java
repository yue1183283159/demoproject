package com.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool
{
	static long stime = 0;
	static long etime = 0;

	static class Task4 implements Runnable
	{

		@Override
		public void run()
		{
			long curtime = System.currentTimeMillis();
			if (stime == 0)
			{
				stime = curtime;
			}

			if (curtime > stime)
			{
				etime = curtime;
			}

			System.out.println(Thread.currentThread().getId());
		}
	}

	public static void main(String[] args) throws Exception
	{
		//test1();
		test2();
	}

	private static void test2() throws Exception
	{
		Runtime runtime = Runtime.getRuntime();
		long sUseMemory = runtime.totalMemory() - runtime.freeMemory();
		
		ExecutorService threadPool = Executors.newFixedThreadPool(3);
		Task4 task4 = new Task4();
		for (int i = 0; i < 100000; i++)
		{
			threadPool.execute(task4);
		}
		Thread.currentThread().sleep(10000);
		System.out.println("time=" + (etime - stime));

		// 如果不关闭线程池，程序一直在运行。
		 //threadPool.shutdown();
		
		long eUseMemory = runtime.totalMemory() - runtime.freeMemory();
		System.out.println("memory=" + (eUseMemory - sUseMemory));
	}

	private static void test1() throws Exception
	{
		Runtime runtime = Runtime.getRuntime();
		long sUseMemory = runtime.totalMemory() - runtime.freeMemory();
		
		Task4 task4 = new Task4();
		for (int i = 0; i < 100000; i++)
		{
			Thread thread = new Thread(task4);
			thread.start();
		}
		Thread.currentThread().sleep(10000);
		System.out.println("time=" + (etime - stime));
		
		//每次运行占用内存不一样，取决垃圾回收
		long eUseMemory = runtime.totalMemory() - runtime.freeMemory();
		System.out.println("memory=" + (eUseMemory - sUseMemory));
	}
}
