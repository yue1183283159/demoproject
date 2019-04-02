package com.common.redis;

import java.text.MessageFormat;
import java.util.Date;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;

public class CounterDemo {

	static RedisTemplate redisTemplate;
	static {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring-redis.xml");
		redisTemplate = ctx.getBean("redisTemplate", RedisTemplate.class);
	}

	private static int[] precision = { 1, 5, 60, 300, 3600, 18000, 86400 };// 1s, 5s, 1m,5m, 1h, 5h

	static void updateCounter(String name, int count) {
		long now = System.currentTimeMillis() / 1000;
		redisTemplate.multi();

		for (int prec : precision) {
			long pnow = (now / prec) * prec;
			String key = prec + ":" + name;
			redisTemplate.opsForSet().add("known:", key, 0);
			redisTemplate.opsForHash().increment("count:" + key, String.valueOf(pnow), count);
		}

		redisTemplate.exec();
	}

	static void updateStatus(String context, String type, String value, int timeout) {
		String destination = MessageFormat.format("status:{0}:{1}", context, type);
		String startKey = destination + ":start";
		
		redisTemplate.multi();

		redisTemplate.exec();
	}

	public static void main(String[] args) {
		// updateCounter("redis counter", 2);
	}
}
