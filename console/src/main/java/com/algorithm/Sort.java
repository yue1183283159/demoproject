package com.algorithm;

public class Sort
{
	private int[] array;

	public Sort(int[] array)
	{
		this.array = array;
	}

	/**
	 * 冒泡：从小到大
	 */
	public void bubbleSort1()
	{
		if (array != null && array.length > 0)
		{
			int lenght = array.length;
			for (int i = 1; i < lenght; i++)
			{
				for (int j = 0; j < lenght - i; j++)
				{
					if (array[j] > array[j + 1])
					{
						int temp = array[j];
						array[j] = array[j + 1];
						array[j + 1] = temp;
					}
				}
			}
		}

	}

	/**
	 * 冒泡：从大到小
	 */
	public void bubbleSort2()
	{
		if (array != null && array.length > 0)
		{
			int lenght = array.length;
			for (int i = 1; i < lenght; i++)
			{
				for (int j = 0; j < lenght - i; j++)
				{
					if (array[j] < array[j + 1])
					{
						int temp = array[j];
						array[j] = array[j + 1];
						array[j + 1] = temp;
					}
				}
			}
		}

	}

	public void quickSort()
	{
		quickSort(array, 0, array.length - 1);
	}

	private void quickSort(int[] src, int begin, int end)
	{
		if (begin < end)
		{
			int key = src[begin];
			int i = begin;
			int j = end;

			while (i < j)
			{
				while (i < j && src[j] > key)
				{
					j--;
				}

				if (i < j)
				{
					src[i] = src[j];
					i++;
				}

				while (i < j && src[i] < key)
				{
					i++;
				}

				if (i < j)
				{
					src[j] = src[i];
					j--;
				}

			}

			src[i] = key;
			quickSort(src, begin, i - 1);
			quickSort(src, i + 1, end);
		}
	}

	public void insertSort()
	{
		int length = array.length;
		if (length > 0)
		{
			for (int i = 1; i < length; i++)
			{
				int temp = array[i];
				int j = i;
				for (; j > 0 && array[j - 1] > temp; j--)
				{
					array[j] = array[j - 1];
				}

				array[j] = temp;
			}
		}
	}

	public void selectSort()
	{
		int length = array.length;
		for (int i = 0; i < length; i++)
		{
			int minIndex = i;
			for (int j = i + 1; j < length; j++)
			{
				if (array[j] < array[minIndex])
				{
					minIndex = j;
				}
			}

			if (minIndex != i)
			{
				int temp = array[minIndex];
				array[minIndex] = array[i];
				array[i] = temp;
			}
		}
	}

	public int[] getArray()
	{
		return array;
	}
	
	public void pringArray()
	{
		for (int i = 0; i < array.length; i++)
		{
			System.out.print(array[i] + " ");
		}
	}
}
