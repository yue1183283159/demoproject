package com.spring.ioc;

import org.junit.After;
import org.junit.Before;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestBase {

	ClassPathXmlApplicationContext ctx = null;

	@Before
	public void init() {
		ctx = new ClassPathXmlApplicationContext("spring-ioc2.xml");
	}

	@After
	public void close() {
		ctx.close();
	}
}
