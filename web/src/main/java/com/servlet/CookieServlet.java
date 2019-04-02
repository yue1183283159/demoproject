package com.servlet;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author:Administrator
 * @date:2018年8月19日
 */
public class CookieServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String lastVisitTime=null;
		//获取请求中所有cookie对象数组，然后遍历获取特定的cookie对象。
		Cookie[] cookies = request.getCookies();
		
		//第一次访问的时候，没有任何cookie，cookies这个数组为null，所以需要非空的判断。
		if(cookies!=null)
		{
			for (Cookie c : cookies) {
				System.out.println(c.getName());
				
				//将hardcode字符串放在比较的左边，这样可以避免空指针异常。如果c.getName()返回为null,如果
				//调用equals方法，就会抛出空指针异常。但是换个顺序比较，就不会有空指针异常。
				if("time".equals(c.getName())) {
				//if (c.getName().equals("time")) {
					lastVisitTime=c.getValue();
					System.out.println("Last Visit Time: " +lastVisitTime);
				}

				if (c.getName().equals("name")) {
					System.out.println(URLDecoder.decode(c.getValue(), "utf-8"));
				}
			}	
		}

		Cookie cookie = new Cookie("time", new Date().toLocaleString());
		// cookie.getName();
		// cookie.getValue();
		// cookie.setValue("");
		// 在服务器设置的时间是服务器端的时间，
		//cookie.setMaxAge(60 * 60 * 24 * 7); // 生存期一周
		//cookie.setMaxAge(0);
		//setMaxAge()设置cookie的有效时间。单位是秒
		//正整数：表示超过了正整数的数值时间，cookie就会丢失
		//负整数：浏览器关闭了，cookie就会丢失
		//0:表示删除同名的cookie
		//cookie的api中没有直接删除cookie的方法。要做删除，需要向浏览器发送一个同名，同path的cookie，设置cookie的最大存活时间为0
		//当浏览器收到cookie的时候，由于是根据cookie名和path来区分一个cookie的，后来的cookie就会覆盖之前cookie，由于后来的
		//cookie的生存期为0，浏览器收到之后也会立刻删除这cookie。而且这个cookie不需要有值，这样还可以节省带宽。
		cookie.setPath(request.getContextPath());
		//一般设置当前web应用的虚拟路径，好处是当浏览器访问当前web应用下的任何一个资源时，都可以把cookie带回服务器
		response.addCookie(cookie);

		//对于中文，cookie不能直接存储中文，如果要存中文，需要对中文内容进行URL编码。
		//获取cookie中的中文内容，需要对其进行URL解码。
		Cookie cookie2 = new Cookie("name", URLEncoder.encode("张三", "utf-8"));
		response.addCookie(cookie2);

		if(lastVisitTime!=null)
		{
			response.getWriter().write("Last Visited Datetime:"+lastVisitTime);;	
		}else 
		{
			response.getWriter().write("First Visit This Site.");;
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
