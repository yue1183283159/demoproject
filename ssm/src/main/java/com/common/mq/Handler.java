package com.common.mq;

/**
 *消息处理类，用来向looper对象的消息队列放消息，和处理从looper中获取到的消息
 **/
public class Handler {
	//消息队列跟looper有关联，handler中要关联looper，从looper中获取消息队列然后处理消息
	private Looper looper;

	public Handler(Looper looper) {
		this.looper = looper;
	}
	
	//消息发给谁，要看looper关联的线程
	public void sendMessage(Message message) {
		looper.getMQ().put(message);
	}
	
	//一般设置成空方法，在其他线程中重写此方法，这样能够使处理消息更灵活。
	//处理消息：handler关联的那个线程的looper，就由那个线程调用此方法处理消息
	public void handleMessage(Message message) {
		/*if (message.getWhat() == 1) {
			Object data = message.getData();
			System.out.println(Thread.currentThread().getName()+"->"+ data);
		}*/
	}
}
