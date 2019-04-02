package com.api;

import java.util.ArrayList;
import java.util.Random;

public class OOPDemo
{

	public static void main(String[] args)
	{

		// SoldierEx[] soldierExs = new SoldierEx[5];
		ArrayList<SoldierEx> soldierExs2 = new ArrayList();
		for (int i = 0; i < 5; i++)
		{
			// soldierExs[i] = new SoldierEx(i + 1);
			soldierExs2.add(new SoldierEx(i + 1));
		}

		// System.out.println("Current Soldier Count: " + SoldierEx.count);

		while (SoldierEx.count > 0)		
		{
			// for (int i = 0; i < soldierExs.length; i++)
			// {
			// soldierExs[i].attack();
			// }
			
			for (int i = 0; i < soldierExs2.size(); i++)
			{
				SoldierEx soldierEx = soldierExs2.get(i);
				if (soldierEx.isDead)
				{
					soldierExs2.remove(soldierEx);
					continue;
				}
				soldierEx.attack();
			}

			System.out.println("Current Soldier Count: " + SoldierEx.count);
			System.out.println("list size：" + soldierExs2.size());
		}
		
		B b = new B();
		System.out.println("-------------------------");
		B b2 = new B();

	}

}

// static 属于类，不属于实例
// 加载之后，存放在内存的方法区。实例在堆区。
// 一般用类名调用静态成员
// 什么时候使用静态：能不用就不用，静态是“非面向对象”的语法
// 使用场景：共享的数据，工具方法，
// 静态初始化块
/*
 * class A{ static { 在类被加载时，只执行一次 } }
 */

// final 常量，值不可变
// final Soldier s=new Soldier();
// s.id=6; correct.
// s=new Soldier(); incorrect.
// 在方法上加final,在子类中不能被重写
// 在类上加final，不能被继承。System,String都是final，不能被继承

// 访问控制符
// 控制类，类中成员访问范围
// public 无限制访问
// protected 类，包，子类中访问
// [default] 类，包中访问
// private 类中访问
// 选择：应该尽量小范围的访问；public与其他开发者的一个契约，约定公开的东西会尽量保持稳定不变。

// 对象的创建过程
class A
{
	static int v1 = 1;
	int v2 = 2;
	static
	{
		System.out.println("A static block.");
	}

	public A()
	{
		System.out.println("A constructor");
	}
}

class B extends A
{
	static int v3 = 3;
	int v4 = 4;
	static
	{
		System.out.println("B static block.");
	}

	public B()
	{
		System.out.println("B constructor.");
	}
}

class SoldierEx
{
	int id;
	int blood = 100;
	boolean isDead = false;
	// 士兵数量
	static int count;

	public SoldierEx(int id)
	{
		this.id = id;
		count++;
	}

	public void attack()
	{
		if (blood == 0)
		{
			System.out.println(id + "号士兵已经死了。");
			return;
		}

		System.out.println(id + "号士兵进攻，");
		blood -= new Random().nextInt(20);
		if (blood <= 0)
		{
			blood = 0;
			count--;
			System.out.println(id + "号士兵已经死了。");
			isDead = true;
		} else
		{
			System.out.println(id + "好士兵血量剩余：" + blood);
		}
	}
}

/*
 * 抽象类：半成品类，没有完成的类。不能创造对象。 抽象类中没有实现的方法，必须在子类中实现。 子类不实现抽象方法，就必须也声明为抽象类
 */
abstract class TestAbstractClass
{
	private int name;

	public void say()
	{
		System.out.println("say method");
	}

	public abstract void cry();

}