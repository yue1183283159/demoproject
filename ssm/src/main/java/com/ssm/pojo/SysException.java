package com.ssm.pojo;

import java.math.BigInteger;
import java.util.Date;

/**
 *系统异常日志 
 *记录系统出现的异常信息
 **/
public class SysException {

	private BigInteger id;
	private String exception;
	private String handleStatus;
	private Date addon;

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}

	public String getHandleStatus() {
		return handleStatus;
	}

	public void setHandleStatus(String handleStatus) {
		this.handleStatus = handleStatus;
	}

	public Date getAddon() {
		return addon;
	}

	public void setAddon(Date addon) {
		this.addon = addon;
	}

}
