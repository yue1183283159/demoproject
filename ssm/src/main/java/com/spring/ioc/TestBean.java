package com.spring.ioc;

import java.util.List;

import org.junit.Test;



public class TestBean extends TestBase {

	//@Test
	public void testContainer() {
		Container container = ctx.getBean("container", Container.class);
		List<String> list = container.getList();
		System.out.println(list);
		System.out.println(list.getClass().getName());
	}

	@Test
	public void testBeans() {
		MProducer producer = ctx.getBean("mproducer", MProducer.class);
		producer.getContainer().addData("test data");
		MConsumer consumer = ctx.getBean("mconsumer", MConsumer.class);
		System.out.println(consumer.getContainer().getData());

	}
}
