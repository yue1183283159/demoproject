package com.common.redis;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

public class RedisUnitTest {
	ClassPathXmlApplicationContext ctx = null;

	@Before
	public void init() {
		ctx = new ClassPathXmlApplicationContext("spring-redis.xml");
	}

	//@Test
	public void testRedisSentinel() {
		//1.定义哨兵集合
		Set<String> sets=new HashSet<>();
		//2.向集合中添加哨兵节点
		sets.add(new HostAndPort("192.168.0.123", 26379).toString());
		sets.add(new HostAndPort("192.168.0.123", 26380).toString());
		sets.add(new HostAndPort("192.168.0.123", 26381).toString());
		//定义哨兵连接池
		JedisSentinelPool sentinelPool=new JedisSentinelPool("mymaster", sets);
		Jedis jedis= sentinelPool.getResource();
		jedis.set("name", "sa");
		System.out.println(jedis.get("name"));
	}
	
	//@Test
	public void testRedis() {
		RedisTemplate redisTemplate = ctx.getBean("redisTemplate", RedisTemplate.class);
		System.out.println(redisTemplate);

		redisTemplate.opsForValue().set("name", "sa");
		redisTemplate.opsForValue().set("age", 23);
		System.out.println(redisTemplate.opsForValue().get("name"));
		System.out.println(redisTemplate.opsForValue().get("age"));

		redisTemplate.opsForList().leftPush("slist", "a");
		redisTemplate.opsForList().rightPush("slist", "b");
		redisTemplate.opsForList().rightPush("slist", "c");
		redisTemplate.opsForList().rightPush("slist", "d");
		System.out.println(redisTemplate.opsForList().leftPop("slist"));
		List<Object> sList = redisTemplate.opsForList().range("slist", 0, -1);
		System.out.println(sList);
		System.out.println(redisTemplate.opsForList().size("slist"));

		redisTemplate.opsForHash().put("staff", "id", 1001);
		redisTemplate.opsForHash().put("staff", "name", "CSI Officer");

		Set<String> staffKeys = redisTemplate.opsForHash().keys("staff");
		System.out.println(staffKeys);
		System.out.println(redisTemplate.type("staff"));
		redisTemplate.opsForHash().get("staff", "name");

		Article article = new Article();
		article.setId("10101");
		article.setTitle("Test object");
		article.setLink("www.baidu.com");
		article.setPoster("u:1001");
		article.setTime(new Date().getTime());
		article.setVotes(1);
		Article article1 = new Article();
		article1.setId("10102");
		article1.setTitle("Test object 2");
		article1.setLink("www.xinlang.com");
		article1.setPoster("u:1002");
		article1.setTime(new Date().getTime());
		article1.setVotes(1);

		Map<String, Article> aMap = new HashMap<>();
		aMap.put(article.getId(), article);
		aMap.put(article1.getId(), article1);

		redisTemplate.opsForHash().putAll("article_obj", aMap);

		System.out.println(redisTemplate.type("article_obj"));
		System.out.println(redisTemplate.opsForHash().size("article_obj"));
		Article art = (Article)redisTemplate.opsForHash().get("article_obj", "10102");
		System.out.println(art.getTitle());
		
		redisTemplate.opsForSet().add("staff_id", 101);
		redisTemplate.opsForSet().add("staff_id", 102);
		redisTemplate.opsForSet().add("staff_id", 101);
		System.out.println(redisTemplate.opsForSet().members("staff_id"));
		
		redisTemplate.opsForZSet().add("staff_score", "staff_101", 100);
		redisTemplate.opsForZSet().add("staff_score", "staff_102", 130);
		redisTemplate.opsForZSet().add("staff_score", "staff_103", 120);
		
		Set<Object> staffScoreSet=redisTemplate.opsForZSet().range("staff_score", 0, 150);
		System.out.println(staffScoreSet);
		long count= redisTemplate.opsForZSet().count("staff_score", 99, 125);
		System.out.println(count);
	}

	// @Test
	public void testArticleVoteRedis() {
		RedisTemplate redisTemplate = ctx.getBean("redisTemplate", RedisTemplate.class);
		ArticleVoteRedis articleVoteRedis = new ArticleVoteRedis(redisTemplate);
		articleVoteRedis.postArticle("user:1001", "first article", "baidu.com");

		Set<Object> keys = redisTemplate.keys("article:*");
		System.out.println(keys);

		articleVoteRedis.getArticles(1, "");
		System.out.println();
		System.out.println();
		System.out.println();
		articleVoteRedis.voteArticle(1, "article:10.0");
	}

	@After
	public void destory() {
		// 清除redis中的数据
		// RedisTemplate redisTemplate = ctx.getBean("redisTemplate",
		// RedisTemplate.class);
		// redisTemplate.getConnectionFactory().getConnection().flushDb();
		ctx.close();
	}
}
