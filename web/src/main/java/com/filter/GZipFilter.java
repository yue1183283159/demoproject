package com.filter;

import java.io.ByteArrayOutputStream;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.zip.GZIPOutputStream;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class GZipFilter implements Filter
{
	public GZipFilter()
	{

	}

	public void destroy()
	{

	}

	public void init(FilterConfig fConfig) throws ServletException
	{

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException
	{
		MyHttpResponse myHttpResponse = new MyHttpResponse((HttpServletResponse) response);
		// 创建一个response的装饰者对象
		chain.doFilter(request, myHttpResponse);

		/*
		 * 过滤响应 从缓存容器对象得到压缩前的内容 注意：response对象中没有方法获取实体内容
		 */
		char[] content = myHttpResponse.getCharArray();
		// gzip压缩
		ByteArrayOutputStream buf = new ByteArrayOutputStream();
		GZIPOutputStream gzip = new GZIPOutputStream(buf);
		gzip.write(new String(content).getBytes());
		gzip.finish();
		byte[] result = buf.toByteArray();
		//告诉浏览器发送内容的压缩格式
		myHttpResponse.setHeader("content-encoding", "gzip");
		response.getOutputStream().write(result);
	}

}

class MyHttpResponse extends HttpServletResponseWrapper
{

	private HttpServletResponse response;
	// 定义一个缓存容器对象
	private CharArrayWriter charArrayWriter = new CharArrayWriter();

	/*
	 * 提供一个获取charArray内容的方法（包含网页内容）
	 */
	public char[] getCharArray()
	{
		return charArrayWriter.toCharArray();
	}

	public MyHttpResponse(HttpServletResponse response)
	{
		super(response);
		this.response = response;
	}

	/**
	 * 重写getWriter()方法，让其返回一个带缓存功能的PrintWriter
	 */
	public PrintWriter getWriter()
	{
		/**
		 * 现在已经创建了一个带CharArrayWriter缓存容器的PrintWriter了，
		 * 如果我们调用带缓存PrintWriter对象的write()方法，那么内容会直接写入到CharrArrayWriter缓存容器中。
		 */
		return new PrintWriter(charArrayWriter);
	}

}

