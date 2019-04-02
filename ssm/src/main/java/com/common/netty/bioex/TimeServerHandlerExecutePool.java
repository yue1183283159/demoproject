package com.common.netty.bioex;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
/**
 *创建线程池，消息队列来处理客户端socket。
 *由于线程池和消息队列都是有界的，因此无论客户端并发连接数多大，都不会导致线程个数过于膨胀或者内存溢出。 
 *但是底层的通信依然采用同步阻塞模型，无法从根本上解决问题
 **/
public class TimeServerHandlerExecutePool {
	private ExecutorService executor;

	public TimeServerHandlerExecutePool(int maxPoolSize, int queueSize) {
		executor = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(), maxPoolSize, 120L,
				TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(queueSize));
	}

	public void execute(Runnable task) {
		executor.execute(task);
	}
}
