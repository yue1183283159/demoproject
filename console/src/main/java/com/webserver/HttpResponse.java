package com.webserver;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse
{
	private OutputStream out;
	private int status;
	private String contentType;
	private long contentLength;
	private Map<Integer, String> statusMap;

	public void setStatus(int status)
	{
		this.status = status;
	}

	public void setContentType(String contentType)
	{
		this.contentType = contentType;
	}

	public void setContentLength(long contentLength)
	{
		this.contentLength = contentLength;
	}

	public int getStatus()
	{
		return status;
	}

	public String getContentType()
	{
		return contentType;
	}

	public long getContentLength()
	{
		return contentLength;
	}

	public HttpResponse(OutputStream out)
	{
		this.out = out;
		statusMap = new HashMap<>();
		statusMap.put(HttpContext.STATUS_CODE_OK, HttpContext.STATUS_REASON_OK);
		statusMap.put(HttpContext.STATUS_CODE_NOT_FOUND, HttpContext.STATUS_REASON_NOT_FOUND);
		statusMap.put(HttpContext.STATUS_CODE_ERROR, HttpContext.STATUS_REASON_ERROR);
	}

	//是否发送状态行，响应头标识
	private boolean hasPrintHeader = false;

	/**
	 *该方法用于将指向客户端的输出流返回，在返回之前自动将状态行，响应头发送给客户端
	 *注意:该方法可以被多次调用，而状态行，响应头应该只被发送一次（提供一个是否发送过的标识）
	 */
	public OutputStream getOutputStream()
	{
		if(!hasPrintHeader) 
		{
			PrintStream pStream=new PrintStream(out);
			//pStream.println("HTTP/1.1 "+getStatus()+" "+statusMap.get(getStatus()));
			pStream.println(ServerContext.PROTOCOL+" "+status+" "+statusMap.get(status));
			pStream.println("Content-Type: "+getContentType());
			pStream.println("Content-Length: "+getContentLength());
			
			//发送空行，表示响应头已经发送完毕
			pStream.println("");
			hasPrintHeader=true;
		}
		return out;
	}

}
