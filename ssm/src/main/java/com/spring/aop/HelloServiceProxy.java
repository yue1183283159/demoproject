package com.spring.aop;

public class HelloServiceProxy implements HelloService
{
	private HelloService helloService;

	// 通过set方法创建关联
	// public void setHelloService(HelloService helloService)
	// {
	// this.helloService = helloService;
	//
	// }

	//通过构造方法赋值
	public HelloServiceProxy(HelloService helloService)
	{
		this.helloService = helloService;
	}

	@Override
	public void hello()
	{
		System.out.println("method.start " + System.nanoTime());
		helloService.hello();
		System.out.println("method.end " + System.nanoTime());

	}

}
