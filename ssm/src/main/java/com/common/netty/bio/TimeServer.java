package com.common.netty.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TimeServer {
	public static void main(String[] args) {
		int port = 8088;
		if (args != null && args.length > 0) {
			try {
				port = Integer.valueOf(args[0]);
			} catch (Exception e) {
				// use default port
			}
		}

		ServerSocket server = null;
		try {
			server = new ServerSocket(port);
			System.out.println("The time server is start in port:" + port);
			Socket socket = null;
			while (true) {
				socket = server.accept();
				new Thread(new TimeServerHandler(socket)).start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (server != null) {
				System.out.println("The time server close.");
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
