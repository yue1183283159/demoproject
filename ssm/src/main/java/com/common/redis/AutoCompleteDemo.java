package com.common.redis;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;

public class AutoCompleteDemo {

	static RedisTemplate redisTemplate;
	static {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring-redis.xml");
		redisTemplate = ctx.getBean("redisTemplate", RedisTemplate.class);
	}

	static void addUpdateContact(String user, String contact) {
		String key = "recent:" + user;
		redisTemplate.multi();
		redisTemplate.opsForList().remove(key, 1, contact);// 如果联系人在列表中了，就先删除
		redisTemplate.opsForList().leftPush(key, contact);// 然后就联系人加载列表的左边，也就是排第一个
		redisTemplate.opsForList().trim(key, 0, 99);// 如果最近联系人的数量超过了100个，就自动移除一百之后的人
		redisTemplate.exec();
	}

	static String[] fetchAutoCompleteList(String user, String prefix) {
		List<String> candidates = redisTemplate.opsForList().range("recent:" + user, 0, -1);
		List<String> matches = new ArrayList<>();
		for (String contact : candidates) {
			if (contact.toLowerCase().startsWith(prefix.toLowerCase())) {
				matches.add(contact);
			}
		}

		return matches.toArray(new String[matches.size()]);
	}

	public static void main(String[] args) {

		boolean isrunning = true;
		while (isrunning) {
			String inKeys = new Scanner(System.in).nextLine();
			//System.out.println(inKeys);
			//addUpdateContact("101", inKeys);
			String[] matches = fetchAutoCompleteList("101", inKeys);
			for (String contact : matches) {
				System.out.println(contact);
			}
			
			if ("end".equals(inKeys)) {
				isrunning = false;
			}
		}

	}

}
