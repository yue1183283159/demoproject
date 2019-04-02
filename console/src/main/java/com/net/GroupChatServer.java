package com.net;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class GroupChatServer
{
	static ExecutorService threadPool;
	static boolean isRunning = true;
	// ArrayList-thread unsafety
	// store the client
	static CopyOnWriteArrayList<ClientHandler> clientHandlerList = new CopyOnWriteArrayList<>();
	// store the message
	static BlockingQueue<String> msgQueue = new LinkedBlockingQueue<>();

	public static void main(String[] args)
	{
		try
		{
			// 创建线程池
			threadPool = Executors.newFixedThreadPool(30);
			ForwardThread forwardThread = new ForwardThread();
			threadPool.execute(forwardThread);

			ServerSocket serverSocket = new ServerSocket(5222);
			System.out.println("Server Started, listening 5222 port");
			while (isRunning)
			{
				Socket clientSocket = serverSocket.accept();
				// System.out.println(clientSocket.getInetAddress().getHostAddress());
				// System.out.println(clientSocket.getPort());// 打印客户端的源端口
				// telnet 127.0.0.1 5222使用Telnet当客户端来测试

				ClientHandler clientHandler = new ClientHandler(clientSocket);
				clientHandlerList.add(clientHandler);

				threadPool.execute(clientHandler);
			}

		} catch (Exception e)
		{

		}
	}
}
