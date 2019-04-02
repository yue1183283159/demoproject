package com.api;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateDemo
{
	public static void main(String[] args) throws Exception
	{
		Date date = new Date();
		System.out.println(date);

		long mills = date.getTime();
		System.out.println(mills);

		// 让Date对象指向昨天的此时此刻
		long yesterday = date.getTime() - 1000 * 60 * 60 * 24;
		date.setTime(yesterday);
		System.out.println(date);

		// System.out.println("input your birthdate:");
		// String birth = new Scanner(System.in).nextLine();
		String birth = "1989-09-24";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dob = sdf.parse(birth);
		System.out.println(dob);

		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStr = sdf1.format(date);
		System.out.println(dateStr);

		Date curDate = new Date();
		long minus = curDate.getTime() - dob.getTime();
		long days = minus / 1000 / 60 / 60 / 24;
		System.out.println("You have existed " + days);

		// 有时区，北京东八区，所以显示时间不是 1970年1月1日零点
		// CST是时区的意思
		// Thu Jan 01 08:00:00 CST 1970
		Date d = new Date();
		d.setTime(0);
		System.out.println(d);

		// Calendar.getInstance()
		// 在泰国，日本可能创建当地历法
		// 其他地区，创建公历对象
		Calendar cal = new GregorianCalendar();
		Calendar cal1 = Calendar.getInstance();
		System.out.println(cal.getTime());
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy年M月d日");
		System.out.println(sdf2.format(cal.getTime()));

		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int date1 = cal.get(Calendar.DATE);
		int hour = cal.get(Calendar.HOUR);
		int minute = cal.get(Calendar.MINUTE);
		int second = cal.get(Calendar.SECOND);

		// 用固定数字代码表示周几，日（1）一（2）二（3）三（4）四（5）五（6）六（7）
		int week = cal.get(Calendar.DAY_OF_WEEK);

		System.out.println(year + "-" + month + "-" + date1);
		System.out.println(hour + ":" + minute + ":" + second);
		System.out.println(week);

		// 输入年-月，打印日历
		String inputDate = "2018-5";
		SimpleDateFormat sdf4 = new SimpleDateFormat("yyyy-MM");
		Date iDate = sdf4.parse(inputDate);
		System.out.println("2018-5对应的默认日期：" + iDate);
		Calendar cal4 = Calendar.getInstance();
		cal4.setTime(iDate);
		// 周几
		int day = cal4.get(Calendar.DAY_OF_WEEK);
		// 获取当月的天数
		int maxDay = cal4.getActualMaximum(Calendar.DAY_OF_MONTH);
		System.out.println("2018-5对应的天数是：" + maxDay);
		System.out.println();
		/*
		 * 打印日历 
		 * 1 2 3 4 5 6 7 
		 * 日 一 二 三 四 五 六
		 */
		int count = 0;
		System.out.println("日\t一\t二\t三\t四\t五\t六");
		// 打印day-1个空格
		for (int i = 0; i < day - 1; i++)
		{
			System.out.print(" \t");
			count++;
		}
		// 1到maxDay, 打印7个值就换行
		for (int i = 1; i <=maxDay; i++)
		{
			System.out.print(i + "\t");
			count++;
			if (count == 7)
			{
				System.out.println();
				count = 0;
			}
		}
		
		System.out.println();
		System.out.println();
		System.out.println();
		

		// 获取任意一年的二月有多少天
		// set会进位，
		int yearStr = 2000;
		Calendar cal2 = Calendar.getInstance();
		cal2.set(Calendar.YEAR, yearStr);
		cal2.set(Calendar.MONTH, 1);
		cal2.set(Calendar.DATE, 1);
		// 得到2000年的2月1日的日期，然后取这个月的最大天数
		System.out.println(cal2.getTime());
		int max = cal2.getActualMaximum(Calendar.DATE);
		System.out.println(max);

		Calendar cal3 = Calendar.getInstance();
		cal3.add(Calendar.YEAR, 1);
		cal3.add(Calendar.MONDAY, 1);
		System.out.println(cal3.getTime());

	}
}
