package com.spring.ioc;

import java.util.ArrayList;
import java.util.List;

//生产者消费者模式，需要一个中间容器来存放数据，生产者一直向容器中放数据，消费者一直从容器中取数据
//解耦，缓冲
public class MContainer {
	private List<String> list = new ArrayList<>();

	public void addData(String data) {
		list.add(data);
	}

	public List<String> getData() {
		return list;
	}
}
