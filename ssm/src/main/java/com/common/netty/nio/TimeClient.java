package com.common.netty.nio;

public class TimeClient {
	public static void main(String[] args) {

		try {
			int port = 8088;
			new Thread(new TimeClientHandle("127.0.0.1", port)).start();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
