package com.minimybatis;

import java.lang.reflect.Proxy;

//SqlSession提供一个getMapper方法来获取dao接口，dao由代理类动态创建。
//传入一个核心的Sql执行类SqlExecuteHandler,该类实现了InvocationHandler接口
public class SqlSession {
	@SuppressWarnings("unchecked")
	public <T> T getMapper(Class<T> cls) {
		return (T) Proxy.newProxyInstance(cls.getClassLoader(), new Class[] { cls }, new SqlExecuteHandler());
	}
}
