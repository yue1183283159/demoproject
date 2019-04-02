package com.net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client extends Protocal
{
	public Client(Socket socket) throws Exception
	{
		super(socket);

	}

	public static void main(String[] args) throws Exception
	{
		Socket s = new Socket("localhost", 10086);
		// 向服务器发消息
		Client c = new Client(s);
		String str = "我只想在这里写一句话。";
		c.sendMessage(str);
		// 从服务器接收数据
		c.receiveMessage();
		// 关闭连接
		c.closeConnection();
	}
}

// public class Client
// {
// public static void main(String[] args) throws Exception, IOException
// {
// Socket socket = new Socket("localhost", 10086);
// String str = "Hello";
// byte[] bs = str.getBytes("utf-8");
// DataOutputStream dOutputStream = new
// DataOutputStream(socket.getOutputStream());
// dOutputStream.write(1);
// dOutputStream.writeInt(bs.length);
// dOutputStream.write(bs);
// dOutputStream.flush();
//
// //recive message from server
// DataInputStream dataInputStream = new
// DataInputStream(socket.getInputStream());
// int type = dataInputStream.readByte();
// if (type == 1)
// {
// int len = dataInputStream.readInt();
// byte[] bs2 = new byte[len];
// dataInputStream.read(bs2);
// System.out.println(new String(bs2, "utf-8"));
// }
// socket.close();
// }
//
// }
