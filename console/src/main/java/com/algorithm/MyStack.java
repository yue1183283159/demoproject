package com.algorithm;

import java.util.Arrays;

public class MyStack
{
	int size;
	int[] array;

	public MyStack()
	{
		this(10);
	}

	public MyStack(int initSize)
	{
		if (initSize <= 0)
		{
			initSize = 10;
		}
		array = new int[initSize];
	}

	// 入栈
	public void push(int item)
	{
		if (size == array.length)
		{
			// 扩容
			array = Arrays.copyOf(array, size * 2);
		}
		array[size++] = item;
	}

	// 获取栈顶元素，没有出栈
	public int peek()
	{
		if (size == 0)
		{
			throw new IndexOutOfBoundsException("The stack is empty.");
		}

		return array[size - 1];
	}

	// 出栈，获取栈顶元素
	public int pop()
	{
		int item = peek();
		size--;// 直接让元素个数减一，不需要真的清除数据，下次入栈时会覆盖
		return item;
	}

	public boolean isFull()
	{
		return size == array.length;
	}

	public boolean isEmpty()
	{
		return size == 0;
	}

	public int size()
	{
		return size;
	}

}
