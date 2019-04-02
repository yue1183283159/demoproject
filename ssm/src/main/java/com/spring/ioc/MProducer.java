package com.spring.ioc;

/** 生成者对象 */
public class MProducer {
	private MContainer container;

	public MContainer getContainer() {
		return container;
	}

	//让spring通过set方法注入对象
	public void setContainer(MContainer container) {
		this.container = container;
	}

}
