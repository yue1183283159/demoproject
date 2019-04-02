package com.common.netty.bio;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

//发送查询服务器时间的“query time order”指令，读取服务器响应的结果打印出来，随后关闭连接，释放资源
public class TimeClient {
	public static void main(String[] args) {
		int port = 8088;
		if (args != null && args.length > 0) {
			try {
				port = Integer.valueOf(args[0]);
			} catch (Exception e) {
				// default value
			}
		}

		Socket socket = null;
		BufferedReader in = null;
		PrintWriter out = null;
		try {
			socket = new Socket("127.0.0.1", port);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);// true auto flush
			out.println("query time order");
			System.out.println("send query order to server successed.");
			String resp = in.readLine();
			System.out.println("The server time is:" + resp);
		} catch (Exception e) {

		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e2) {

				} finally {
					in = null;
				}
			}

			if (out != null) {
				try {
					out.close();
				} catch (Exception e2) {

				} finally {
					out = null;
				}
			}

			if (socket != null) {
				try {
					socket.close();
				} catch (Exception e2) {

				} finally {
					socket = null;
				}
			}
		}

	}
}
