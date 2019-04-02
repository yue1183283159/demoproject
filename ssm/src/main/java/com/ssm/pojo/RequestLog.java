package com.ssm.pojo;

import java.util.Date;
/**
 *请求信息
 *记录请求信息，请求耗时 
 **/
public class RequestLog {
	private Integer id;
	private String url;
	private String data;
	private Integer elapsedTime;
	private Date addon;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Integer getElapsedTime() {
		return elapsedTime;
	}

	public void setElapsedTime(Integer elapsedTime) {
		this.elapsedTime = elapsedTime;
	}

	public Date getAddon() {
		return addon;
	}

	public void setAddon(Date addon) {
		this.addon = addon;
	}

	@Override
	public String toString() {
		return "RequestLog [url=" + url + ", data=" + data + ", elapsedTime=" + elapsedTime + "]";
	}
}
