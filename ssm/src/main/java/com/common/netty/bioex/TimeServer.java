package com.common.netty.bioex;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.common.netty.bio.TimeServerHandler;

public class TimeServer {
	public static void main(String[] args) {
		int port = 8088;
		ServerSocket server = null;
		try {
			server = new ServerSocket(port);
			System.out.println("The time server is start in port:" + port);
			Socket socket = null;
			TimeServerHandlerExecutePool singleExecutor = new TimeServerHandlerExecutePool(50, 1000);// 创建I/O
			while (true) {
				socket = server.accept();
				singleExecutor.execute(new TimeServerHandler(socket));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (server != null) {

				try {
					server.close();
				} catch (IOException e) {
				} finally {
					server = null;
				}
			}
		}
	}
}
