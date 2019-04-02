package com.common.netty.aio;

import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.CountDownLatch;

import com.common.netty.bio.TimeServerHandler;

public class AsyncTimeServerHandler implements Runnable {

	private int port;

	CountDownLatch latch;

	AsynchronousServerSocketChannel asynchronousServerSocketChannel;

	public AsyncTimeServerHandler(int port) {
		this.port = port;
		try {
			asynchronousServerSocketChannel = asynchronousServerSocketChannel.open();
			asynchronousServerSocketChannel.bind(new InetSocketAddress(port));
			System.out.println("The time server is start in port:" + port);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void run() {
		latch = new CountDownLatch(1);
		doAccept();
		try {
			latch.wait();
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}

	}

	private void doAccept() {
		asynchronousServerSocketChannel.accept(this, new AcceptCompletionHandler());

	}

}
