package com.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

/*
 * 数组，流程控制语句。
 */
public class ForWhileDemo
{

	public static void main(String[] args)
	{
		// calTax();

		// calBMI();

		// calPayMoney();

		// convet1();

		/*
		 * System.out.println("Input integer number:"); int n = new
		 * Scanner(System.in).nextInt(); if (testZhi(n)) { System.out.println("是质素"); }
		 * else { System.out.println("不是质素"); }
		 */

		// System.out.println("打印不同形状的* ");
		// outputShape();

		// System.out.println("打印乘法口诀");
		// outputKj();

		// System.out.println("百元买百鸡");
		// calChikenNum();

		// testBreak();

		// random [0,1)
		// 不确定多少次能到想要的值，for循环的结束条件空，无条件执行.这种情况下要使用break结束循环
		/*
		 * for (int i = 1;; i++) { double n = Math.random(); if (n > 0.999) {
		 * System.out.println("执行了" + i + "次"); break; } }
		 * 
		 * for (int i = 1;; i++) { double n = Math.random(); if (n <= 0.999) { continue;
		 * } System.out.println("执行了" + i + "次"); break; }
		 */

		// countNum();

		// 求两个数的最大公约数，最小公倍数.
		// 求一个数的约数24, 16
		/*
		 * List<Integer> nl1 = getYue(24); System.out.println(nl1); List<Integer> nl2 =
		 * getYue(16); System.out.println(nl2); int maxN = 0; if (nl1.size() >
		 * nl2.size()) { maxN = getMaxSameVaue(nl2, nl1); } else { maxN =
		 * getMaxSameVaue(nl1, nl2); } System.out.println("最大公约数是：" + maxN);
		 */

		// 生成字母的大小写形式
		/*
		 * char[] arr = getLetter(); System.out.println(Arrays.toString(arr));
		 */

		// 交换数组中两个位置的元素 找出最小值的索引
		/*
		 * int[] arr1 = { 2, 4, 6, 3, 1 };
		 * System.out.println(Arrays.toString(changeNum(arr1, 2, 3)));
		 
		 * int min = arr1[0]; int index = 0; for (int i = 1; i < arr1.length; i++) { if
		 * (arr1[i] < min) { index = i; min = arr1[i]; } }
		 * System.out.println("min index:" + index + ", the value is:" + min);
		 */
	}

	
	
	public static int[] changeNum(int[] arr, int index1, int index2)
	{
		int temp = arr[index1];
		arr[index1] = arr[index2];
		arr[index2] = temp;
		return arr;
	}

	// 生成26个字母的大小写形式
	public static char[] getLetter()
	{
		char[] arr = new char[52];
		for (int i = 0; i < 52; i++)
		{
			if (i < 26)
			{
				arr[i] = (char) ('a' + i);
			} else
			{
				arr[i] = (char) ('A' + i - 26);
			}
		}
		return arr;
	}

	private static int getMaxSameVaue(List<Integer> mins, List<Integer> maxs)
	{
		int max = 0;
		for (int n : mins)
		{
			if (maxs.contains(n) && n > max)
			{
				max = n;
			}
		}
		return max;
	}

	private static List<Integer> getYue(int n)
	{
		List<Integer> nl1 = new ArrayList<Integer>();
		for (int i = 1; i <= n; i++)
		{
			if (n % i == 0)
			{
				nl1.add(i);
			}
		}
		return nl1;
	}

	// random产生随机数一般是左闭右开
	// 统计数组中0-9数字出现的次数
	public static void countNum()
	{
		int[] arr = new int[20];
		for (int i = 0; i < arr.length; i++)
		{
			int n = new Random().nextInt(10);
			arr[i] = n;
		}

		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		for (int i = 0; i < arr.length; i++)
		{
			if (map.keySet().contains(arr[i]))
			{
				map.put(arr[i], map.get(arr[i]) + 1);
			} else
			{
				map.put(arr[i], 1);
			}
		}

		for (int i : arr)
		{
			System.out.print(i + " ");
		}
		System.out.println();
		for (int key : map.keySet())
		{
			System.out.print(key + ":" + map.get(key) + ",");
		}
	}

	public static void testBreak()
	{
		for (int i = 0; i <= 5; i++)
		{
			if (i == 4)
			{
				break;
			}
			System.out.print(i);
		}

		for (int i = 0; i <= 5; i++)
		{
			if (i == 4)
			{
				continue;
			}
			System.out.print(i);
		}
	}

	/*
	 * 外层循环公鸡数量g从0到20递增 买g只公鸡后最多还能买多少母鸡max 内层循环母鸡数量m从0到max 计算小鸡数量存到变量x
	 * 数量相加g+m+x==100 输出这个组合
	 */

