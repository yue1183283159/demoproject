package com.spring.ioc;

import java.util.List;
import java.util.Map;
import java.util.Properties;

public class Container {
	private List<String> list;
	private Map<String, Object> map;
	private Properties properties;

	//spring底层通过反射调用无参构造函数来创建类的对象，然后调用set方法为属性赋值
	//如果无参构造函数私有化，spring底层是如何构造对象的？
	//私有的构造函数，spring也能完成对象构造
	private Container() {
		System.out.println("init container");
	}
	
	public List<String> getList() {
		return list;
	}

	public void setList(List<String> list) {
		this.list = list;
	}

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

}
