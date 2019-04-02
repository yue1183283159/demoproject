package com.net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

/*
 * 将server , client 公共的代码提取出来
 * */
public class Protocal
{
	private Socket socket;
	private DataInputStream dInputStream;
	private DataOutputStream dOutputStream;

	public Protocal(Socket socket) throws Exception
	{
		super();
		this.socket = socket;
		dInputStream = new DataInputStream(socket.getInputStream());
		dOutputStream = new DataOutputStream(socket.getOutputStream());
	}

	public void sendMessage(String message) throws Exception
	{
		dOutputStream.writeByte(1);
		byte[] bs = message.getBytes("utf-8");
		dOutputStream.writeInt(bs.length);
		dOutputStream.write(bs);
		dOutputStream.flush();
	}

	public String receiveMessage() throws Exception
	{
		int type = dInputStream.readByte();
		if (type == 1)
		{
			int len = dInputStream.readInt();
			byte[] bs = new byte[len];
			dInputStream.read(bs);
			return new String(bs, "utf-8");

		}
		return "";
	}

	public void closeConnection() throws Exception
	{
		socket.close();
	}
}
