package com.spring.aop;

import java.util.Arrays;

import org.springframework.stereotype.Component;

@Component
public class Container<T> {
	private Object[] array;
	private int size;

	// 无参构造函数，供spring构造对象使用，不然交给spring管理的时，spring创建对象会失败。
	public Container() {
		this(16);// 一些容器的默认容量大小是16，扩容因子是0.75
	}

	public Container(int initCap) {
		array = new Object[initCap];
	}

	public void add(T t) {
		// 判断容器是否已满，满了则扩容
		if (size == array.length) {
			// 数组扩容方法1
			// Object[] dest = new Object[array.length * 2];
			// System.arraycopy(array, 0, dest, 0, array.length);
			// array = dest;

			Arrays.copyOf(array, array.length * 2);
		}
		// 放数据
		array[size] = t;
		// 有效元素个数加1
		size++;

	}

	public int size() {
		return size;
	}
}
