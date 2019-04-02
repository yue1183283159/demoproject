package com.thread;

//ThreadLocal并不是一个Thread，而是Thread的局部变量
//ThreadLocal是如何做到为每一个线程维护变量的副本的呢？
//其实实现的思路很简单：在ThreadLocal类中定义了一个ThreadLocalMap，每一个Thread中都有一个该类型的变量——threadLocals——用于存储每一个线程的变量副本，Map中元素的键为线程对象，而值对应线程的变量副本。
//在Java的多线程编程中，为保证多个线程对共享变量的安全访问，通常会使用synchronized来保证同一时刻只有一个线程对共享变量进行操作。
//这种情况下可以将类变量放到ThreadLocal类型的对象中，使变量在每个线程中都有独立拷贝，不会出现一个线程读取变量时而被另一个线程修改的现象
public class ThreadLocalMain
{
	public static void main(String[] args)
	{
		SequenceNumber sn = new SequenceNumber();
		// ③ 3个线程共享sn，各自产生序列号
		TestClient t1 = new TestClient(sn);
		TestClient t2 = new TestClient(sn);
		TestClient t3 = new TestClient(sn);
		new Thread(t1).start();
		new Thread(t2).start();
		new Thread(t3).start();
	}
}

class TestClient implements Runnable
{

	private SequenceNumber sn;

	public TestClient(SequenceNumber sn)
	{
		super();
		this.sn = sn;
	}

	@Override
	public void run()
	{
		for (int i = 0; i < 3; i++)
		{
			System.out.println("thread[" + Thread.currentThread().getName() + "] sn[" + sn.getNextNum() + "]");
		}
	}

}

class SequenceNumber
{
	// 通过匿名内部类覆盖ThreadLocal的initialValue()方法，指定初始值
	private static ThreadLocal<Integer> seqNum = new ThreadLocal<Integer>()
	{
		public Integer initialValue()
		{
			return 0;
		}
	};

	// 获取下一个序列值
	public Integer getNextNum()
	{
		seqNum.set(seqNum.get() + 1);
		return seqNum.get();
	}
}
