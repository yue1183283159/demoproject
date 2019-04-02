package com.common.dubbo.demo;

public class DemoServiceImpl implements DemoService {

	@Override
	public String sayHello(String name) {

		return "Hello, " + name;
	}

}
