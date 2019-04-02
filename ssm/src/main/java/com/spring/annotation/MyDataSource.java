package com.spring.annotation;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
@Lazy(true) //true可以省略，默认值，不使用这个注解，表示立刻加载
public class MyDataSource {

	@PostConstruct//init-method,初始化时调用
	public void init() {

	}

	@PreDestroy//destory方法，销毁时调用
	public void destory() {

	}
}
