package com.api;

import java.util.Scanner;

/*
 * 方法,面向对象
 */
public class ExtendsDemo
{

	public static void main(String[] args) throws InterruptedException
	{
		/*
		 * Soldier s1 = new Soldier(1, "张三"); Soldier s2 = new Soldier(2, "李四");
		 * s1.go(); s1.attack(); s2.go(); s2.attack(); s2.blood = 200; s2.attack();
		 */

		// Student stu1 = new Student(12, "王五", 'F', 23);
		// System.out.println(stu1.toString());

		// Dog dog = new Dog("Lily", 0, 0); dog.feed(); dog.feed(); dog.feed();
		// dog.play(); dog.play(); dog.play(); dog.punish();
		//

		// System.out.println("1-Dog,2-Cat:");
		// int n = new Scanner(System.in).nextInt();
		// System.out.println("Please input the pet name:");
		// String name = new Scanner(System.in).nextLine();
		//
		// Dog dog2 = null;
		// Cat cat = null;
		// if (n == 1)
		// {
		// dog2 = new Dog(name, 50, 50);
		// play(dog2);
		// } else if (n == 2)
		// {
		// cat = new Cat(name, 50, 50);
		// play(cat);
		// }

		Shape line = new Line();
		line.draw();
		line.clear();

		f(new Shape());
		f(new Line());
		f(new Square());
	}

	static void f(Shape shape)
	{
		shape.draw();
		if(shape instanceof Line) {
			Line line=(Line)shape;
			line.length();
		}
		shape.clear();
	}

	static void play(Pet pet) throws InterruptedException
	{
		// 三个方法机会均等的调用
		while (true)
		{
			double d = Math.random();
			if (d < 0.33333)
			{
				pet.play();

			} else if (d < 0.66666)
			{
				pet.feed();
				// 运行期类型判断，instanceof对其真实类型或父类型都返回true。
				if (pet instanceof Dog)
				{
					Dog dog = (Dog) pet;
					dog.eatBone();
				}

			} else
			{
				pet.punish();
			}

			Thread.sleep(1000 * 2);
		}
	}
}

class Shape
{
	String color;
	double size;

	// 在子类中重写这个方法
	public void draw()
	{
		System.out.println("draw shape.");
	}

	public void clear()
	{
		System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");

		System.out.println("clear the drawed shape.");
	}

}

class Line extends Shape
{
	@Override
	public void draw()
	{
		System.out.println("Draw Line ——————————————————————————————");
	}
	
	public void length() {
		System.out.println("one miter line.");
	}
}

class Square extends Shape
{
	@Override
	public void draw()
	{
		System.out.println("Draw Square.口口口口口口口口口口口口口口口口口口口口");
	}
}

class Circle extends Shape
{
	@Override
	public void draw()
	{
		System.out.println("Draw Circle.口口口口口口口口口口口口口口口口口口");
	}

}

abstract class Pet
{
	protected String name;
	protected int full;
	protected int happy;

	public Pet(String name, int full, int happy)
	{
		this.name = name;
		this.full = full;
		this.happy = happy;
	}

	public void feed()
	{
		if (full == 100)
		{
			System.out.println(name + "饱了");
			return;
		}

		System.out.println(name + " 被喂食了。");
		full += 10;
		System.out.println(name + " 饱食度：" + full);
	}

	public void play()
	{
		if (full == 0)
		{
			System.out.println(name + "已经饿的玩不动了。");
			return;
		}
		System.out.println("陪" + name + "玩耍。");
		full -= 10;
		happy += 10;
		System.out.println(name + " 饱食度：" + full);
		System.out.println(name + " 快乐度：" + happy);
	}

	public void punish()
	{
		if (happy == 0)
		{
			System.out.println(name + "已经超级不快乐了。");
			return;
		}
		// cry在子类中重写，使用子类对象调用该方法时，先去子类中查找cry方法
		System.out.println("打" + name + "的PP。" + cry());
		happy -= 10;
		System.out.println(name + " 快乐度：" + happy);
	}

	public abstract String cry();

}

class Dog extends Pet
{
	public Dog(String name, int full, int happy)
	{
		super(name, full, happy);
	}

	public String cry()
	{
		return "汪汪，汪汪。。。";
	}

	public void eatBone()
	{
		System.out.println(name + "啃骨头。");
	}

}

class Cat extends Pet
{

	public Cat(String name, int full, int happy)
	{
		super(name, full, happy);
	}

	public String cry()
	{
		return "喵喵，喵喵。。。";

	}
}

class Student
{
	private int id;
	private String name;
	private char gender;
	private int age;

	public Student()
	{
	}

	public Student(int id, String name)
	{

		// this.id = id;
		// this.name = name;

		this(id, name, 'F');// 调用下面的三个参数的构造函数 。减少重复代码
	}

	public Student(int id, String name, char gender)
	{

		// this.id = id;
		// this.name = name;
		// this.gender = gender;

		this(id, name, gender, 0);// 调用下面的四个参数的构造函数
	}

	public Student(int id, String name, char gender, int age)
	{
		this.id = id;
		this.name = name;
		this.gender = gender;
		this.age = age;
	}

	@Override
	public String toString()
	{
		return this.id + "," + this.name + "," + this.gender + "," + this.age;
	}

}

class Soldier
{
	private int id;
	private String name;
	int blood = 100;

	// 无参构造
	public Soldier()
	{
		System.out.println("执行默认无惨构造");
	}

	public Soldier(int id, String name)
	{
		this.id = id;
		this.name = name;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void go()
	{
		System.out.println(id + "号士兵前进");
	}

	public void attack()
	{
		if (blood == 0)
		{
			System.out.println(id + "号士兵，血量不足了，已经阵亡。");
			return;
		}

		System.out.println(id + "号士兵进攻");
		blood -= 10;
		System.out.println(id + "号士兵血量剩余：" + blood);
	}

}
