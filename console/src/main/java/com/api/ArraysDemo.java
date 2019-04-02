package com.api;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Arrays类主要提供数组的各种操作，纯工具类
 **/
public class ArraysDemo {
	public static void main(String[] args) {
		// 快速根据根据可变参数生成一个list。
		String[] strArr = { "A", "B", "C" };
		List<String> sList = Arrays.asList(strArr);
		System.out.println(sList);
		List sList2 = Arrays.asList("D", 1, new Date());
		System.out.println(sList2);

		// 二分查找
		int[] testNumArr = new int[100];
		Random random = new Random(System.currentTimeMillis());
		for (int i = 0; i < 100; i++) {
			testNumArr[i] = random.nextInt(1000);
		}
		testNumArr[random.nextInt(100)] = 200;

		int index = Arrays.binarySearch(testNumArr, 200);
		System.out.println("position index:" + index);
		System.out.println(Arrays.toString(testNumArr));

		// copyof,copyofrange
		String[] strArr1 = { "a", "b", "c", "d" };
		String[] copy = Arrays.copyOf(strArr1, 2);
		System.out.println(Arrays.toString(copy));
		String[] rangecopy = Arrays.copyOfRange(strArr1, 2, strArr1.length);
		System.out.println(Arrays.toString(rangecopy));

	}
}
