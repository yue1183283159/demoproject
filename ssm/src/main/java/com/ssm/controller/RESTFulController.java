package com.ssm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController //使用此注解，可以省略@ResponseBody和@Controller。方法上也不需要使用@ResponseBody注解了
@RequestMapping("/Rest/")
//@ResponseBody
//@Controller
public class RESTFulController
{
	@RequestMapping(value = "index", method = RequestMethod.GET)
	@ResponseBody
	public String index()
	{
		return "Hello";
	}
	
	@RequestMapping(value = "data/{id}/{name}/{age}", method = RequestMethod.GET)
	@ResponseBody
	public String data(@PathVariable(value="id") Integer dId,@PathVariable("name") String name,@PathVariable("age")Integer age)
	{
		String message = "The Data ID:" + dId;
		return message;
	}

	//consumes = "application/json";规定请求header格式必须是json格式和类型必须是application/json
	@RequestMapping(value = "add", method = RequestMethod.POST,consumes = "application/json")
	@ResponseBody
	public String add(@RequestBody TestData newData)
	{
		return newData.getName() + "," + newData.getAge();
	}
	
	//@CookieValue()
	@RequestMapping("test")
	@ResponseBody
	public String test(@CookieValue("JSESSIONID")String sessionId,@CookieValue("id")Integer id) {
		return "";
	}

}

class TestData
{
	private Integer id;
	private String name;
	private Integer age;

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Integer getAge()
	{
		return age;
	}

	public void setAge(Integer age)
	{
		this.age = age;
	}

}
