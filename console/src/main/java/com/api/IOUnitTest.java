package com.api;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

public class IOUnitTest
{
	@Test
	public void testFile()
	{
		File file = new File("D:/");
		File[] subFiles = file.listFiles();
		for (File f : subFiles)
		{
			if (f.isDirectory())
			{
				// System.out.println("\\" + f.getName());
			}
			if (f.isFile())
			{
				// System.out.println(f.getName());
			}

		}
	}

	@Test
	public void testFileInputStream() throws Exception
	{
		// FileOutputStream fos = new FileOutputStream("order.txt");
		// byte[] orderId = new byte[1];
		// orderId[0] = 97;
		// for (int i = 0; i < 1000000; i++)
		// {
		// fos.write(orderId);
		// }
		// fos.close();
		// System.out.println("end");

		// long stime = System.currentTimeMillis();
		// FileInputStream fis = new FileInputStream("order.txt");
		// byte[] rb = new byte[1];
		// //每次读取一个字节，会频繁操作磁盘，这样频繁长时间操作，会损坏计算机。这种方式不可取。
		// int len = 0;
		// while ((len = fis.read(rb)) > 0)
		// {
		// //System.out.println(rb[0]);
		// }
		// long etime = System.currentTimeMillis();
		// System.out.println(etime - stime);

		// FileInputStream fis = new FileInputStream("order.txt");
		// BufferedInputStream bis = new BufferedInputStream(fis);
		// byte[] rb = new byte[1024 * 8];// 8K
		// long stime = System.currentTimeMillis();
		// int len = 0;
		// while ((len = bis.read(rb)) > 0)
		// {
		//
		// }
		// long etime = System.currentTimeMillis();
		// System.out.println(etime - stime);

		// copy file
		FileInputStream fis = new FileInputStream("order.txt");
		BufferedInputStream bis = new BufferedInputStream(fis);

		FileOutputStream fos = new FileOutputStream("order_copy.txt");
		BufferedOutputStream bos = new BufferedOutputStream(fos);

		byte[] buffer = new byte[1024 * 8];
		int len = 0;
		// 从Order.txt中取数据
		while ((len = bis.read(buffer)) > 0)
		{
			bos.write(buffer, 0, len);// 写数据
			bos.flush();// 缓冲区如果没有读满，不会写入文件，所以需要调用flush方法，将缓冲区数据都写入。
		}
		bis.close();
		// bos.close();//close会调用flush的方法，将最后读到的数据写入。
		System.out.println("end");

	}

	public void testRandomAccessFile() throws Exception
	{
		
		RandomAccessFile raf=new RandomAccessFile("raf.txt","rw");
		byte[] data1= {101,102};
		raf.write(data1);
		byte[] data2= {103,104,105};
		raf.write(data2);
		raf.close();
		
		raf=new RandomAccessFile("raf.txt","rw");
		System.out.println(raf.getFilePointer());
		byte[] rd1=new byte[2];
		raf.read(rd1);
		System.out.println(Arrays.toString(rd1));
		System.out.println(raf.getFilePointer());
		raf.close();
		
		
		String userName = "zhangsan";
		String password = "123456";
		RandomAccessFile raf1 = new RandomAccessFile("user.txt", "rw");
		long len = raf1.length();
		// before write, move the pointer。避免新填入数据覆盖之前的数据。
		raf1.seek(len);
		raf1.writeUTF(userName + "=" + password);
		raf1.close();

		raf1 = new RandomAccessFile("user.txt", "rw");
		long len1 = raf1.length();
		long pointer = raf1.getFilePointer();
		String findName = "wangwu";
		String findPass = "123456";
		boolean flag = false;
		while (pointer < len1)
		{
			String line = raf1.readLine();
			System.out.println(line);
			String[] account = line.split("=");
			if (account[0] == findName && account[1] == findPass)
			{
				flag = true;
				break;
			}

			// String name = raf.readUTF();
			// String pass = raf.readUTF();
			// System.out.println(name + ", " + pass);
			pointer = raf1.getFilePointer();
			if (pointer >= len1)
			{
				break;
			}
		}
	}

	// @Test
	public void testObjectOutputStream() throws Exception
	{
		// save many orders to file
		ArrayList<OrderEntity> orderList = new ArrayList<>();
		OrderEntity orderEntity1 = new OrderEntity();
		orderEntity1.setOrderId(1001);
		orderEntity1.setPayment(20000);
		orderList.add(orderEntity1);

		OrderEntity orderEntity2 = new OrderEntity();
		orderEntity2.setOrderId(1002);
		orderEntity2.setPayment(30000);
		orderList.add(orderEntity2);

		FileOutputStream fileOutputStream = new FileOutputStream("order.dat");
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
		objectOutputStream.writeObject(orderList);
		objectOutputStream.close();
	}

	// @Test
	public void testObjectInputStream() throws Exception
	{
		FileInputStream fileInputStream = new FileInputStream("order.dat");
		ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
		ArrayList<OrderEntity> orderEntities = (ArrayList<OrderEntity>) objectInputStream.readObject();
		System.out.println(orderEntities.size());
	}

	// @Test
	public void testPrintWriter() throws Exception
	{
		FileOutputStream fileOutputStream = new FileOutputStream("chat.txt");
		PrintWriter printWriter = new PrintWriter(fileOutputStream, true);
		// PrintWriter printWriter1=new PrintWriter(new
		// OutputStreamWriter(fileOutputStream),true);
		printWriter.println("First Message.");
		printWriter.println("第一个 Message.");
		printWriter.println("回复消息");
		printWriter.close();
		System.out.println("end");
	}

	// @Test
	public void testBufferedReader() throws Exception
	{
		FileInputStream fileInputStream = new FileInputStream("chat.txt");
		// 处理编码问题
		InputStreamReader reader = new InputStreamReader(fileInputStream);
		BufferedReader bufferedReader = new BufferedReader(reader);
		// 一行一行的读，遇到换行符时输出
		String line = null;
		while ((line = bufferedReader.readLine()) != null)
		{
			System.out.println(line);
		}
		bufferedReader.close();

	}

	@Test
	public void testCopyFile() throws Exception
	{
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream("chat.txt")));
		PrintWriter printWriter = new PrintWriter(new FileOutputStream("chat_bak.txt"), true);
		String line = null;
		while ((line = bufferedReader.readLine()) != null)
		{
			printWriter.println(line);
		}
		bufferedReader.close();
		printWriter.close();

	}
}
