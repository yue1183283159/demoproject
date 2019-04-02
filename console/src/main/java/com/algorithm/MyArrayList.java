package com.algorithm;

import java.util.Arrays;

/**
 * ArrayList底层是通过数组实现的。
 * 初始化集合->添加元素->内部数组长度是否足够
 * ->（不够长）创建新数组，复制原有数组数据到新数组
 * ->（够长）直接添加
 */
public class MyArrayList
{
	private static final int INITIAL_SIZE = 10;
	private int size = 0;
	private int[] array;

	public MyArrayList()
	{
		array = new int[INITIAL_SIZE];
	}

	public MyArrayList(int initial)
	{
		if (initial <= 0)
		{
			initial = INITIAL_SIZE;
		}
		array = new int[initial];
	}

	public void add(int num)
	{
		if (size == array.length)
		{
			array = Arrays.copyOf(array, size * 2);
		}
		array[size++] = num;
	}

	public int get(int i)
	{
		if (i >= size)
		{
			throw new IndexOutOfBoundsException("The index exceed the array max index.");
		}
		return array[i];
	}

	public int set(int i, int num)
	{
		int oldNum = get(i);
		array[i] = num;
		return oldNum;
	}

	public int size()
	{
		return size;
	}
}
