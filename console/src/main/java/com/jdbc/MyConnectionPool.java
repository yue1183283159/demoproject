package com.jdbc;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import javax.sql.DataSource;

/**
 * 这个类用来实现自定义连接池 3.3.2.2.准备连接池容器，用来存放连接 3.3.2.3.在静态代码块中，初始化一批连接
 * 3.3.2.4.提供getConnection方法，用来获取数据库连接 3.3.2.5.提供returnConn方法，用来把连接还回池中
 * 3.3.2.6.测试
 */

// 1、sun公司要求，如果想自定义连接池，
// 必须实现javax.sql.DataSource
public class MyConnectionPool implements DataSource
{
	private static List<Connection> conList = new LinkedList<>();

	static
	{
		for (int i = 0; i < 5; i++)
		{
			Connection connection = MySqlHelper.getConnection();
			conList.add(connection);
		}
	}

	@Override
	public Connection getConnection() throws SQLException
	{
		//get方法太单纯，只是读一下，所以remove
		Connection connection = conList.remove(0);
		System.out.println("MyPool has " + conList.size() + " connection.");
		//从包装类获取
		Connection connDecorator = new ConnectionDecorator(connection, this);
		return connDecorator;
	}

	public void returnConn(Connection connection)
	{
		try
		{
			if (connection != null && !connection.isClosed())
			{
				conList.add(connection);
				System.out.println("MyPool has " + conList.size() + " connection.");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public PrintWriter getLogWriter() throws SQLException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getLoginTimeout() throws SQLException
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLogWriter(PrintWriter arg0) throws SQLException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setLoginTimeout(int arg0) throws SQLException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isWrapperFor(Class<?> arg0) throws SQLException
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public <T> T unwrap(Class<T> arg0) throws SQLException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Connection getConnection(String username, String password) throws SQLException
	{
		// TODO Auto-generated method stub
		return null;
	}

}
