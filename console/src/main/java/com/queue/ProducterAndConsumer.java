package com.queue;

import java.text.MessageFormat;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

//BlockingQueue是一个阻塞队列，它的存取可以保证只有一个线程在进行
public class ProducterAndConsumer {
	public static void main(String[] args) throws Exception {
		// 初始化一个公共队列，用来存放数据
		BlockingQueue<PCData> queue = new LinkedBlockingQueue<>(10);
		Producer p1 = new Producer(queue);
		Producer p2 = new Producer(queue);
		Producer p3 = new Producer(queue);

		Consumer c1 = new Consumer(queue);
		Consumer c2 = new Consumer(queue);
		Consumer c3 = new Consumer(queue);

		// 使用线程池来执行多线程任务
		ExecutorService service = Executors.newCachedThreadPool();
		service.execute(p1);
		service.execute(p2);
		service.execute(p3);

		service.execute(c1);
		service.execute(c2);
		service.execute(c3);

		Thread.sleep(1000 * 10);
		p1.stop();
		p2.stop();
		p3.stop();
		Thread.sleep(3000);
		service.shutdown();
	}
}

//生产者负责生产一个数字并存入缓冲区，消费者从缓冲区中取出数据
class PCData {
	private final int intData;

	public PCData(int d) {
		intData = d;
	}

	public int getData() {
		return intData;
	}

	@Override
	public String toString() {
		return "data:" + intData;
	}
}

class Producer implements Runnable {

	private volatile boolean isRunning = true;
	private BlockingQueue<PCData> queue;// 内存缓冲区
	private static AtomicInteger count = new AtomicInteger();// 总数，原子操作
	private static final int SLEEPTIME = 1000;

	public Producer(BlockingQueue<PCData> queue) {
		this.queue = queue;
	}

	@Override
	public void run() {
		PCData data = null;
		Random random = new Random();
		System.out.println("start producting id:" + Thread.currentThread().getId());
		try {
			while (isRunning) {
				Thread.sleep(random.nextInt(SLEEPTIME));
				data = new PCData(count.incrementAndGet());
				System.out.println(data + " push to queue.");
				if (!queue.offer(data, 2, TimeUnit.SECONDS)) {
					System.err.println("failed push to queue.");
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			Thread.currentThread().interrupt();
		}

	}

	public void stop() {
		isRunning = false;
	}

}

class Consumer implements Runnable {

	private BlockingQueue<PCData> queue;
	private static final int SLEEPTIME = 1000;

	public Consumer(BlockingQueue<PCData> queue) {
		this.queue = queue;
	}

	@Override
	public void run() {
		System.out.println("start consumer id:" + Thread.currentThread().getId());
		Random random = new Random();
		try {
			while (true) {
				PCData data = queue.take();
				if (data != null) {
					int re = data.getData() * 2;
					System.out.println(MessageFormat.format("{0}*2={1}", data, re));
					Thread.sleep(random.nextInt(SLEEPTIME));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Thread.currentThread().interrupt();
		}
	}

}
