package com.common.dubbo.demo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Consumer {
	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				"com/common/dubbo/demo/consumer.xml");

		context.start();
		DemoService demoService = (DemoService) context.getBean("demoService");
		String hello = demoService.sayHello("world");
		System.out.println(hello);
	}
}
