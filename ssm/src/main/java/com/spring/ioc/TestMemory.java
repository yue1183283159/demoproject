package com.spring.ioc;

public class TestMemory
{
	public static void main(String[] args)
	{
		Outer outer = new Outer();
		// 会造成内存泄漏，因为内部类会引用这个外部实例对象
		// outer.new WorkThread().start();//实例化外部类里的实例内部类

		new Outer.WorkThread().start();//实例化外部类的静态内部类
		
		outer = null;// 将引用置空，被垃圾回收机制回收
		System.gc();// 通知JVM,进行内存回收
	}
}

class Outer
{
	/*
	 * 实例内部类，此类对象依赖于外部类对象，默认会保存外部类对象的一个引用
	 */
	// class WorkThread extends Thread
	// {
	// @Override
	// public void run()
	// {
	// while (true)
	// {
	// }
	// }
	// }

	/*
	 * 静态内部类：不依赖与外部类而存在，不会保存外部类的引用
	 */
	static class WorkThread extends Thread
	{
		@Override
		public void run()
		{
			while (true)
			{
			}
		}
	}

	@Override
	protected void finalize() throws Throwable
	{
		super.finalize();
		System.out.println("finalize");
	}

}
