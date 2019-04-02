package com.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class FilterDemo implements Filter{

	/**
	 * 在filter实例销毁之前执行。
	 * 在服务器关闭或者web应用被移出容器时，随着filter实例的销毁而执行。
	 * 
	 * */
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	/**
	 *对过滤器所拦截下来的请求进行处理
	 *
	 **/
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("filter demo excuted.");
		//过滤器放行请求和响应
		chain.doFilter(req, resp);
	}

	/**
	 * 当filter实例创建之后会被服务器立即调用进行初始化操作。
	 * 在web应用被加载之后，立即创建当前web应用下的所有过滤器实例
	 * 
	 * */
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}

}
