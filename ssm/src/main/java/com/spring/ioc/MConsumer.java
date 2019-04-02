package com.spring.ioc;

/** 消费者对象 */
public class MConsumer {
	private MContainer container;

	//让spring通过构造方法注入container对象
	public MConsumer(MContainer container) {
		this.container = container;
	}

	public MContainer getContainer() {
		return container;
	}
}
