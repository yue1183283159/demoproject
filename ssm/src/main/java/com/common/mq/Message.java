package com.common.mq;

/**
 *消息，存入消息队列的消息对象
 **/
public class Message {
	private int what;//消息类型
	private Object data;//消息体，消息中的数据
	//封装消息处理对象，增加消息处理的灵活性，不同消息可以使用不同的处理方法
	private Handler handler;

	public void setHandler(Handler handler) {
		this.handler = handler;
	}
	
	public Handler getHandler() {
		return handler;
	}

	public int getWhat() {
		return what;
	}

	public void setWhat(int what) {
		this.what = what;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
