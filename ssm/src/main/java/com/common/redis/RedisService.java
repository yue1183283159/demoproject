package com.common.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

@Service
public class RedisService
{
	@Autowired(required = false)
	private JedisSentinelPool sentinelPool;

	public void set(String key, String value)
	{
		Jedis jedis = sentinelPool.getResource();
		jedis.set(key, value);
		sentinelPool.returnResource(jedis);
	}

	public String get(String key)
	{
		Jedis jedis = sentinelPool.getResource();
		String value = jedis.get(key);
		sentinelPool.returnResource(jedis);
		return value;
	}

}
