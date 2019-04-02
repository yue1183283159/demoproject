package com.common.netty.bio;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

public class TimeServerHandler implements Runnable {

	private Socket socket;

	public TimeServerHandler(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		BufferedReader in = null;
		PrintWriter out = null;
		try {
			in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
			out = new PrintWriter(this.socket.getOutputStream(), true);// true, 自动flush
			String currentTime = null;
			String body = null;
			while (true) {
				body = in.readLine();
				if (body == null) {
					break;
				}
				System.out.println("The time server received order:" + body);
				currentTime = "query time order".equalsIgnoreCase(body)
						? new Date(System.currentTimeMillis()).toString()
						: "bad order";

				out.print(currentTime);
			}

		} catch (Exception e) {
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
