package com.net;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

/*
 * 接受客户端信息
 * */
public class ClientHandler implements Runnable
{
	private boolean isRunning = true;
	private Socket socket;
	private int port;

	public boolean isRunning()
	{
		return isRunning;
	}

	public void setRunning(boolean isRunning)
	{
		this.isRunning = isRunning;
	}

	public Socket getSocket()
	{
		return socket;
	}

	public void setSocket(Socket socket)
	{
		this.socket = socket;
	}

	public ClientHandler(Socket socket)
	{
		System.out.println("Client " + socket.getPort() + " connected.");
		this.socket = socket;
		this.port = socket.getPort();
	}

	@Override
	public void run()
	{
		BufferedReader bufferedReader = null;
		try
		{
			InputStream inputStream = socket.getInputStream();
			// InputStreamReader实现了字符的编码
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
			bufferedReader = new BufferedReader(inputStreamReader);
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		while (isRunning)
		{
			try
			{
				String content = bufferedReader.readLine();
				if (content != null && content.length() > 0)
				{
					// System.out.println(port + ": " + content);
					if ("exit".equals(content))
					{
						GroupChatServer.msgQueue.put(port + ": exited.");
						isRunning = false;
						GroupChatServer.clientHandlerList.remove(this);
						socket.close();
					} else
					{
						GroupChatServer.msgQueue.put(port + ": " + content);
					}

				}
			} catch (Exception e)
			{
				try
				{
					GroupChatServer.msgQueue.put(port + ": exited.");
					isRunning = false;
					GroupChatServer.clientHandlerList.remove(this);
					socket.close();
					System.out.println("Client " + port + " disconnected.");
				} catch (Exception e1)
				{
					e1.printStackTrace();
				}

			}
		}
	}
}
