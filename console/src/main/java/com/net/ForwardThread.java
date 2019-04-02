package com.net;

import java.io.OutputStream;
import java.io.PrintWriter;

/*
 * send message to clients
 * 
 * */
public class ForwardThread extends Thread
{
	boolean isRunning = true;

	@Override
	public void run()
	{
		while (isRunning)
		{
			try
			{
				// get the message from msgQueue
				// get the client from client list
				// send message to client
				String msg = GroupChatServer.msgQueue.take();
				for (ClientHandler handler : GroupChatServer.clientHandlerList)
				{
					OutputStream outputStream = handler.getSocket().getOutputStream();
					PrintWriter pWriter = new PrintWriter(outputStream, true);
					pWriter.println(msg);
				}

			} catch (Exception e)
			{

			}
		}
	}
}
