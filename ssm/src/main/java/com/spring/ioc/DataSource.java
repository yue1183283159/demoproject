package com.spring.ioc;

public class DataSource
{
	private String driver;
	private String url;
	private String userName;
	private String password;
	private int coreSize;

	public DataSource()
	{
	}

	public DataSource(String driver, String url, String userName, String password)
	{
		super();
		this.driver = driver;
		this.url = url;
		this.userName = userName;
		this.password = password;
	}

	public void setCoreSize(int coreSize)
	{
		this.coreSize = coreSize;
	}

	public int getCoreSize()
	{
		return coreSize;
	}

	public String getDriver()
	{
		return driver;
	}

	public void setDriver(String driver)
	{
		this.driver = driver;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	@Override
	public String toString()
	{
		return "DataSource[drive=" + driver + ", url=" + url + ", userNmae=" + userName + ", password=" + password
				+ "]";
	}

}
