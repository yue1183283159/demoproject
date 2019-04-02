package com.threadsafe;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 没有实现同步，两个线程同时操作银行账户，出现线程安全问题
 *
 **/
public class OperateMoney {
	public static void main(String[] args) {
		final Bank bank = new Bank();
		Thread tadd = new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(1000);
					} catch (Exception e) {
						e.printStackTrace();
					}

					bank.addMoney(100);
					bank.lookMoney();
					System.out.println("\n");
				}

			}
		});

		Thread tsub = new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					bank.subMoney(100);
					bank.lookMoney();
					System.out.println("\n");

					try {
						Thread.sleep(1000);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
		
		
		tsub.start();
		tadd.start();
	}
}

/**
 *如果没有实现同步，则会出现线程安全问题。因为两个线程会同时访问一个 没有同步的方法，如果两个线程
 *同时操作业务对象中的实例变量，就会出现线程安全。
 *1. 同步方法，使用synchronized关键字修饰方法。
 *synchronized关键字也可以修饰静态方法，此时如果调用该静态方法，将会锁住整个类。
 * 同步是一种高开销的操作，因此应该尽量减少同步的内容
 *2. 使用synchronized代码块同步关键的代码
 *3. 使用重入锁实现线程同步，ReentrantLock类是可以重入，互斥，实现Lock借口的锁。
 *ReentrantLock：ReentrantLock()创建一个ReentrantLock实例，lock()获得锁，unlock释放锁
 *
 *关于Lock对象和synchronized关键字的选择：
 *最后两个都不使用，如果synchronized关键字能满足就用synchronized，如果需要更高级的功能，就是用ReentrantLock类
 *使用ReentrantLock类要注意及时释放lock，否则会出现死锁，通常在finally代码块中释放锁
 *4. 使用局部变量来实现线程同步
 *使用ThreadLocal管理变量，则每一个使用该变量的线程都获得该变量的副本，副本之间相互独立，这样每一个线程都可以随意修改自己的变量副本，而不会对其他线程产生影响
 **/
class Bank {
	private int count = 0;// 账户余额

	private Lock lock=new ReentrantLock();
	
	private static ThreadLocal<Integer> countex=new ThreadLocal<>();
	
	protected Integer initialValue() {
		return 0;
	}
	
	/*public void addMoney(int money) {
		count += money;
		System.out.println(System.currentTimeMillis() + " add money: " + money);
	}*/

	/*public synchronized void addMoney(int money) {
		count += money;
		System.out.println(System.currentTimeMillis() + " add money: " + money);
	}*/
	
	/*public void addMoney(int money) {
		synchronized(this) {
			count += money;	
		}
		
		System.out.println(System.currentTimeMillis() + " add money: " + money);
	}*/
	
	public void addMoney(int money) {
		lock.lock();//上锁
		try {
			count += money;	
			System.out.println(System.currentTimeMillis() + " add money: " + money);
			
		} finally {
			lock.unlock();//解锁
		}		
		
	}
	
	
	/*public void subMoney(int money) {
		if (count - money < 0) {
			System.out.println("remain money is not enough.");
			return;
		}
		count -= money;
		System.out.println(System.currentTimeMillis() + " get money: " + money);
	}*/
	
	/*public synchronized void subMoney(int money) {
		if (count - money < 0) {
			System.out.println("remain money is not enough.");
			return;
		}
		count -= money;
		System.out.println(System.currentTimeMillis() + " get money: " + money);
	}*/

	/*public void subMoney(int money) {
		synchronized (this) {
			if (count - money < 0) {
				System.out.println("remain money is not enough.");
				return;
			}
			count -= money;
		}
		
		System.out.println(System.currentTimeMillis() + " get money: " + money);
	}*/
	
	public void subMoney(int money) {
		lock.lock();
		try {
			if (count - money < 0) {
				System.out.println("remain money is not enough.");
				return;
			}
			count -= money;	
		
			System.out.println(System.currentTimeMillis() + " get money: " + money);
		} finally {
			lock.unlock();
		}			
	}
	
	public void lookMoney() {
		System.out.println("account remain money:" + count);
	}

}