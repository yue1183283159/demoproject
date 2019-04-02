package com.common.netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class TimeClientHandle implements Runnable {

	private String host;
	private int port;
	private Selector selector;
	private SocketChannel socketChannel;
	private volatile boolean stop;

	public TimeClientHandle(String host, int port) {
		this.host = host == null ? "127.0.0.1" : host;
		this.port = port;

		try {
			selector = Selector.open();
			socketChannel = SocketChannel.open();
			socketChannel.configureBlocking(false);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	@Override
	public void run() {
		try {
			doConnect();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}

		while (!stop) {
			try {
				selector.select(1000);// 每隔1s唤醒selector
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

			} catch (Exception e) {
				e.printStackTrace();
				System.exit(1);
			}

		}

		if (selector != null) {
			try {
				selector.close();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				selector = null;
			}
		}

	}

	private void handleInput(SelectionKey key) throws Exception {
		if (key.isValid()) {
			SocketChannel sc = (SocketChannel) key.channel();
			if (key.isConnectable()) {
				if (sc.finishConnect()) {
					sc.register(selector, SelectionKey.OP_READ);
					doWrite(sc);
				} else {
					System.exit(1);// 连接失败，进程退出
				}

				if (key.isReadable()) {
					ByteBuffer readBuf = ByteBuffer.allocate(1024);
					int readBytes = sc.read(readBuf);
					if (readBytes > 0) {
						readBuf.flip();
						byte[] bytes = new byte[readBuf.remaining()];
						readBuf.get(bytes);
						String body = new String(bytes, "UTF-8");
						System.out.println("Now is:" + body);
						this.stop = true;
					} else if (readBytes < 0) {
						key.cancel();
						sc.close();
					} else {
						;// 读到0字节，忽略。这种情况会经常发生
					}
				}

			}
		}
	}

	private void doWrite(SocketChannel sc) throws IOException {
		byte[] req = "t".getBytes();
		ByteBuffer writeBuf = ByteBuffer.allocate(req.length);
		writeBuf.put(req);
		writeBuf.flip();
		sc.write(writeBuf);
		if (!writeBuf.hasRemaining()) {// 数据已全部发送，没有半包
			System.out.println("Send order to server succeed.");
		}

	}

	private void doConnect() throws IOException {
		if (socketChannel.connect(new InetSocketAddress(host, port))) {
			socketChannel.register(selector, SelectionKey.OP_READ);
			doWrite(socketChannel);
		} else {
			socketChannel.register(selector, SelectionKey.OP_CONNECT);
		}

	}

}
