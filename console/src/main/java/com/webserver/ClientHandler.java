package com.webserver;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;

import java.net.Socket;

public class ClientHandler implements Runnable
{

	private Socket socket = null;

	public ClientHandler(Socket socket)
	{
		this.socket = socket;
	}

	@Override
	public void run() {
		try
		{
			InputStream in = socket.getInputStream();
			OutputStream out = socket.getOutputStream();
			HttpRequest request=new HttpRequest(in);
			if(request.getUri()!=null) 
			{
				File file=new File("WebContent"+request.getUri());
				HttpResponse response=new HttpResponse(out);
				if(file.exists()) 
				{
					response.setStatus(HttpContext.STATUS_CODE_OK);
				}else 
				{
					file=new File(ServerContext.WEB_ROOT+"/"+ServerContext.NotFoundPage);
					response.setStatus(HttpContext.STATUS_CODE_NOT_FOUND);					
				}
				
				response.setContentType(getContentType(file));
				response.setContentLength(file.length());
				
				reponseFile(file, response);
			}
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally 
		{
			if(socket!=null) 
			{
				try
				{
					socket.close();
				}catch (Exception e) {
					e.printStackTrace();
				}  
				finally {
					socket=null;
				}
			}
		}
		
	}
	
	private void reponseFile(File file,HttpResponse response) throws Exception 
	{
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
		byte[] buff = new byte[(int) file.length()];
		bis.read(buff);
		
		PrintStream pStream=new PrintStream(response.getOutputStream());		
		pStream.write(buff);
	}
	
	private String getContentType(File file) 
	{
		String fileName=file.getName();
		String ext=fileName.substring(fileName.lastIndexOf(".")+1);
		return ServerContext.Types.get(ext);
	}
	
	/*
	 * 简化版，代码不能重用，混乱。需要重构，将request和response封装起来
	@Override
	public void run()
	{
		try
		{
			// 获取客户端的输入流，用于获取客户端发送的请求信息
			InputStream in = socket.getInputStream();
			// 获取指向客户端的输出流，用于向客户端发送响应数据
			OutputStream out = socket.getOutputStream();
			// PrintStream用于包装其他输入流，能够为输出流提供便捷的功能，便于打印各种值的输出形式
			PrintStream ps = new PrintStream(out);

			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line = br.readLine();
			System.out.println("Client: "+line);
			
			if (line != null && line.length() > 0)
			{
				String uri = line.split("\\s")[1];// 浏览器请求资源的路径
				long len = 0;
				byte[] buff = null;
				if (uri != null && uri.length() > 1)
				{
					System.out.println("uri:" + uri);
					// 创建file对象表示浏览器所请求的资源文件
					File file = new File("WebContent" + uri);
					len = file.length();

					BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
					// 将文件一次性读取到字节数组中保存
					buff = new byte[(int) file.length()];
					bis.read(buff);
				} else
				{
					String data = "Hello";
					len = data.length();
					buff = data.getBytes();
				}

				ps.println("HTTP/1/1 200 OK");
				ps.println("Content-Type:text/html");
				ps.println("Content-Length:" + len);
				ps.println("");

				ps.write(buff);
				ps.flush();
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			if (socket != null)
			{
				try
				{
					socket.close();
				} catch (IOException e)
				{
					socket = null;
				}
			}

		}
	}
	*/

}
