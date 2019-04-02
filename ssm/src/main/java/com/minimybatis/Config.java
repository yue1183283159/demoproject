package com.minimybatis;

//封装MyBatis的配置信息
public class Config {
	public static final Config DEFAULT = new Config();

	private Config() {
	}

	private String url = "jdbc:mysql://localhost/test_db";
	private String user = "root";
	private String pwd = "root";
	private String mapperPath="com/minmybatis/";

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getMapperPath() {
		return mapperPath;
	}

	public void setMapperPath(String mapperPath) {
		this.mapperPath = mapperPath;
	}
	

}
