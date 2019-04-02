package com.common.mq;

public class TestMQ {
	// 1. 两个线程：主线程，工作线程
	// 2. 主线程发消息给工作线程，工作线程执行耗时操作
	// 3. 工作线程执行完结束给主线程发消息，主线程执行页面更新
	//模拟的是很多工作原理，比如安卓系统，浏览器ajax异步请求与主线程等
	static Thread mainThread;
	static Handler mainHandler;
	
	public static void main(String[] args) {
		WorkThread w1 = new WorkThread();
		w1.start();
		//主线程中开启工作线程，工作线程开启之后创建线程中looper对象，然后迭代消息队列
		//从工作线程中获取looper对象，然后传给Handler，构造此looper的handler对象，
		//调用handler发送消息，是将消息放入looper中的消息队列。完成主线程将消息发给工作线程的流程。
		// looper中的loop方法不停迭代消息队列，取出消息之后再交给handler处理。完成工作线程中消息处理流程。
		Handler h1 = new Handler(w1.getLooper()) {
			@Override
			public void handleMessage(Message message) {
				if (message.getWhat() == 1) {
					Object data = message.getData();
					System.out.println(Thread.currentThread().getName()+"*****>"+ data);
					
					//工作线程给主线程发消息，将主线程的handle对象，封装到发给主线程的消息对象中，然后send消息。
					//sendMessage将消息发送给主线程的looper中消息队列，主线程的looper迭代消息队列，拿到消息只会调用handle处理消息。
					Message mainMsg = new Message();
					mainMsg.setData(Thread.currentThread().getName()+" is end.");
					mainMsg.setWhat(10);
					mainMsg.setHandler(mainHandler);
					mainHandler.sendMessage(mainMsg);
				}
			}
		};
		
		Message message = new Message();
		message.setData("hello");
		message.setWhat(1);
		message.setHandler(h1);
		h1.sendMessage(message);
		
		WorkThread w2=new  WorkThread();
		w2.start();
		Handler h2=new Handler(w2.getLooper()) {
			@Override
			public void handleMessage(Message message) {
				if (message.getWhat() == 1) {
					Object data = message.getData();
					System.out.println(Thread.currentThread().getName()+"----->"+ data);
					
					Message mainMsg = new Message();
					mainMsg.setData(Thread.currentThread().getName()+" is end.");
					mainMsg.setWhat(10);
					mainMsg.setHandler(mainHandler);
					mainHandler.sendMessage(mainMsg);
				}
			}
		};
		
		message = new Message();
		message.setData("hi");
		message.setWhat(1);
		message.setHandler(h2);
		h2.sendMessage(message);
		
		Looper.prepare();//主线程中创建looper对象
		mainThread=Thread.currentThread();//获取主线程对象
		//创建主线程消息使用的handle对象，重写handleMessage方法。
		mainHandler=new Handler(Looper.getLooper()){
			@Override
			public void handleMessage(Message message) {
				if(message.getWhat()==10) {
					System.out.println("Main:" +message.getData());	
				}				
			}
		};
		
		//主线程中looper对象执行loop方法，获取消息队列中消息，然后进行处理
		Looper.getLooper().loop();	
	}
}

//工作线程，工作线程要创建looper对象，然后通过looper对象去迭代消息队列中数据
class WorkThread extends Thread {
	private Looper looper;

	@Override
	public void run() {
		synchronized (this) {
			Looper.prepare();//创建looper
			looper = Looper.getLooper();
			notifyAll();//保证从线程中looper对象，然后通知获取looper的线程可以执行获取动作了，避免出现空对象。
		}

		looper.loop();//迭代消息队列
	}

	public synchronized Looper getLooper() {
		while (looper == null) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		return looper;
	}

}