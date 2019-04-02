package com.common.dubbo.rpc;

import java.net.InetSocketAddress;

/**
 * RPC(Remote Procedure Call)远程过程调用,一个计算机通信协议 允许本地代码像调用本地服务一样调用远程服务。 RPC与语言无关的。
 **/
public class TestRPC {
	public static void main(String[] args) {

		// 先启动服务提供中心，等待客户端连接调用
		new Thread(new Runnable() {

			@Override
			public void run() {
				IServer server = new ServiceCenter(8088);
				server.register(HelloService.class, HelloServiceImpl.class);
				server.start();
			}
		}).start();

		// 客户端通过RPC代理类，获取服务的代理对象，然后在本地调用服务的方法
		HelloService service = RPCClientProxy.getRemoteProxyObj(HelloService.class,
				new InetSocketAddress("127.0.0.1", 8088));

		for (int i = 0; i < 10; i++) {
			System.out.println(service.sayHi("rpc" + i));
		}

	}
}
