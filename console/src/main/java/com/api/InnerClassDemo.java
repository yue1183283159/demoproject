package com.api;

public class InnerClassDemo
{
	public static void main(String[] args)
	{
		WeaponEx w = f1();
		w.kill();

		WeaponEx w2 = f2("Yi Tian Jian");
		w2.kill();
	}

	private static WeaponEx f2(String name)
	{
		WeaponEx wx = new WeaponEx()
		{
			@Override
			public void kill()
			{
				System.out.println(name+" killed.");
			}

		};
		return wx;
	}

	// 局部内部类，一般是在方法中返回这个内部类的实例对象，内部类一般继承一个定义的父类或是接口，
	// 便于在外边接受这个返回的对象，然后执行内部类的定义的方法
	static WeaponEx f1()
	{
		class AK47 implements WeaponEx
		{
			@Override
			public void kill()
			{
				System.out.println("Execute AK47 kill method.");
			}
		}

		AK47 ak47 = new AK47();
		return ak47;
	}
}

interface WeaponEx
{
	void kill();
}
