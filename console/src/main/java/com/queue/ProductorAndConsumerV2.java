package com.queue;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ProductorAndConsumerV2 {
	public static ReentrantLock lock = new ReentrantLock();
	public static Condition empty = lock.newCondition();
	public static Condition full = lock.newCondition();

	public static void main(String[] args) {
		List<PCData> queue = new ArrayList<>();
		int length = 10;
		ProducterV2 p1 = new ProducterV2(queue, length);
		ProducterV2 p2 = new ProducterV2(queue, length);
		ProducterV2 p3 = new ProducterV2(queue, length);
		ConsumerV2 c1 = new ConsumerV2(queue);
		ConsumerV2 c2 = new ConsumerV2(queue);
		ConsumerV2 c3 = new ConsumerV2(queue);

		ExecutorService service = Executors.newCachedThreadPool();
		service.execute(p1);
		service.execute(p2);
		service.execute(p3);
		service.execute(c1);
		service.execute(c2);
		service.execute(c3);
	}
}

class ConsumerV2 implements Runnable {

	private List<PCData> queue;

	public ConsumerV2(List<PCData> queue) {
		this.queue = queue;
	}

	@Override
	public void run() {
		try {
			while (true) {
				if (Thread.currentThread().isInterrupted()) {
					break;
				}

				PCData data = null;
				ProductorAndConsumerV2.lock.lock();
				if (queue.size() == 0) {
					ProductorAndConsumerV2.full.signalAll();
					ProductorAndConsumerV2.empty.await();
				}

				Thread.sleep(1000);
				data = queue.remove(0);
				ProductorAndConsumerV2.lock.unlock();
				int re = data.getData() * 2;
				System.out.println(MessageFormat.format("Consumer Id {0}: used {1}*2={2}",
						Thread.currentThread().getId(), data.getData(), re));

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

class ProducterV2 implements Runnable {

	private List<PCData> queue;
	private int len;

	public ProducterV2(List<PCData> queue, int len) {
		this.queue = queue;
		this.len = len;
	}

	@Override
	public void run() {
		try {
			while (true) {
				if (Thread.currentThread().isInterrupted()) {
					break;
				}

				Random random = new Random();
				PCData data = new PCData(random.nextInt(100));
				ProductorAndConsumerV2.lock.lock();
				if (queue.size() >= len) {
					ProductorAndConsumerV2.empty.signalAll();
					ProductorAndConsumerV2.full.await();
				}

				Thread.sleep(1000);
				queue.add(data);
				ProductorAndConsumerV2.lock.unlock();

				System.out.println("producer ID:" + Thread.currentThread().getId() + " product:" + data.getData());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
