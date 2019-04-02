package com.designpattern;

/***
 * 设计模式:一种套路,一种经验总结.
 * 单例模式:
 * 1)属于一种创建型模式(与对象创建相关)
 * 2)是为了保证类的对象在内存中的全局唯一性,给定
 * 的一种设计规则.
 * 单例模式应用场景:
 * 1)池对象的应用(例如字符串池)
 * 2)资源访问对象
 * 单例模式应用的目的:(减少对象对资源占用)
 * 单例模式的具体实现思路?
 * 规则:
 * 1)类的外部不允许直接构建此类对象
 * 2)类的外部只能通过静态方法访问此对象
 * 实现:
 * 1)构造方法私有化
 * 2)在类的内部构建对象
 * 3)定义一个静态方法,通过这个方法直接返回此对象
 */
// 单例设计方案1
class Singleton01
{
	private Singleton01()
	{
	}

	private static Singleton01 instance;

	public static Singleton01 getInstance()
	{
		if (instance == null)
		{
			instance = new Singleton01();
		}
		return instance;
	}
}

// 以上单例设计可能存在什么问题?(线程安全问题)
// 单例设计方案2(线程安全单例)
class Singleton02
{
	private Singleton02()
	{
	}

	private static Singleton02 instance;

	public static synchronized Singleton02 getInstance()
	{
		if (instance == null)
		{
			instance = new Singleton02();
		}
		return instance;
	}
}

// 以上单例设计存在什么问题吗?(性能问题)
// 单例设计方案3(线程安全+效率)
class Singleton03
{// 大对象,稀少用
	private Singleton03()
	{
	}

	private static Singleton03 instance;

	public static Singleton03 getInstance()
	{
		if (instance == null)
		{
			synchronized (Singleton03.class)
			{
				if (instance == null)
				{
					instance = new Singleton03();
				}
			}
		}
		return instance;
	}
}
// 以上设计适合对instance对象的频繁访问吗?不适合

// 单例设计方案四(小对象频繁用)
class Singleton04
{
	// private int[] array=new int[1024];
	private Singleton04()
	{
	}

	/** 类加载时创建 */
	private static final Singleton04 instance = new Singleton04();

	/** 此方法中因为没有阻塞问题所以适合频繁访问 */
	public static Singleton04 getInstance()
	{
		return instance;
	}
	// public static void display(){}
	// public void show(){}
}

// 请问以上设计存在什么问题吗?(假如对象比较大,
// 类加载时就创建了此对象,假如不使用,就可能长时间占用资源)
// 单例设计方案五(采用延迟加载策略优化设计方案四)
class Singleton05
{
	private Singleton05()
	{
	}

	static class Lazy
	{// 类何时被加载?
		public static final Singleton05 instance = new Singleton05();
	}

	public static Singleton05 getInstance()
	{
		// 何时需要何时加载
		System.out.println("init instance.");
		return Lazy.instance;
	}
	
	public static void display() {
		System.out.println("display method.");
	}
	
	public void show() {
		System.out.println("show method.");
	}
	// 请问访问Singleton05类的这个方法时候会加载Lazy吗?  
	// public static void display(){}  //不会，静态方法不需要通过类的实例调用，它有类对象调用。
	// 请问访问Singleton05类的show方法时是否需要加载Lazy?
	// public void show(){} //会，因为非静态成员方法，需要类的实例对象来调用
}
// ---------------------------------------------

public class SingletonPattern
{
	public static void main(String[] args)
	{
		
		Singleton05.display();
		Singleton05.getInstance().show();
		
		
		for (int i = 0; i < 5; i++)
		{
			new Thread()
			{
				public void run()
				{
					System.out.println(Singleton01.getInstance());
				};
			}.start();
		}
	}
}
