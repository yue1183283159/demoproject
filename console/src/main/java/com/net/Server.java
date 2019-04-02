package com.net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Protocal
{

	public Server(Socket socket) throws Exception
	{
		super(socket);
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws Exception
	{
		ServerSocket ss = new ServerSocket(10086);
		Socket s = ss.accept();
		Server sv = new Server(s);
		// 从客户端接收数据
		String str = sv.receiveMessage();
		System.out.println(str);
		// 向客户端回复数据
		String str1 = "知道了！";
		sv.sendMessage(str1);
		// 关闭连接
		sv.closeConnection();
		ss.close();
	}
}

// public class Server
// {
// public static void main(String[] args) throws Exception
// {
// ServerSocket serverSocket = new ServerSocket(10086);
// System.out.println("Server started.");
// Socket socket = serverSocket.accept();
// DataInputStream dInputStream = new DataInputStream(socket.getInputStream());
// int type = dInputStream.readByte();
// if (type == 1)// 1-文本数据，2-图片数据，3- 视频数据
// {
// int len = dInputStream.readInt();
// byte[] b = new byte[len];
// dInputStream.read(b);
// System.out.println(new String(b, "utf-8"));
// }
//
// //response message to client
// DataOutputStream dataOutputStream=new
// DataOutputStream(socket.getOutputStream());
// String response="I know";
// byte[] bs=response.getBytes("utf-8");
// dataOutputStream.writeByte(1);
// dataOutputStream.writeInt(bs.length);
// dataOutputStream.write(bs);
// dataOutputStream.flush();
// serverSocket.close();
// }
// }
