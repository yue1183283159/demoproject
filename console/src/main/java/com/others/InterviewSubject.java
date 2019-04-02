package com.others;

import java.util.HashSet;
import java.util.Set;

import com.sun.org.apache.bcel.internal.generic.NEW;

public class InterviewSubject {
	public static void main(String[] args) {
		// 1. 主流的关系型数据库有Oracle、DB2、Microsoft SQL Server、Microsoft Access、MySQL
		// 非关系型数据库有NoSql，Cloudant。
		// 临时性键值存储（memcached、Redis）、永久性键值存储（ROMA、Redis）、面向文档的数据库（MongoDB、CouchDB）、面向列的数据库（Cassandra、HBase）

		// 2. volatile关键字能保证线程安全吗？不能
		/*
		 * volatile是不能保证线程安全的，它只是保证了数据的可见性，不会再缓存，每个线程都是从主存中读到的数据，而不是从缓存中读取的数据.
		 * volatile是一种轻量级的同步机制，它主要有两个特性：一是保证共享变量对所有线程的可见性；二是禁止指令重排序优化。
		 * 同时需要注意的是，volatile对于单个的共享变量的读/写具有原子性，但是像num++这种复合操作，volatile无法保证其原子性
		 */

		// 3.不通过构造函数也能创建对象吗？能
		// Java创建对象的几种方式：
		// (1) 用new语句创建对象，这是最常见的创建对象的方法。
		// (2)
		// 运用反射手段,调用java.lang.Class或者java.lang.reflect.Constructor类的newInstance()实例方法。
		// (3) 调用对象的clone()方法。
		// (4) 运用反序列化手段，调用java.io.ObjectInputStream对象的 readObject()方法。

		// 4.考察对null的理解。null可以被强转成任意类型。
		((NULL) null).haha();
		System.out.println(((Integer) null).MAX_VALUE);

		System.out.println();
		System.out.println();
		// 5.考察try{}finally{}中return的顺序，以及i++的用法
		System.out.println(Test.getResult());// 1
		// ++，--是先用值后执行加减然后赋值。所以return的value++是++之前的值。
		// inc()中是++，return的值是0，但是value值为1；dec()方法中是--，return的是value的值1，随后被--为0；
		System.out.println(Test.value);// ++之后--，value值还是0 //0

		System.out.println();
		System.out.println();
		// 6.重写类中equals方法之后的对象比较，以及set集合中的hash值比较
		Set<Test1> tSet = new HashSet<>();
		tSet.add(new Test1("m", "n"));
		// set集合中的对象比较是比较对象的hashcode，如果没有重写类的hashcode方法，使用它们返回相同的hash值，在set中就是两个不同的对象
		System.out.println(tSet.contains(new Test1("m", "n")));// false
		// 重写了类的equals方法，比较属性值相等，则两个对象相等
		System.out.println(new Test1("m", "n").equals(new Test1("m", "n")));// true

		System.out.println();
		System.out.println();
		// 7. 线程,thread.start()是开始执行线程的方法，如果thread.run()则是调用run方法，不是线程执行。
		// 线程实现，定义一个子类继承Thread类，重写run方法，实例化子类，调用start()方法。
		Thread thread = new Thread() {
			public void run() {
				pong();
			}
		};
		thread.run();// 同步调用run方法，此时输出pongping
		// thread.start();//开启线程，执行run方法，此时线程进入cpu中等待执行，所以会输出pingpong
		System.out.print("ping");

		System.out.println();
		System.out.println();
		// 8. 类加载,类继承，类中成员的初始化顺序
		// 父类静态变量 > 父类静态初始块 > 子类静态变量 > 子类静态初始块 > 父类成员变量 > 父类非静态初始块 >
		// 父类构造器 > 子类成员变量 > 子类非静态初始块 > 子类构造器
		new HelloB();

		System.out.println();
		System.out.println();
		// 9. 继承，成员属性的范围.父类中的私有属性不能被子类继承，并且私有属性只能在当前类中访问，出了当前类不能访问。
		Person person = new Child();
		// System.out.println(person.name);//编译不能通过

		// 10. 反转字符串
		StringBuilder sBuilder = new StringBuilder("abc");
		sBuilder = sBuilder.reverse();
		System.out.println(sBuilder.toString());

		System.out.println();
		String str1 = "abc";
		char[] cArr = str1.toCharArray();
		str1 = "";
		for (int i = cArr.length - 1; i >= 0; i--) {
			str1 += cArr[i];
		}
		System.out.println(str1);

		// 跳出多重循环，让外层的循环条件表达式的结果可以收到内层循环体的控制
		// 在二维数组中查找到某个数字。
		int arr[][] = { { 1, 2, 3 }, { 4, 5, 6 }, { 9 } };
		boolean isfound = false;
		for (int i = 0; i < arr.length && !isfound; i++) {
			for (int j = 0; j < arr[i].length; j++) {
				System.out.println("i=" + i + ",j=" + j);
				if (arr[i][j] == 5) {
					isfound = true;
					break;
				}
			}
		}
	}

	static void pong() {
		System.out.println("pong");
	}
}

class NULL {
	public static void haha() {
		System.out.println("haha");
	}
}

class Test {
	static int value = 0;

	static int inc() {
		return value++;
	}

	static int dec() {
		return value--;
	}

	static int getResult() {
		try {
			// int val=inc(); //此时是val=0,value=1;
			inc();
		} finally {
			// int val=dec(); //此时是val=1,value=0,return 的是1.
			// return val;
			return dec();
		}
	}
}

class Test1 {
	private String first, last;

	public Test1(String first, String last) {
		this.first = first;
		this.last = last;
	}

	// 判断对象相等，重写equals方法
	public boolean equals(Object o) {
		Test1 test1 = (Test1) o;
		return test1.first.equals(first) && test1.last.equals(last);
	}

	// 对于需要放入set，map中需要使用hashcode的地方，重写hashcode方法，用于判断对象是否存在
	@Override
	public int hashCode() {
		System.out.println(super.hashCode());
		return super.hashCode();
	}
}

class HelloA {
	public HelloA() {
		System.out.println("HelloA");
	}

	{
		System.out.println("I'm A Class.");
	}

	static {
		System.out.println("static A");
	}
}

class HelloB extends HelloA {
	public HelloB() {
		System.out.println("HelloB");
	}

	{
		System.out.println("I'm B Class.");
	}

	static {
		System.out.println("static B.");
	}
}

class Person {
	private String name = "Person";
	int age = 0;
}

class Child extends Person {
	public String grade;
}
