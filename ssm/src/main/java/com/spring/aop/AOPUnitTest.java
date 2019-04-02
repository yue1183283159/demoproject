package com.spring.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AOPUnitTest {

	private static ClassPathXmlApplicationContext ctx;

	@Before
	public void init() {
		ctx = new ClassPathXmlApplicationContext("spring-aop.xml");
	}

	// @Test
	public void testCustomProxy() {
		// 代理类要实现接口，然后关联被代理类，这样才能不修改原来的代码
		HelloService helloService = new HelloServiceImpl();
		HelloServiceProxy helloServiceProxy = new HelloServiceProxy(helloService);
		// helloServiceProxy.setHelloService(helloService);
		helloServiceProxy.hello();
	}

	// 使用jdk中的动态代理类
	// @Test
	public void testDyProxy() {
		// 目标对象
		HelloService helloService = new HelloServiceImpl();
		// 为这个目标对象创建一个代理对象
		// 获得这个目标类的类加载器对象
		ClassLoader loader = helloService.getClass().getClassLoader();
		// 获得目标对象实现的接口
		Class<?>[] interfaces = helloService.getClass().getInterfaces();
		ServiceHandler handler = new ServiceHandler(helloService);
		HelloService proxy = (HelloService) Proxy.newProxyInstance(loader, interfaces, handler);
		proxy.hello();
	}

	// 使用spring aop特性，基于xml配置
	// @Test
	public void testXmlSpringAop() {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring-aop.xml");
		HelloService helloService = ctx.getBean("helloService", HelloService.class);
		helloService.hello();
		ctx.close();
	}

	// 使用注解实现aop
	// @Test
	public void testConfigSpringAop() {

		/*AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AOPConfig.class);
		OrderService orderService = ctx.getBean("orderServiceImpl", OrderService.class);
		orderService.saveOrder();
		ctx.close();*/
	}

	@Test
	public void testContainerAdd() {
		Container<String> strContainer= ctx.getBean("container", Container.class);
		strContainer.add("Admin");
		//System.out.println(strContainer.size());
	}
	
	@After
	public void destory() {
		ctx.close();
	}

}

class ServiceHandler implements InvocationHandler {
	private Object target;

	public ServiceHandler(Object target) {
		this.target = target;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("begin transaction");
		Object result = method.invoke(target, args);
		System.out.println("end transaction");
		return result;
	}
}
