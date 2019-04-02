package com.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class GroupChatClient
{
	static BlockingQueue<String> msgQueue = new LinkedBlockingQueue<>();

	public static void main(String[] args)
	{
		try
		{
			// connect to server
			// start send message thread
			// put the msg to queue
			// exit
			Socket socket = new Socket("127.0.0.1", 5222);
			SendThread sendThread = new SendThread(socket);
			sendThread.start();

			ReceiveThread receiveThread = new ReceiveThread(socket);
			receiveThread.start();

			Scanner scanner = new Scanner(System.in);
			boolean isRunning = true;
			while (isRunning)
			{
				String content = scanner.nextLine();
				msgQueue.put(content);
				if ("exit".equals(content))
				{
					Thread.sleep(100);
					isRunning = false;
					sendThread.isRunning = false;
					socket.close();
				}
			}
		} catch (Exception e)
		{
		}
	}
}

class ReceiveThread extends Thread
{
	boolean isRunning = true;
	Socket socket;
	BufferedReader bufferedReader;

	public ReceiveThread(Socket socket)
	{
		try
		{
			this.socket = socket;
			InputStream inputStream = socket.getInputStream();
			InputStreamReader reader = new InputStreamReader(inputStream);
			bufferedReader = new BufferedReader(reader);
		} catch (Exception e)
		{
			// TODO: handle exception
		}

	}

	@Override
	public void run()
	{
		while (isRunning)
		{
			try
			{
				String content = bufferedReader.readLine();
				if (content != null)
				{
					System.out.println("receive:" + content);
				}
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
}

class SendThread extends Thread
{

	boolean isRunning = true;
	Socket Socket;
	PrintWriter printWriter;

	public SendThread(Socket socket) throws Exception
	{
		this.Socket = socket;
		OutputStream outputStream = socket.getOutputStream();
		printWriter = new PrintWriter(outputStream, true);
	}

	@Override
	public void run()
	{
		while (isRunning)
		{
			try
			{
				String content = GroupChatClient.msgQueue.take();
				printWriter.println(content);
				System.out.println(content + " send success.");
			} catch (Exception e)
			{
				e.printStackTrace();
			}

		}
	}
}