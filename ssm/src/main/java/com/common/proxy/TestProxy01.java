package com.common.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.logging.Logger;

interface HelloService {
    void sayHello(String msg);

    void sayWelcome();
}

class HelloServiceImpl implements HelloService {

    @Override
    public void sayHello(String msg) {
        System.out.println(msg);
    }

    @Override
    public void sayWelcome() {
        System.out.println("Welcome");

    }

}

// 封装代理对象扩展功能的一个对象
class TargetHandler implements InvocationHandler {

    private Logger logger = Logger.getLogger(getClass().getName());
    private Object target = null;

    public TargetHandler(Object target) {
        this.target = target;
    }

    /*
     * @param proxy 指向代理对象
     *
     * @param method 指向目标对象的方法对象
     *
     * @param args 执行目标对象方法时传入的参数
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        logger.info("Method start.....");
        // 执行目标方法
        Object result = method.invoke(target, args);
        logger.info("Method end.....");
        return result;
    }
}

public class TestProxy01 {
    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        //helloService.sayHello("Hello AOP");

        // 创建代理对象
        HelloService proxyInstance = (HelloService) Proxy.newProxyInstance(helloService.getClass().getClassLoader(), // 目标对象
                helloService.getClass().getInterfaces(), new TargetHandler(helloService));
        proxyInstance.sayHello("Hello Proxy.");
        proxyInstance.sayWelcome();
    }

}
