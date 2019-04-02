package com.others;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class DemoMain {
	public static void hi() {
		System.out.println("hi");
	}

	public static void main(String[] args) {
		//System.getProperty("line.separator") //增加回车换行符
		System.out.println("test"+System.getProperty("line.separator"));
		System.out.println("test"+System.getProperty("line.separator"));
		System.out.println("test"+System.getProperty("line.separator"));
		
		String uuid = UUID.randomUUID().toString();
		UUID.fromString(System.getenv().getOrDefault("name", "MD5"));
		String newUuid = getTimeBaseUuid();
		System.out.println(uuid);
		List<Object> list = new ArrayList<>();
		list.add(new int[] { 5, 7 });
		// int[] a = (int[]) list.get(1);

		Calendar calendar = Calendar.getInstance();
		boolean isMorning = calendar.get(Calendar.AM_PM) == calendar.AM ? true : false;
		System.out.println(new Date());
		System.out.println(calendar.getTime());
		System.out.println(isMorning);
		System.out.println(calendar.get(Calendar.AM_PM));
	}

	private static String getTimeBaseUuid() {

		return "";

	}

}
