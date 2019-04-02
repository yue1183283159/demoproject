package com.thread;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class ThreadSafety
{
	static String objLock="objLock";
	static ArrayList<String> arrayList = new ArrayList<>();
	static CopyOnWriteArrayList<String> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
	static int size = 100000;

	public static void main(String[] args) throws Exception
	{
		// test1();
		test2();
	}

	private static void test1() throws Exception
	{
		Task1 task1 = new Task1();
		for (int i = 0; i < size; i++)
		{
			Thread thread = new Thread(task1);
			thread.start();
		}
		// 因为ArrayList是线程不安全的，所以每次结果不一样，长度不是100000。因为会有线程没有将值插入集合中。
		System.out.println(arrayList.size());
		
		//即使等待所有线程都执行完，也不能保证插入值正确
		Thread.currentThread().sleep(10000);
		System.out.println(copyOnWriteArrayList.size());
	}

	private static void test2() throws Exception
	{
		Task2 task2 = new Task2();
		for (int i = 0; i < size; i++)
		{
			Thread thread = new Thread(task2);
			thread.start();
		}
		// CopyOnWriteArrayList 是线程安全的ArrayList。但是现在长度也不是100000.
		// 因为for循环完了，主线程停止了，但是还有好多thread没有执行，所以值没有插入到集合中。
		System.out.println(copyOnWriteArrayList.size());

		// 保证所有线程都执行完,CopyOnWriteArrayList中才能被插入100000个数据
		//Thread.currentThread().sleep(10000);
		//
		synchronized (objLock)
		{
			objLock.wait();
			System.out.println(copyOnWriteArrayList.size());
		}
		
	}
}

class Task1 implements Runnable
{

	@Override
	public void run()
	{
		ThreadSafety.arrayList.add("");

	}
}

class Task2 implements Runnable
{
	@Override
	public void run()
	{
		synchronized (ThreadSafety.objLock)
		{
			ThreadSafety.copyOnWriteArrayList.add("");
			//最后一个线程执行完再发通知。需要一个计数判断哪个线程是最后一个
			ThreadSafety.objLock.notify();	
		}
		
	}
}
