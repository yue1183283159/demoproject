package com.queue;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//notify/wait() 来加强生产消费者模型
public class ProductorAndConsumerV1 {
	public static void main(String[] args) {
		List<PCData> queue = new ArrayList<>();
		int length = 10;
		ProducerV1 p1 = new ProducerV1(queue, length);
		ProducerV1 p2 = new ProducerV1(queue, length);
		ProducerV1 p3 = new ProducerV1(queue, length);
		ConsumerV1 c1 = new ConsumerV1(queue);
		ConsumerV1 c2 = new ConsumerV1(queue);
		ConsumerV1 c3 = new ConsumerV1(queue);
		
		//使用线程池
		ExecutorService service=Executors.newCachedThreadPool();
		service.execute(p1);
        service.execute(p2);
        service.execute(p3);
        service.execute(c1);
        service.execute(c2);
        service.execute(c3);
	}
}

class ConsumerV1 implements Runnable {

	private List<PCData> queue;

	public ConsumerV1(List<PCData> queue) {
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
				synchronized (queue) {
					if (queue.size() == 0) {
						queue.wait();
						queue.notifyAll();
					}
					data = queue.remove(0);
				}

				int re = data.getData() * 2;
				System.out.println(MessageFormat.format("{0} used: {1}*2={2}", Thread.currentThread().getId(),
						data.getData(), re));
				Thread.sleep(1000);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}

class ProducerV1 implements Runnable {

	private List<PCData> queue;
	private int length;

	public ProducerV1(List<PCData> queue, int length) {
		this.queue = queue;
		this.length = length;
	}

	@Override
	public void run() {
		try {
			while (true) {
				if (Thread.currentThread().isInterrupted()) {
					break;
				}

				Random random = new Random();
				int temp = random.nextInt(100);
				System.out.println(Thread.currentThread().getId() + " product：" + temp);

				PCData data = new PCData(temp);

				synchronized (queue) {
					if (queue.size() >= length) {
						queue.notifyAll();
						queue.wait();
					} else {
						queue.add(data);
					}
				}

				Thread.sleep(1000);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}