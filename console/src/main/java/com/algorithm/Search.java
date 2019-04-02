package com.algorithm;

public class Search
{
	private int[] array;

	public Search(int[] array)
	{
		this.array = array;
	}

	/**
	 * 哨兵方式顺序查找
	 * 
	 * 这种方式比直接for循环查找比较的次数少一半。 for(int i=0;i<length;i++){if(key==array[i]){return
	 * i;}} 每次循环需要比较两次，一次是i<length,一次是key==array[i] 使用哨兵方式的while循环，之比较一次。
	 */
	public int sequentialSearch(int key)
	{
		if (key == array[0])
		{
			return 0;
		}
		// 临时保存array[0]的值
		int temp = array[0];
		array[0] = key;
		int i = array.length - 1;
		// 倒序比较
		while (array[i] != key)
		{
			i--;
		}
		array[0] = temp;
		if (i == 0)
		{
			return -1;
		}
		return i;
	}

	/**
	 * 二分查找 数列有序，使用顺序存储结构
	 */
	public int BinarySearch(int key)
	{
		return BinarySearch(key, 0, array.length - 1);

	}

	private int BinarySearch(int key, int start, int end)
	{
		if (start > end)
		{
			return -1;
		}
		int mid = start + (end - start) / 2;
		if (array[mid] == key)
		{
			return mid;
		} else if (key < array[mid])
		{
			return BinarySearch(key, start, mid - 1);
		} else
		{
			return BinarySearch(key, mid + 1, end);
		}
	}

}
