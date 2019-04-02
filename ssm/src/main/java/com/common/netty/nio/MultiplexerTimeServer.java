package com.common.netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

/**
 * multiplexer 多工器，多路器
 **/
public class MultiplexerTimeServer implements Runnable {

	private Selector selector;

	private ServerSocketChannel serverChannel;

	private volatile boolean stop;

	/**
	 * 初始化多路复用器，绑定监听端口
	 **/
	public MultiplexerTimeServer(int port) {
		try {
			selector = Selector.open();// 创建Selector线程，创建多路服务器
			serverChannel = ServerSocketChannel.open();// 打开ServerSocketChannel，用于监听客户端连接，是所有客户端连接的父管道
			serverChannel.configureBlocking(false);// 设置连接为非阻塞模式
			serverChannel.socket().bind(new InetSocketAddress(port), 1024);// 绑定监听端口
			serverChannel.register(selector, SelectionKey.OP_ACCEPT);// 将ServerSocketChannel注册到selector线程的多路复用器上，监听accept事件
			System.out.println("The time server is start in port:" + port);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public void stop() {
		this.stop = true;
	}

	@Override
	public void run() {
		try {
			while (!stop) {
				selector.select(1000);// 休眠一秒，无论是否有读写时间发生，selector每隔1s被唤醒一次
				Set<SelectionKey> selectionKeys = selector.selectedKeys();
				Iterator<SelectionKey> it = selectionKeys.iterator();
				SelectionKey key = null;
				while (it.hasNext()) {
					key = it.next();
					it.remove();
					try {
						handleInput(key);
					} catch (Exception e) {
						if (key != null) {
							key.cancel();
							if (key.channel() != null) {
								key.channel().close();
							}
						}
					}

				}

			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		// 多路复用器关闭后，所有注册在上面的channel和Pipe等资源都会自动去注册并关闭
		if (selector != null) {
			try {
				selector.close();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				selector = null;
			}
		}

	}

	private void handleInput(SelectionKey key) throws Exception {
		if (key.isValid()) {
			// 处理新接入的请求消息
			if (key.isAcceptable()) {
				// accept the new connection
				ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
				SocketChannel sc = ssc.accept();
				sc.configureBlocking(false);
				// Add new connection to selector
				sc.register(selector, SelectionKey.OP_READ);
			}

			if (key.isReadable()) {
				// read the data
				SocketChannel sc = (SocketChannel) key.channel();
				ByteBuffer readBuf = ByteBuffer.allocate(1024);// ByteBuffer capacity.缓冲区1M
				int readBytes = sc.read(readBuf);// channel中读取数据到缓冲区
				if (readBytes > 0) {// 读取到数据
					readBuf.flip();//将缓冲区当前的limit设置为position，position设置为0，用于后续对缓冲区的读取操作
					//flip将多余的空位置去掉。因为设置的bytebuffer容量为1M,如果没有读到1M的数据，则字节数组有空位置
					byte[] bytes = new byte[readBuf.remaining()];
					readBuf.get(bytes);// 将buffer中的数据读到byte数组中
					// 编码byte数据
					String body = new String(bytes, "UTF-8");
					System.out.println("The time server received order:" + body);
					String currentTime = "t".equalsIgnoreCase(body) ? new Date().toString() : "bad order";
					doWrite(sc, currentTime);

				} else if (readBytes < 0) {
					// 关闭客户端连接
					key.cancel();
					sc.close();
				} else {
					// 读到0字节，忽略
				}

			}

		}

	}

	private void doWrite(SocketChannel sc, String response) throws Exception {
		if (response != null && response.trim().length() > 0) {
			byte[] bytes = response.getBytes();//将相应信息转成字节数组
			ByteBuffer writeBuf = ByteBuffer.allocate(bytes.length);
			writeBuf.put(bytes);//将字节数组放入写入缓存区
			writeBuf.flip();
			sc.write(writeBuf);//SocketChannel从缓冲区中把数据输出
		}

	}

}
