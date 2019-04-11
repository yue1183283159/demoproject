package com.redis.controller;

import com.redis.component.MessageChannel;
import com.redis.component.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/test/")
public class TestController {

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping("index")
    @ResponseBody
    public String index() {
        //Redis pub/sub message mode
        redisTemplate.convertAndSend(MessageChannel.CHAT_MESSAGE, "hi");
        redisTemplate.convertAndSend(MessageChannel.CHAT_USER, "user online");
        redisTemplate.convertAndSend(MessageChannel.SYS_ONLINE,"system online");
        redisTemplate.convertAndSend(MessageChannel.SYS_MESSAGE,"administrator delete one user.");

        //Redis baisc use
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

        return "hello world";
    }
}
