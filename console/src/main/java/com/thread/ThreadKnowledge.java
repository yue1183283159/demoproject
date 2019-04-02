package com.thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ThreadKnowledge {
	public static void main(String[] args) throws Exception {
		// 多次调用一个线程的start方法，会出现什么情况. 会抛出java.lang.IllegalThreadStateException异常
		// TestThread testThread = new TestThread();
		// testThread.start();
		// testThread.start();
		// testThread.start();
		//
		// TestThread1 testThread1=new TestThread1();
		// Thread thread=new Thread(testThread1);
		// thread.start();
		// thread.start();
		// thread.start();

		// 假如有Thread1、Thread2、Thread3、Thread4四条线程分别统计C、D、E、F四个盘的大小，所有线程都统计完毕交给Thread5线程去做汇总，应当如何实现？
		// 利用java.util.concurrent包下的CountDownLatch(减数器)或者CyclicBarrier(循环栅栏)可以解决此类问题
		// 利用CountDownLatch实现：
		System.out.println("利用CountDownLatch解决");
		countDownLatch();
		// 利用CyclicBarrier解决
		System.out.println("利用CyclicBarrier解决");
		cyclicBarrier();

		// newSingleThreadExecutor
		// 创建一个单线程化的Executor，即只创建唯一的工作者线程来执行任务，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO,
		// 优先级)执行。如果这个线程异常结束，会有另一个取代它，保证顺序执行。单工作线程最大的特点是可保证顺序地执行各个任务，并且在任意给定的时间不会有多个线程是活动的
		ExecutorService singleThreadService = Executors.newSingleThreadExecutor();
		for (int i = 0; i < 10; i++) {
			final int index = i;
			singleThreadService.execute(new Runnable() {

				@Override
				public void run() {
					try {
						System.out.println("Thread " + Thread.currentThread().getId() + ": " + index);
						Thread.sleep(2000);
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			});
		}

		// newScheduleThreadPool 创建一个定长的线程池，而且支持定时的以及周期性的任务执行，支持定时及周期性任务执行
		ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);
		// 延迟3秒执行任务
		scheduledThreadPool.schedule(new Runnable() {
			@Override
			public void run() {
				System.out.println("delay 3 seconds executed.");
			}
		}, 3, TimeUnit.SECONDS);

		// 延迟1秒之后每隔3秒执行一次，定期执行任务
		scheduledThreadPool.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				System.out.println("delay 1 seconds, and excute every 2 seconds");
			}
		}, 0, 2, TimeUnit.SECONDS);
	}

	static void cyclicBarrier() throws Exception {
		Runnable barrierAction = new Runnable() {
			@Override
			public void run() {
				System.out.println("统计C,D,E,F");

			}
		};

		final CyclicBarrier cyclicBarrier = new CyclicBarrier(4, barrierAction);

		Runnable runC = new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(3000);
					System.out.println("C盘");
					cyclicBarrier.await();
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		};

		Runnable runD = new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(3000);
					System.out.println("D盘");
					cyclicBarrier.await();
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		};

		Runnable runE = new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(3000);
					System.out.println("E盘");
					cyclicBarrier.await();
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		};

		Runnable runF = new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(3000);
					System.out.println("F盘");
					cyclicBarrier.await();
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		};

		// 创建线程池，使用线程池执行线程
		ExecutorService service = Executors.newFixedThreadPool(4);
		service.submit(runC);
		service.submit(runD);
		service.submit(runE);
		service.submit(runF);
		service.shutdown();
	}

	static void countDownLatch() throws Exception {
		// 创建一个能容纳4个线程的减数器
		final CountDownLatch countDownLatch = new CountDownLatch(4);

		Runnable runC = new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(3000);
					System.out.println("统计C盘");
					countDownLatch.countDown();// 单任务，计数器减1
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		};

		Runnable runD = new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(3000);
					System.out.println("统计D盘");
					countDownLatch.countDown();// 单任务，计数器减1
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		};

		Runnable runE = new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(3000);
					System.out.println("统计E盘");
					countDownLatch.countDown();// 单任务，计数器减1
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		};

		Runnable runF = new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(3000);
					System.out.println("统计F盘");
					countDownLatch.countDown();// 单任务，计数器减1
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		};

		// 创建线程池
		ExecutorService service = Executors.newFixedThreadPool(4);
		service.submit(runC);
		service.submit(runD);
		service.submit(runE);
		service.submit(runF);

		countDownLatch.await();// 第五个线程主线程等待
		System.out.println("合计C,D,E,F");
		service.shutdown();

	}
}

class TestThread1 implements Runnable {

	@Override
	public void run() {
		try {
			for (int i = 0; i < 100000; i++) {
				System.out.println("Thread " + Thread.currentThread().getId() + ": " + i);
				Thread.sleep(100);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}

class TestThread extends Thread {

	@Override
	public void run() {
		try {
			for (int i = 0; i < 100000; i++) {
				System.out.println("Thread " + Thread.currentThread().getId() + ": " + i);
				Thread.sleep(100);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
