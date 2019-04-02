package com.common.redis;

import java.util.ArrayList;
import java.util.Set;

import redis.clients.jedis.Jedis;

public class LoginDemo {
	public String checkToken(Jedis jedis, String token) {
		return jedis.hget("login", token);
	}

	public void updateToken(Jedis jedis, String token, String user, String item) {
		long time = System.currentTimeMillis() / 1000;
		jedis.hset("login", token, user);// 维护令牌与用户之间的映射
		jedis.zadd("recent", time, token);// 保持令牌最后一次出现的时间
		if (item != null) {
			jedis.zadd("viewed:" + token, time, item);// 根据这个令牌来设置该用户在这个时候访问的商品名称
			jedis.zremrangeByRank("viewed:" + token, 0, -26);// 移除就的用户记录，只保留用户浏览过的25个商品记录
			jedis.zincrby("viewed:", -1, item);
		}
	}

}

class CleanSessionThread extends Thread {
	private Jedis jedis;
	private int limit;
	private boolean quit;

	public CleanSessionThread(int limit) {
		this.jedis = new Jedis();
		jedis.select(15);
		this.limit = limit;
	}

	public void quit() {
		quit = true;
	}

	public void run() {
		while (!quit) {
			long size = jedis.zcard("recent:");// 根据登录时间确定在线人数
			if (size <= limit) {
				try {
					Thread.sleep(1000);
				} catch (Exception e) {

				}
			} else {
				long endIndex = Math.min(size - limit, 100);
				Set<String> tokensSet = jedis.zrange("recent:", 0, endIndex - 1);
				String[] tokens = tokensSet.toArray(new String[tokensSet.size()]);
				ArrayList<String> sessionKeys = new ArrayList<>();
				for (String token : tokens) {
					sessionKeys.add("viewed:" + token);
				}

				jedis.del(sessionKeys.toArray(new String[sessionKeys.size()]));
				jedis.hdel("login:", tokens);
				jedis.zrem("recent:", tokens);
			}
		}
	}

}
