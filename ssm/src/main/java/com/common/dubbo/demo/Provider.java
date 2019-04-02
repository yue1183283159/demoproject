package com.common.dubbo.demo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Provider {
	public static void main(String[] args) throws Exception {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				"com/common/dubbo/demo/provider.xml");
		context.start();
		System.out.println("provider started.");
		System.in.read();//让程序一直运行
	}
}
