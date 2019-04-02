package com.spring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TimeMonitor {

	//切入点
	//@Pointcut("bean(container)")
	@Pointcut("within(com.spring.aop.Container)")
	public void pointCutMethod() {
		
	}
	
	@Around("pointCutMethod()")
	public Object aroundMethod(ProceedingJoinPoint jPoint) throws Throwable {
		Long startTime = System.currentTimeMillis();
		Object result = jPoint.proceed();
		Long endTime = System.currentTimeMillis();

		String targetClass = jPoint.getTarget().getClass().getName();
		String targetMethod = jPoint.getSignature().getName();

		System.out.println("执行" + targetClass + "的" + targetMethod + "方法，耗费的时间：" + (endTime - startTime)+"ms");
		return result;
	}
}
