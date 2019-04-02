package com.ssm.common.exception;

public class Result<T> {
	private Integer code;
	private String msg;
	private T data;

	public final Integer getCode() {
		return code;
	}

	public final void setCode(Integer code) {
		this.code = code;
	}

	public final String getMsg() {
		return msg;
	}

	public final void setMsg(String msg) {
		this.msg = msg;
	}

	public final T getData() {
		return data;
	}

	public final void setData(T data) {
		this.data = data;
	}

}
