package com.ssm.common.exception;

public class MyException extends RuntimeException {

	// 错误码
	private Integer code;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public MyException(String message) {
		super(message);
	}

	/**
	 * 构造器重载，主要是自己考虑某些异常自定义一些返回码
	 */
	public MyException(Integer code, String message) {
		super(message);
		this.code = code;
	}
}
