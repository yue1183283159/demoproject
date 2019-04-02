package com.ssm.common.vo;

/**
 *前后端分离，使用RESTFul风格请求服务端数据
 *统一响应结构
 *
 */
public class JsonResult
{
	private static final int SUCCESS = 1;
	private static final int ERROR = 0;
	private static final int NOPERMISSION = 2;

	/** 状态码 */
	private int state = SUCCESS;
	/** 状态信息 */
	private String message;
	/** 具体数据 */
	private Object data;

	public JsonResult()
	{
		message = "action success";
	}

	public JsonResult(Object data)
	{
		this.data = data;
	}

	public JsonResult fail(String message)
	{
		this.state = ERROR;
		this.message = message;
		return this;
	}

	public JsonResult nopermission()
	{
		this.state = NOPERMISSION;
		return this;
	}

	public JsonResult success()
	{
		return this;
	}

	public int getState()
	{
		return state;
	}

	public void setState(int state)
	{
		this.state = state;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public Object getData()
	{
		return data;
	}

	public void setData(Object data)
	{
		this.data = data;
	}
}
