package com.redis.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/test/")
public class TestController {

    @RequestMapping("index")
    @ResponseBody
    public String index(){
        return  "hello world";
    }
}

class  AppTest{

    public  void test(){}
}