package com.redis;

import redis.clients.jedis.Jedis;

public class SubThread implements Runnable
{

	private Subscriber subscriber;
	private Jedis jedis;

	public SubThread(Subscriber subscriber)
	{
		this.subscriber = subscriber;
		this.jedis = new RedisUtil().getJedis();
	}

	@Override
	public void run()
	{
		try
		{
			jedis.subscribe(subscriber, "mychannel");
		} catch (Exception e)
		{
			System.out.println(String.format("subsrcibe channel error, %s", e));
		} finally
		{
			if (jedis != null)
			{
				jedis.close();
			}
		}
	}

}
