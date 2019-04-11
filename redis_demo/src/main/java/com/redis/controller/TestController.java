package com.redis.controller;

import com.redis.component.MessageChannel;
import com.redis.component.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/test/")
public class TestController {

    @Autowired
    private RedisService redisService;

    @RequestMapping("index")
    @ResponseBody
    public String index() {

        redisService.publishMessage(MessageChannel.CHAT_CHANNEL, "hi");
        return "hello world";
    }
}
