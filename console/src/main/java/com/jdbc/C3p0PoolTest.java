package com.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class C3p0PoolTest
{
	public static void main(String[] args)
	{
		Connection conn = null;
		PreparedStatement pStatement = null;
		ResultSet rSet = null;
		try
		{
			DataSource dataSource = C3P0Pool.getDataSource();
			conn = dataSource.getConnection();
			pStatement = conn.prepareStatement("select now() as time");
			rSet = pStatement.executeQuery();
			while (rSet.next())
			{
				System.out.println(rSet.getString(1));

			}
			rSet.close();
			pStatement.close();
			conn.close();
			//从连接池获取的connection对象，是经过改造的，
			//只要调用close方法，就会将此连接对象还回连接池中
			//如果连接对象是自己跟数据库要的，关闭之后是将连接还给数据库了。
		} catch (Exception e)
		{
			// TODO: handle exception
		} finally
		{
			// 如果是从连接池获取的连接，用完之后一定要还回去

		}
	}
}

class C3P0Pool
{
	//private static String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	//private static String DB_URL = "jdbc:mysql://localhost:3306/mysql";
	// private static String
	// URL="jdbc:mysql://localhost:3306/db_test?user=root&password=root";
	//private static String USER = "root";
	//private static String PASS = "root";

	static
	{
		// 读取properties文件设置数据库连接信息

	}

	// 私有化构造方法，不允许外部构造此类的实例
	private C3P0Pool()
	{

	}

	public static DataSource getDataSource()
	{
		try
		{
			ComboPooledDataSource cpds = new ComboPooledDataSource();
			//从配置文件中读取连接信息，名字必须是c3p0-config.xml，放在class目录下
			
			//cpds.setDriverClass(JDBC_DRIVER);
			//cpds.setJdbcUrl(DB_URL);
			//cpds.setUser(USER);
			//cpds.setPassword(PASS);
			return cpds;
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

}
