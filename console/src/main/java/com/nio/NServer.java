package com.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;

import com.sun.org.apache.xml.internal.security.Init;

public class NServer {
	// 用于检测所有channel状态的selector
	private Selector selector = null;

	private static final int PORT = 3000;
	// 定义编码，解码的字符集对象
	private Charset charset = Charset.forName("UTF-8");

	public void init() throws IOException {
		selector = Selector.open();
		// 通过open方法来打开一个为绑定的ServerSocketChannel实例
		ServerSocketChannel server = ServerSocketChannel.open();
		InetSocketAddress isa = new InetSocketAddress("127.0.0.1", PORT);
		// 将该ServerSocketChannel绑定到指定的IP地址上
		server.bind(isa);
		// 设置serverSocket以非阻塞方式工作
		server.configureBlocking(false);
		// 将server注册到指定Selector对象
		server.register(selector, SelectionKey.OP_ACCEPT);
		while (selector.select() > 0) {
			// 依次处理selector上的每个已经选择的SelectionKey
			for (SelectionKey sk : selector.selectedKeys()) {
				// 从Selector上已选择key集中删除正在处理的SelectionKey
				selector.selectedKeys().remove(sk);
				// 如果sk对应的Channel包含客户端的连接请求
				if (sk.isAcceptable()) {
					// 调用accpet方法接收连接，产生服务器端的SocketChannel
					SocketChannel sc = server.accept();
					// 设置采用非阻塞模式
					sc.configureBlocking(false);
					// 将该SocketChannel也注册到Selector
					sc.register(selector, SelectionKey.OP_READ);
					// 将sk对应的Channel设置成准备接收其他请求
					sk.interestOps(SelectionKey.OP_ACCEPT);
				}
				// 如果sk对应的Channel有数据需要读取
				if (sk.isReadable()) {
					// 获取该SelectionKey对应的Channel,该Channel中有可读的数据
					SocketChannel sc = (SocketChannel) sk.channel();
					// 定义准备执行读取数据的ByteBuffer
					ByteBuffer buff = ByteBuffer.allocate(1024);
					String content = "";
					// 开始读数据
					try {
						while (sc.read(buff) > 0) {
							buff.flip();
							content += charset.decode(buff);

						}
						// 打印从该sk对应的Channel里读取的数据
						System.out.println("读取到的数据" + content);
						// 将sk对应的Channel设置成准备下一次读取
						sk.interestOps(SelectionKey.OP_READ);
						// 如果捕获到该skduiying de Channel出现异常，即表明改改Channel对应的Client出现了问题，所以从Selectir中取消sk的注册
					} catch (Exception e) {
						// 从Selector中删除指定的SelectionKey
						sk.cancel();
						if (sk.channel() != null) {
							sk.channel().close();
						}
					}
					// 如果content的长度大于0，即聊天信息不为空
					if (content.length() > 0) {
						// 遍历该selector里注册的所有SelectorKey
						for (SelectionKey key : selector.keys()) {
							// 获取该key对应的Channel
							Channel targetChannel = key.channel();
							// 如果该channel是注册的所有SelectionKey
							if (targetChannel instanceof SocketChannel) {
								// 将读到的内容写入该Channel中
								SocketChannel dest = (SocketChannel) targetChannel;
								dest.write(charset.encode(content));
							}
						}
					}

				}
			}
		}

	}

	public static void main(String[] args) {
		try {
			new NServer().init();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
