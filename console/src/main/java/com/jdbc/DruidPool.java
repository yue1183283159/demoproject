package com.jdbc;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.pool.DruidPooledConnection;

public class DruidPool
{
	private static DruidPool druidPool = null;
	private static DruidDataSource druidDataSource = null;

	// 私有化构造方法，外部不能实例化这个类。只能通过getInstance方法拿到实例
	private DruidPool()
	{

	}

	static
	{
		InputStream in = null;
		try
		{
			Properties properties = new Properties();
			Class clazz = MySqlHelper.class;
			in = clazz.getResourceAsStream("/com/jdbc/druid.properties");
			properties.load(in);
			druidDataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(properties);
			// druidDataSource.setDriverClassName("");
			// druidDataSource.setUrl("");
			// druidDataSource.setInitialSize(10);
			in.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			if (in != null)
			{
				try
				{
					in.close();
				} catch (IOException e)
				{
					e.printStackTrace();
				} finally
				{
					in = null;
				}

			}

		}

	}

	public static synchronized DruidPool getInstance()
	{
		if (druidPool == null)
		{
			druidPool = new DruidPool();
		}
		return druidPool;
	}

	public DruidDataSource getDruidDataSource()
	{
		return druidDataSource;
	}

	public DruidPooledConnection getConnection() throws SQLException
	{
		return druidDataSource.getConnection();
	}

	public void closeDataSource()
	{
		druidDataSource.close();
	}

}
