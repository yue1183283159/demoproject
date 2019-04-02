package com.api;

import java.util.Scanner;

public class BasicOperateDemo
{
	public static void main(String[] args)
	{
		char chr = 'a';
		System.out.println('a' == 97);
		// \u0061 表示a -->
		// 自由落体距离h=1/2*g*t*t

		// long l=999999L;
		/*
		 * float f=3.14F; double d=3; double d1=3D;
		 */

		// 规则1：计算结果的数据类型，与最大的类型一致
		System.out.println(3 / 2);
		System.out.println(9 / 10);
		System.out.println(3d / 2);

		// 规则2：byte，short，char比int小的整数，运算时会先自动转成int
		byte a = -1; // 11111111
		// 左移16位可以，因为运算时先转成int然后在运算
		System.out.println(a << 16);
		byte b = 2;
		// byte s=a+b; //error,=右边的加运算是int类型， int不能赋值给byte

		// 规则3：整数运算溢出
		System.out.println(Integer.MAX_VALUE);
		System.out.println(Integer.MAX_VALUE + 1); // 整数最大值+1变成负数最小值
		System.out.println(300000000 * 60 * 60 * 24 * 365);
		System.out.println(300000000L * 60 * 60 * 24 * 365);

		// 规则4：浮点数运算不精确
		System.out.println(2 - 1.9);
		System.out.println(2 - 1.8);
		System.out.println(2 - 1.7);
		System.out.println(2 - 1.5);
		System.out.println(2 - 1.4);
		System.out.println(2 - 1.2);
		System.out.println(4.35 * 100);
		System.out.println(4.36 * 100);
		System.out.println(4.39 * 100);

		// 规则5：浮点数的特殊值
		// Infinity - 无穷大
		// NaN - Not a Number（i为虚数，不存在的数,i的2次方是-1，所以负数开平方就是虚数，不是一个数，所以NaN）
		System.out.println(3.14 / 0);
		System.out.println(Double.MAX_VALUE * 2);
		System.out.println(Math.sqrt(-4));

		System.out.println(1 % 3);
		System.out.println(2 % 3);
		System.out.println(3 % 3);
		System.out.println(4 % 3);
		System.out.println(5 % 3);

		// 能被4整除但不能被100整除；能被400整除
		// System.out.println("输入年号：");
		// int y = new Scanner(System.in).nextInt();
		int y = 2008;
		boolean is1 = y % 4 == 0;
		boolean is2 = y % 100 == 0;
		boolean is3 = y % 400 == 0;
		String rString = "平年";
		if ((is1 && !is2) || is3)
		{
			rString = "闰年";
		}
		System.out.println(rString);

		// int整数4个字节，拆分成4个byte类型值
		System.out.println("输入用于拆分的整数：");
		int n = new Scanner(System.in).nextInt();
		System.out.println("拆分之后每个字节是：");
		// int类型4个字节，32位，要取第一个字节位，需要向右移动24位，到达最后8位，形成最后一个字节。然后切出来。
		byte b1 = (byte) (n >> 24);
		byte b2 = (byte) (n >> 16);
		byte b3 = (byte) (n >> 8);
		byte b4 = (byte) (n >> 0);
		// 每次右移，把前面位置的字节移动到最后以为，然后强转成byte的时候，直接取最后一位，前面的截掉。
		// 这样会出现负数，因为一个字节的第一位是1的话，表示负数。
		System.out.println(b1);
		System.out.println(b2);
		System.out.println(b3);
		System.out.println(b4);

		// 将byte还原成int
		int r = 0;// int r=0是32位都是0
		// b1是原来的第一个字节，需要左移24位回到原来的位置，后面位都补0
		// 然后与0求或，或是上下位只要有1就是1,
		r = r | (b1 << 24 >>> 0);
		// b2是原来的第二位，只需要移动16位即可，但是不能置左移26位，这样它前面的8位就错了。
		// 需要先左移24位，把前面的8位顶出去，然后无符号右移动8位，这样前面8位全部是补0
		// 然后于r求或，这样前面8位才能得到正确值。其他位同理
		r = r | (b2 << 24 >>> 8);
		r = r | (b3 << 24 >>> 16);
		r = r | (b4 << 24 >>> 24);
		System.out.println("byte合并之后是：" + r);
	}
}