	public static void calChikenNum()
	{
		System.out.println("公鸡5元1只");
		System.out.println("母鸡3元1只");
		System.out.println("小鸡1元3只");
		System.out.println("5x+3y+(1/3)z=100");
		System.out.println("x+y+z=100");
		System.out.println("求x,y,z");
		for (int x = 0; x <= 20; x++)
		{
			int money = 100 - 5 * x;
			int max = money / 3;
			for (int y = 0; y <= max; y++)
			{
				int z = (money - 3 * y) * 3;
				if (x + y + z == 100)
				{
					System.out.println("公鸡：" + x + ", 母鸡:" + y + ", 小鸡：" + z);
				}
			}
		}
	}

	public static void outputKj()
	{
		for (int i = 1; i <= 9; i++)
		{
			for (int j = 1; j <= i; j++)
			{
				if (j == 3 && (i == 3 || i == 4))
				{
					System.out.print(" ");// 让每一列都对齐
				}
				System.out.print(j + "*" + i + "=" + j * i + " ");
			}
			System.out.println();
		}
	}

	public static void outputShape()
	{
		for (int i = 0; i < 5; i++)
		{
			for (int j = 0; j < 5; j++)
			{
				System.out.print("*");
			}
			System.out.println();
		}

		for (int i = 1; i < 6; i++)
		{
			for (int j = 0; j < i; j++)
			{
				System.out.print("*");
			}
			System.out.println();
		}

		for (int i = 1; i <= 5; i++)
		{
			for (int j = 5 - i; j > 0; j--)
			{
				System.out.print(" ");
			}
			for (int j = 0; j < 2 * i - 1; j++)
			{
				System.out.print("*");
			}
			System.out.println();
		}
	}

	public static boolean testZhi(int n)
	{
		// 质素，素数只能被1和自身整除的数
		// 2到n开方+1，找到没有能把n整除的值
		double max = Math.sqrt(n) + 1;
		for (int i = 2; i < max; i++)
		{
			if (n % i == 0)
			{
				return false;
			}
		}
		return true;
	}

	/*
	 * 百分制转5档分制 A [90,100] B [70,90) C [60,70) D [20,60) E [0,20)
	 */
	public static void convet1()
	{
		System.out.println("Input the score:");
		int s = new Scanner(System.in).nextInt();
		if (s < 0 || s > 100)
		{
			System.out.println("the score is invalid.");
		}
		String r = "";
		switch (s / 10)
		{
		case 10:
		case 9:
			r = "A";
			break;
		case 8:
		case 7:
			r = "B";
		case 6:
			r = "C";
		case 5:
		case 4:
		case 3:
		case 2:
			r = "D";
		default:
			r = "E";
			break;
		}
		System.out.println(r);
	}

	/*
	 * 月供，还款
	 */
	public static void calPayMoney()
	{
		System.out.println("Input your money:");
		double p = new Scanner(System.in).nextDouble();
		System.out.println("Input your tax:");
		double r = new Scanner(System.in).nextDouble();
		System.out.println("Input your year:");
		double y = new Scanner(System.in).nextDouble();

		double pay = (p * r * Math.pow(1 + r, y)) / (Math.pow(1 + r, y) - 1);
		System.out.println(pay);
	}

	public static void calBMI()
	{
		System.out.println("Input your hight:");
		double h = new Scanner(System.in).nextDouble();
		System.out.println("Input your weight:");
		double w = new Scanner(System.in).nextDouble();
		double bmi = w / (h * h);
		System.out.println("Your BMI is:" + bmi);
	}

	/*
	 * 个人所得税速算
	 */
	public static void calTax()
	{
		System.out.println("Input your salary：");
		double sal = new Scanner(System.in).nextDouble();

		if (sal <= 3500)
		{
			System.out.println("Not reach the tax.");
			return;
		}

		sal -= 3500;
		// 根据salary的范围，来确定税率和速算扣除率
		double r = 0;
		int k = 0;
		if (sal <= 1500)
		{
			r = 0.03;
			k = 0;
		} else if (sal < 4500)
		{
			r = 0.1;
			k = 105;
		} else if (sal <= 9000)
		{
			r = 0.2;
			k = 555;
		} else if (sal <= 45000)
		{
			r = 0.25;
			k = 1005;
		} else if (sal <= 55000)
		{
			r = 0.3;
			k = 2755;
		} else if (sal <= 80000)
		{
			r = 0.35;
			k = 5505;
		} else
		{
			r = 0.45;
			k = 13505;
		}
		double tax = sal * r - k;
		System.out.println("Your'tax is:" + tax);
	}

}
