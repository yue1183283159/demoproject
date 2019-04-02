package com.webserver;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest
{
	private String method;
	private String uri;
	private String protocol;

	private Map<String, String> parameters;

	public String getMethod()
	{
		return method;
	}

	public String getUri()
	{
		return uri;
	}

	public String getProtocol()
	{
		return protocol;
	}

	public String getParameter(String name)
	{
		if(parameters!=null) 
		{
			return parameters.get(name);
		}
		return null;
	}
	
	public HttpRequest(InputStream in)
	{
		try
		{
			BufferedReader bReader = new BufferedReader(new InputStreamReader(in));

			// 读取请求中的第一行信息，即请求行信息。（GET /index.html HTTP/1.1）
			String line = bReader.readLine();
			System.out.println("line:" + line);
			//在浏览器地址栏上敲一次回车，不一定只有一个请求发送到服务器。因为图片，js文件等，都会发送请求到服务器
			//当没有其他资源请求的时候，也会不止一个请求，因为还有一个ico图标的请求。
			if (line != null && line.length() > 0)
			{
				String[] data = line.split("\\s"); //正则表达式中\s代表空格
				this.method = data[0];
				this.uri = data[1];
				this.protocol = data[2];

				parameters = new HashMap<>();
				if (uri != null && uri.length() > 0 && uri.contains("?"))
				{
					String[] params = uri.split("\\?")[1].split("&");
					for (String param : params)
					{
						parameters.put(param.split("=")[0], param.split("=")[1]);
					}
				}
			}

			// TODO:如果关闭输入流，将不能输出任何数据？
			//不能关闭，因为只是读取的第一行数据，其实客户端发送来的不只一行数据。
			//还有一些请求头部信息需要被服务器端读取
			// in.close();
			// bReader.close();

		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
