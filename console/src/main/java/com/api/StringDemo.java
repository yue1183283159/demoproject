package com.api;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringDemo
{

	public static void main(String[] args)
	{
		String str1 = new String("abc");
		char data[] = { 'a', 'b', 'c' };
		String str2 = new String(data);// 新分配内存
		String s1 = "abc";// 在常量池中新分配内存
		String s2 = "abc";// 访问常量池中存在的对象
		System.out.println(str2 == s1);
		System.out.println(s1 == s2);

		// +字符串就会新建
		String s0 = "testheloworldsaadministrator";
		// String s = "";
		// // 从1970-1-1 0点开始的毫秒值
		// long startTime = System.currentTimeMillis();
		// for (int i = 0; i < 10000; i++)
		// {
		// //s += s0;
		// }
		// long totalTime = System.currentTimeMillis() - startTime;
		// System.out.println(totalTime);

		StringBuilder sb = new StringBuilder("abc");
		sb.append("def");
		sb.deleteCharAt(2);
		System.out.println(sb.toString());
		System.out.println(sb.subSequence(1, 2));

		System.out.println();

		long t1 = System.currentTimeMillis();
		StringBuilder sb1 = new StringBuilder();
		// for(int i=0;i<10000;i++) {
		// sb1.append("abcdefg");
		// }
		for (int i = 0; i < 10; i++)
		{
			sb1.append("abcdefg");
		}

		System.out.println(System.currentTimeMillis() - t1);
		System.out.println(sb1.toString());

		System.out.println();
		System.out.println(sb1.codePointAt(1));
		System.out.println(sb1.codePointBefore(3));

		String s4 = "abc";
		byte[] b4 = s4.getBytes();
		System.out.println(Arrays.toString(b4));
		// 指定编码格式编码byte[]得到字符串
		String s5 = new String(b4, Charset.forName("utf-8"));
		System.out.println(s5);

		System.out.println();

		// 字符串的正则表达式相关方法
		System.out.println("Input the phone number:");
		// String phone=new Scanner(System.in).nextLine();
		String phone = "(010)-23232323232";
		/*
		 * 1234567 12345678 010-1234567 0102-12345678 (010)12345678
		 * 
		 * \d{7,8} ()?\d{7,8} (\d{3,4}-|)?\d{7,8} (\d{3,4}-|\(\d{3,4}\))?\d{7,8}
		 */

		String pattern = "(\\d{3,4}-|\\(\\d{3,4}\\))?\\d{7,8}";
		if (phone.matches(pattern))
		{
			System.out.println("phone format is right");
		} else
		{
			System.out.println("phone format is wrong.");
		}

		String testStr1 = "test,dfdfd tererer";
		String[] sArr = testStr1.split("[,\\s]");
		System.out.println(Arrays.toString(sArr));
		System.out.println(String.join("|", sArr));

		
		
		String viewInfo="阅读(2)";
		String numberpattern="\\d+";
		Pattern numPattern=Pattern.compile(numberpattern);
		Matcher matcher=numPattern.matcher(viewInfo);
		if(matcher.find()) {
			System.out.println("read count:"+matcher.group(0));
		}
		
		
		
		
		
		
		
		System.out.println();
		System.out.println();
		String str = "hellowworld";
		System.out.println(str.indexOf("f"));
		System.out.println(str.indexOf("e"));
		// 指定fromIndex索引处开始查找子字符串str第一次
		System.out.println(str.indexOf("o", 5));
		System.out.println(str.lastIndexOf("o"));

		System.out.println("l occured: " + calOccuredNum("l", str));
		System.out.println("'上海自来水来自海上' 是 回文：" + checkIsHui("上海自来水来自海上"));

		int int1 = Integer.parseInt("345");
		System.out.println(int1);
		int int2 = Integer.valueOf(5);// 直接冲缓存对象中取值，不会新创建对象

		Integer a = new Integer(23);
		Integer b = Integer.valueOf(23);
		Integer c = Integer.valueOf(23);
		System.out.println(a == b);
		System.out.println(b == c);

		a = a + 1;// 右边拆箱之后+1，然后再自动装箱保存到a中
		// a=Integer.valueOf(a.intValue()+1)

		System.out.println(a.byteValue());
		System.out.println(a.shortValue());
		System.out.println(a.longValue());

		System.out.println(Integer.toBinaryString(255));
		System.out.println(Integer.toHexString(255));
		System.out.println(Integer.toOctalString(255));

		// Infinity无穷大
		System.out.println(Double.isInfinite(3.14 / 0));
		// NaN
		System.out.println(Double.isNaN(Math.sqrt(-34)));

	}

	/*
	 * 自字符出现的次数
	 */
	static int calOccuredNum(String subStr, String orginalStr)
	{
		int fromIndex = 0;
		int index = 0;
		int count = 0;
		while (index != -1)
		{
			index = orginalStr.indexOf(subStr, fromIndex);
			if (index != -1)
			{
				count++;
				fromIndex = index + subStr.length();
			}
		}
		return count;
	}

	/*
	 * 判断字符串是回文“上海自来水来自海上”
	 */
	static boolean checkIsHui(String str)
	{
		for (int i = 0; i < str.length() / 2; i++)
		{
			char c1 = str.charAt(i);
			char c2 = str.charAt(str.length() - 1 - i);
			if (c1 != c2)
			{
				return false;
			}
		}
		return true;
	}

}
