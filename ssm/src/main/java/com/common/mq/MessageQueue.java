package com.common.mq;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 消息队列。存放消息。
 * 先进先出
 **/
public class MessageQueue {
	//使用阻塞消息队列，好处是：当消息队列满的时候，再次向消息队列中存消息，此时操作会被阻塞，直到可以存放了才会执行消息插入；
	//获取消息的时候，如果队列中没有消息，获取的操作会被阻塞，等待消息队列中存入消息之后再会执行获取。
	private static BlockingQueue<Message> mQueue = new ArrayBlockingQueue<>(10);

	public void put(Message message) {
		try {
			mQueue.put(message);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public Message take() {
		Message message = null;
		try {
			message = mQueue.take();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return message;
	}
}
