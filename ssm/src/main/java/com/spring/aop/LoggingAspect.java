package com.spring.aop;

public class LoggingAspect
{
	public void beforeMethod()
	{
		System.out.println("Logging: method start");
	}

	public void afterMethod()
	{
		System.out.println("Logging: method end.");
	}
}
