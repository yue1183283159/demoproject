package com.spring.ioc;

public class OpenDataSource {
	private String driverClassName;
	private String url;
	private String username;
	private String password;
	private int coreSize;

	public int getCoreSize() {
		return coreSize;
	}

	public OpenDataSource(int coreSize) {
		this.coreSize = coreSize;
	}

	public String getDriverClassName() {
		return driverClassName;
	}

	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void init() {
		System.out.println("init opendatasource");
	}
	
	public void close() {
		System.out.println("close opendatasource");
	}
	
}
