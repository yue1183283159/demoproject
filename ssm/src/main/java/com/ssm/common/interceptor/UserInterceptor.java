package com.ssm.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

//用来获取用户信息,拦截跳转请求
//HandlerInterceptor是SpringMVC中拦截器的接口
public class UserInterceptor implements HandlerInterceptor
{
	//@Autowired
	//private RedisUtils redisUtils=new RedisUtils(null);
	private static ObjectMapper objectMapper=new ObjectMapper();
	
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception
	{
		
		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception
	{
		
	}

	/***
	 * preHandle Controller方法执行之前
	 * postHandle Controller方法执行之后
	 * afterCompletion 最终执行的方法
	 * 由于业务逻辑,拦截器应该在用户用户点击购车按钮时生效.
	 * 这样请求还没有转向. 应该使用preHandle
	 * 
	 * 如何获取用户信息:???
	 * 	1.先通过request获取Cookie
	 *  2.获取ticket信息
	 *  3.查询缓存操作
	 *  4.判断数据有效性,如果含有用户信息,则直接转向目标页面
	 *  如果用户信息为null.则重定向到登陆页面
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
	{
		//String ticket=CookieUtils.getCookieValue(request,"jt_ticket");
		String ticket="";
		if(!StringUtils.isEmpty(ticket)) 
		{
			//String userJson=(String)redisUtils.get(ticket);
			String userJson="";
			//判断缓存数据是否为null
			//原因：浏览器一直保存着cookie，但是redis中有缓存策略，可能会删除过期的key
			if(!StringUtils.isEmpty(userJson))
			{
				//jackson将json数据转成对象
				//User user=objectMapper.readValue(userJson, User.class);
				//user对象如何存储才能在cart中被获取，通过ThreadLocal实现数据的传递
				//可以将数据放入类中的静态变量中，来达到不同类之间共享数据目的。但是如果直接使用静态的成员变量容易引发线程安全性问题。
				//UserThreadLocal.setUser(user);
				//这里使用ThreadLocal<T> 来存入变量值和获取变量值。多线程中保证变量数据访问不冲突的问题
				//放行跳转
				return true;
			}
		}
		
		//用户没有登录，进行页面跳转到SSO的登录页面
		response.sendRedirect("/user/login.html");
		return false; //false表示拦截，不会放行目标页面
	}

}
