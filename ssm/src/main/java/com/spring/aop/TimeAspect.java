package com.spring.aop;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Service;

@Aspect
@Service
public class TimeAspect
{
	@Pointcut("bean(orderServiceImpl)")
	public void pointcut() {}
	
	@Before("pointcut()")
	public void before()
	{
		System.out.println("Time: method start. "+System.nanoTime());
	}

	@After("pointcut()")
	public void after()
	{
		System.out.println("Time: method end. "+System.nanoTime());
	}
}
