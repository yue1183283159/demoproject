package com.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisUtil
{
	private String address = "192.168.30.129";
	private int port = 6379;
	private String password = "infoshare";
	// 连接 redis 等待时间
	private int timeOut = 10000;

	// 可用连接实例的最大数目，默认值为8；
	// 如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)
	private int maxTotal = 1024;

	// 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8
	private int maxIdle = 200;

	// 等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException
	private int maxWait = 10000;
	// 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的
	private boolean testOnBorrow = true;
	// 连接池
	private JedisPool jedisPool = null;

	public RedisUtil()
	{
		try
		{
			JedisPoolConfig config = new JedisPoolConfig();
			config.setMaxTotal(maxTotal);
			config.setMaxIdle(maxIdle);
			config.setMaxWaitMillis(maxWait);
			config.setTestOnBorrow(testOnBorrow);
			jedisPool = new JedisPool(config, address, port, timeOut, password);

		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public Jedis getJedis()
	{
		if (jedisPool != null)
		{
			return jedisPool.getResource();
		}
		return null;
	}
}
