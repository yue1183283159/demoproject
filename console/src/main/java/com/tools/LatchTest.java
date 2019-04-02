package com.tools;

import java.util.concurrent.CountDownLatch;

/**
 *模拟真正的并发请求：使用CountDownLatch闭锁。
 *方案：1. 开启n个线程，加一个闭锁，开启所有线程。2.等待所有线程都准备好之后，打开闭锁，让所有线程立刻执行，达到并发的效果 
 **/
public class LatchTest {
	public static void main(String[] args) {
		Runnable task = new Runnable() {
			private int counter;// 此处是非线程安全的，用来体现多线程并发时变量的值不正确
			@Override
			public void run() {
				for (int i = 0; i < 10; i++) {
					// 发起请求，省略
					counter++;
					System.out.println(Thread.currentThread().getName() + ":" + counter);
					try {
						Thread.sleep(100);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		};

		try {
			LatchTest latchTest = new LatchTest();
			latchTest.startAllTaskInOnce(5, task);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public long startAllTaskInOnce(int threadNums, final Runnable task) throws InterruptedException {
		final CountDownLatch startGate = new CountDownLatch(1);
		final CountDownLatch endGate = new CountDownLatch(threadNums);

		for (int i = 0; i < threadNums; i++) {
			Thread t = new Thread(() -> {
				try {
					startGate.await();// 使线程在此等待，当开始们打开时，等待的线程同时执行，进入门中
					try {
						task.run();
					} finally {
						endGate.countDown();// 将结束门减1，减到0时，结束开启结束门
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			});

			t.start();
		}

		System.out.println(Thread.currentThread().getName() + ": all thread is ready， waiting going... ");
		startGate.countDown();// 开始门主需要减少1就可以立即开启开始门
		endGate.await();// 等待结束门开启
		System.out.println(Thread.currentThread().getName() + ": all thread is over.");
		return 0;
	}
}
