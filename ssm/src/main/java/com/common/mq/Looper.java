package com.common.mq;

/**
 * 消息迭代器：负责迭代消息队列，从队列中取数据
 * 设计：
 * 1. 一个线程一个looper 
 * 2. looper负责迭代消息队列，然后取出消息，交给对应对象执行
 **/
public class Looper {
	//每一个looper实例对象有自己的消息队列，每一个线程中有自己独立的looper，保证各个线程的工作不相互影响，独自处理自己收到的消息任务
	private MessageQueue mQueue = new MessageQueue();

	//线程中loop方法执行的标识。必须是原子操作，才能在不同线程中通过改变此值，让运行的线程停止
	private static volatile boolean isLoop = true;
	
	//存储线程共享变量。保证looper对象在每个线程中是独立的一份，不会被其他线程共享。
	private static ThreadLocal<Looper> td = new ThreadLocal<>();

	//在获取looper之前，先调用此方法将looper对象创建好并存入当前线程中
	public static void prepare() {
		Looper looper=td.get();
		if(looper!=null) {
			throw new RuntimeException("looper 已经存在");
		}
		
		looper = new Looper();
		td.set(looper);
	}
	
	//私有化构造方法，不允许外部实例化looper对象。
	private Looper() {}

	//外部通过此方法，拿到当前线程中looper对象
	public static Looper getLooper() {
		Looper looper = td.get();
		return looper;
	}

	// 迭代消息队列，获取消息进行处理
	public void loop() {
		while (isLoop) {
			Message message = mQueue.take();
			message.getHandler().handleMessage(message);
			//调用消息中封装的处理方法来处理消息，可以为不同的消息制定不同的处理方法。
			//也可以提供一个处理方法，在方法中增加消息类型判断调用不同的处理方法
			
			try {
				Thread.sleep(100);//短暂的休眠，可以降低程序对内存的使用率。
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static void cancel() {
		isLoop = false;
	}
	
	//从looper中获取消息队列，向里面存放消息
	public MessageQueue getMQ() {
		return mQueue;
	}
}
