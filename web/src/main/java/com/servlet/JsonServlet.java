package com.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet("/JsonServlet")
public class JsonServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public JsonServlet()
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		try
		{
			ObjectMapper mapper = new ObjectMapper();
			/**
			 * ObjectMapper是JSON操作的核心，Jackson的所有JSON操作都是在ObjectMapper中实现。
			 * ObjectMapper有多个JSON序列化的方法，可以把JSON字符串保存File、OutputStream等不同的介质中。
			 * writeValue(File arg0, Object arg1)把arg1转成json序列，并保存到arg0文件中。
			 * writeValue(OutputStream arg0, Object arg1)把arg1转成json序列，并保存到arg0输出流中。
			 * writeValueAsBytes(Object arg0)把arg0转成json序列，并把结果输出成字节数组。
			 * writeValueAsString(Object arg0)把arg0转成json序列，并把结果输出成字符串。
			 */
			
			String json="{\"id\":1,\"name\":\"test\",\"email\":\"1234@163.com\",\"birthDate\":844099200000,\"valid\":false}";
			Person p1= mapper.readValue(json, Person.class);
			System.out.println(p1.getId());
			
			Person p = new Person();
			p.setId(1);
			p.setName("test");
			p.setEmail("1234@163.com");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			p.setBirthDate(sdf.parse("1996-10-01"));
			String jsonStr = mapper.writeValueAsString(p);
		
			List<Person> pList = new ArrayList<Person>();
			pList.add(new Person(2, "sa", "123@163.com", sdf.parse("1996-10-01"), true));
			pList.add(new Person(32, "sa2", "123@163.com", sdf.parse("1996-10-01"), true));
			pList.add(new Person(4, "sa4", "123@163.com", sdf.parse("1996-10-01"), true));
			String arrayJsonStr = mapper.writeValueAsString(pList);

			int retCode=500;
			mapper.writeValueAsString(retCode);
			
			response.getWriter().append(jsonStr)
			.append("------***********--------")
			.append(arrayJsonStr).append("-----****----")
			.append(mapper.writeValueAsString(retCode));
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doGet(request, response);
	}

}