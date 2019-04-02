package com.spring.ioc;

import org.springframework.stereotype.Service;

@Service
public class HelloService
{
	public void sayHello()
	{
		System.out.println("Hello Spring IOC");
	}
	
	int count;

	public HelloService()
	{
		System.out.println("Init HelloService.");

	}

	public synchronized void count()
	{
		count++;
	}

	public void init()
	{
		System.out.println("invoke init() method.");
	}

	public void destroy()
	{
		System.out.println("invoke destroy() method.");
	}
}
