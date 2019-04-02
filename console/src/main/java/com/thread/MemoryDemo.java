package com.thread;

public class MemoryDemo
{
	public static void main(String[] args)
	{
		Runtime runtime = Runtime.getRuntime();
		// 从操作系统中申请的最大内存
		System.out.println(runtime.maxMemory() / 1024 / 1024 + "M");
		// 已经申请到的内存
		System.out.println(runtime.totalMemory() / 1024 / 1024 + "M");
		// 空闲内存
		System.out.println(runtime.freeMemory() / 1024 / 1024 + "M");
		System.out.println();
		for (int i = 0; i < 100; i++)
		{
			byte[] data = new byte[1024 * 1024 * 10];
			System.out.println(i + " --------");
			System.out.println(runtime.totalMemory() / 1024 / 1024 + "M");
			System.out.println(runtime.freeMemory() / 1024 / 1024 + "M");
			
			//当内存不够用的时候，系统进行垃圾回收，将没有引用的数组对象进行回收，释放内存。
			//如果对象有引用（不应该把对象放入静态对象中），就不会被回收，会造成内存泄漏
			System.out.println("已经用了" + (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024 + "M");
		}
	}

}
