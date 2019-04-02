package com.spring.ioc;

public class JdbcTemplate
{
	private DataSource dataSource;

	public JdbcTemplate()
	{
		System.out.println("JdbcTemplate()");
	}

	public JdbcTemplate(DataSource dataSource)
	{
		this.dataSource = dataSource;
	}

	public void setDataSource(DataSource dataSource)
	{
		this.dataSource = dataSource;
	}

	public DataSource getDataSource()
	{
		return dataSource;
	}
}
