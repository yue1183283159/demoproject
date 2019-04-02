package com.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Scanner;

public class NClient {
	// 定义检测SocketChannel的Selector对象
	private Selector selector = null;
	private static int PORT = 30000;
	// 定义处理编码和解码的字符集
	private Charset charset = Charset.forName("UTF-8");
	// 客户端SocketChannel
	private SocketChannel sc = null;

	public void init() throws IOException {
		selector = Selector.open();
		InetSocketAddress isa = new InetSocketAddress("127.0.0.1", PORT);
		// 调用open静态方法创建连接到指定主机 的SocketChannel
		sc = SocketChannel.open(isa);
		// 设置该sc以非阻塞方式工作
		sc.configureBlocking(false);
		sc.register(selector, SelectionKey.OP_READ);
		// 启动读取服务器端数据的线程
		new ClientThread().start();
		// 创建键盘输入流
		Scanner scan = new Scanner(System.in);
		while (scan.hasNextLine()) {
			// 读取键盘输入
			String line = scan.nextLine();
			// 将键盘输入的内容输出到SocketChannel中
			sc.write(charset.encode(line));
		}
	}

	// 定义读取服务器端数据的线程
	private class ClientThread extends Thread {
		public void run() {
			try {
				// 遍历每个有可用IO操作的Channel对应的SelectionKey
				while (selector.select() > 0) {
					for (SelectionKey sk : selector.selectedKeys()) {
						// 删除正在处理的SelectionKey
						selector.selectedKeys().remove(sk);
						// 如果sk对应的Channel有数据需要读取
						if (sk.isReadable()) {
							// 获取该SelectionKey对应的Channel,该Channel中有可读的数据
							SocketChannel sc = (SocketChannel) sk.channel();
							// 定义准备执行读取数据的ByteBuffer
							ByteBuffer buff = ByteBuffer.allocate(1024);
							String content = "";
							// 开始读数据

							while (sc.read(buff) > 0) {
								buff.flip();
								content += charset.decode(buff);
							}
							// 打印从该sk对应的Channel里读取的数据
							System.out.println("读取到的数据" + content);
							// 将sk对应的Channel设置成准备下一次读取
							sk.interestOps(SelectionKey.OP_READ);
						}
					}

				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws IOException {
		new NClient().init();
	}

}
