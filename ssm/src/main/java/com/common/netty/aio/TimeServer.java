package com.common.netty.aio;

public class TimeServer {
	public static void main(String[] args) {
		int port = 8088;
		if (args != null && args.length > 0) {
			try {
				port = Integer.valueOf(args[0]);
			} catch (NumberFormatException e) {
				// 采用默认值
			}
		}

		AsyncTimeServerHandler timeServer = new AsyncTimeServerHandler(port);

		new Thread(timeServer, "AIO-AsyncTimeServerHandler-001").start();

	}
}
