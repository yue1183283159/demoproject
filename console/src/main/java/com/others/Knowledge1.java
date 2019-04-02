package com.others;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class Knowledge1 {
	public static void main(String[] args) throws Exception {
		// 1. 值传递与引用传递
		Integer a1 = 0;// 基本类型，都是值传递
		paramCheck(a1);
		System.out.println(a1);// a1的值没有改变

		Integer b1 = 100;
		Integer b2 = 100;
		System.out.println(b1 == b2);// true

		Integer b3 = 128;
		Integer b4 = 128;
		System.out.println(b3 == b4);// false
		
		System.out.println(3/2);
		System.out.println(9/10);
		System.out.println(3d/2);

		Callable<String> oneCallable = new SomeCallable();
		FutureTask<String> oneTask = new FutureTask<>(oneCallable);
		Thread oneThread = new Thread(oneTask);
		oneThread.start();

		int taskSize = 5;
		ExecutorService pool = Executors.newFixedThreadPool(taskSize);
		List<Future> taskList = new ArrayList<>();
		for (int i = 0; i < taskSize; i++) {
			Callable c = new MyCallable(i + "");
			Future f = pool.submit(c);// 执行任务并获取Future对象
			taskList.add(f);
		}
		pool.shutdown();// 关闭线程池
		// 获取并发任务的运行结果
		for (Future f : taskList) {
			System.out.println(">>>" + f.get().toString());
		}

		Timer timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				System.out.println("test timer...");

			}
		}, new Date(), 1000);
		
		timer.cancel();

	}

	private static void paramCheck(Integer a1) {
		if (a1 <= 1) {
			a1 += 1;
		}
		// System.out.println(a1);
	}
}

class SomeCallable<v> implements Callable<v> {

	@Override
	public v call() throws Exception {
		System.out.println("call...");
		return null;
	}

}

class MyCallable implements Callable<Object> {
	private String taskNum;

	public MyCallable(String taskNum) {
		this.taskNum = taskNum;
	}

	@Override
	public Object call() throws Exception {
		System.out.println(">>>" + taskNum + " task started.");

		Thread.sleep(1000);

		return taskNum + ", " + System.currentTimeMillis();
	}

}
