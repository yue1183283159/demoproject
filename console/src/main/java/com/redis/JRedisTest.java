package com.redis;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.Test;

import redis.clients.jedis.Jedis;

public class JRedisTest {
    private RedisUtil redisUtil = new RedisUtil();

    @Test
    public void testStr() {
        System.out.println();
        System.out.println();
        System.out.println("Redis String");
        Jedis jedis = redisUtil.getJedis();
        //jedis.set("name", "admin");
        System.out.println(jedis.get("name"));
        jedis.close();
    }

    @Test
    public void testMap() {
        System.out.println();
        System.out.println();
        System.out.println("Redis Map");
        Jedis jedis = redisUtil.getJedis();
        Map<String, String> map = new HashMap<>();
        map.put("name", "zhangsan");
        map.put("age", "22");
        map.put("qq", "111732");
        jedis.hmset("user", map);

        List<String> rStrings = jedis.hmget("user", "name", "age", "qq");
        System.out.println(rStrings);

        jedis.hdel("user", "age");

        Iterator<String> iterator = jedis.hkeys("user").iterator();
        while (iterator.hasNext()) {
            String key = (String) iterator.next();
            System.out.println(key + ":" + jedis.hget("user", key));
        }

        jedis.close();
    }

    @Test
    public void testList() {
        System.out.println();
        System.out.println();
        System.out.println("Redis List");
        Jedis jedis = redisUtil.getJedis();
        jedis.del("staffId");
        // 从左边压入值
        jedis.lpush("staffId", "1001");
        jedis.lpush("staffId", "1002");
        jedis.lpush("staffId", "1003");
        // 获取list的长度
        long len = jedis.llen("staffId");
        System.out.println(len);
        // end -1, 也是取list所有元素
        System.out.println(jedis.lrange("staffId", 0, -1));
        System.out.println(jedis.lrange("staffId", 0, len));
        // 从右边压入值
        jedis.rpush("staffId", "2001");
        jedis.rpush("staffId", "2002");
        jedis.rpush("staffId", "2003");
        System.out.println(jedis.lrange("staffId", 0, jedis.llen("staffId")));
        jedis.close();
    }

    @Test
    public void testSet() {
        System.out.println();
        System.out.println();
        System.out.println("Redis Set");
        Jedis jedis = redisUtil.getJedis();
        jedis.sadd("staff", "zhangsan");
        jedis.sadd("staff", "lisi");
        jedis.sadd("staff", "lisi");
        jedis.sadd("staff", "wagnwu");
        // 获取集合中所有元素
        System.out.println(jedis.smembers("staff"));
        // 判断是否是集合中元素
        System.out.println(jedis.sismember("staff", "who"));
        // 随机取集合中元素
        System.out.println(jedis.srandmember("staff"));
        // 获取集合中元素个数
        System.out.println(jedis.scard("staff"));
        jedis.close();
    }

    @Test
    public void testSort() {
        System.out.println();
        System.out.println();
        System.out.println("Redis Sort");
        Jedis jedis = redisUtil.getJedis();
        jedis.del("account");
        jedis.lpush("account", "1");
        jedis.lpush("account", "8");
        jedis.lpush("account", "5");
        jedis.lpush("account", "11");
        System.out.println(jedis.lrange("account", 0, -1));
        System.out.println(jedis.sort("account"));
        System.out.println(jedis.lrange("account", 0, -1));
        jedis.close();

    }

    /*
     * 测试redis的发布订阅功能
     */
    @Test
    public void testSub() {
        System.out.println();
        System.out.println();
        System.out.println("Redis PubSub");
        Subscriber subscriber = new Subscriber();
        SubThread subThread = new SubThread(subscriber);
        Thread thread = new Thread(subThread);
        thread.start();
        //jedis.close();
    }

    @Test
    public void testPub() {
        Jedis jedis = redisUtil.getJedis();
        jedis.publish("mychannel", "hello");
        jedis.close();
    }

    @Test
    public void testKeyOperate() {
        System.out.println();
        System.out.println();
        System.out.println("Redis Key Operate");
        Jedis jedis = redisUtil.getJedis();
        // 判断key否存在
        System.out.println("判断key999键是否存在：" + jedis.exists("key999"));
        System.out.println("新增key001,value001键值对：" + jedis.set("key001", "value001"));
        System.out.println("判断key001是否存在：" + jedis.exists("key001"));
        // 输出系统中所有的key
        System.out.println("新增key002,value002键值对：" + jedis.set("key002", "value002"));
        System.out.println("系统中所有键如下：");
        Set<String> keys = jedis.keys("*");
        Iterator<String> it = keys.iterator();
        while (it.hasNext()) {
            String key = it.next();
            System.out.println(key);
        }
        // 删除某个key,若key不存在，则忽略该命令。
        System.out.println("系统中删除key002: " + jedis.del("key002"));
        System.out.println("判断key002是否存在：" + jedis.exists("key002"));
        // 设置 key001的过期时间
        System.out.println("设置 key001的过期时间为5秒:" + jedis.expire("key001", 5));
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
        }
        // 查看某个key的剩余生存时间,单位【秒】.永久生存或者不存在的都返回-1
        System.out.println("查看key001的剩余生存时间：" + jedis.ttl("key001"));
        // 移除某个key的生存时间
        System.out.println("移除key001的生存时间：" + jedis.persist("key001"));
        System.out.println("查看key001的剩余生存时间：" + jedis.ttl("key001"));
        // 查看key所储存的值的类型
        System.out.println("查看key所储存的值的类型：" + jedis.type("key001"));

        jedis.close();
    }

    @After
    public void destory() {
        Jedis jedis = redisUtil.getJedis();
        jedis.flushDB();// 清空redis库中所有数据
        jedis.close();
    }

}
