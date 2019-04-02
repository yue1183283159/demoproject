package com.jdbc;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;

public class MySqlHelper
{
	
	private static String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private static String DB_URL = "jdbc:mysql://localhost:3306/db_test";
	//private static String URL="jdbc:mysql://localhost:3306/db_test?user=root&password=root";
	private static String USER = "root";
	private static String PASS = "root";

	// 私有化构造函数，避免外界创建类的实例
	private MySqlHelper()
	{
	};

	static
	{
		try
		{
			Properties properties = new Properties();
			Class clazz = MySqlHelper.class;
			InputStream in = clazz.getResourceAsStream("/com/jdbc/jdbc.properties");
			properties.load(in);

			DB_URL = properties.getProperty("url");
			USER = properties.getProperty("username");
			PASS = properties.getProperty("password");
			JDBC_DRIVER = properties.getProperty("driver");
			in.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	public static Connection getConnection()
	{
		try
		{
			Class.forName(JDBC_DRIVER);
			Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
			return conn;
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static void clossAll(Connection conn, Statement stmt, ResultSet rs)
	{
		if (rs != null)
		{
			try
			{
				rs.close();
			} catch (SQLException e)
			{
				e.printStackTrace();
			} finally
			{
				rs = null;
			}
		}

		if (stmt != null)
		{
			try
			{
				stmt.close();
			} catch (SQLException e)
			{
				e.printStackTrace();
			} finally
			{
				stmt = null;
			}
		}

		if (conn != null)
		{
			try
			{
				conn.close();
			} catch (SQLException e)
			{
				e.printStackTrace();
			} finally
			{
				conn = null;
			}
		}

	}

	public static int executeAddUpdate(String sql)
	{
		try
		{
			Class.forName(JDBC_DRIVER);
			try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
					Statement stmt = conn.createStatement();)
			{
				int n = stmt.executeUpdate(sql);
				return n;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return 0;
	}

	public static CachedRowSet executeQuery(String sql)
	{
		try
		{
			Class.forName(JDBC_DRIVER);
			try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
					Statement stmt = conn.createStatement();
					ResultSet rs = stmt.executeQuery(sql);)
			{
				RowSetFactory factory = RowSetProvider.newFactory();
				CachedRowSet cachedRs = factory.createCachedRowSet();
				cachedRs.populate(rs);
				return cachedRs;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static CachedRowSet executeQuery(String sql, String[] parameters)
	{
		try
		{
			Class.forName(JDBC_DRIVER);
			try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
					PreparedStatement pstmt = conn.prepareStatement(sql);)
			{
				for (int i = 0; i < parameters.length; i++)
				{
					pstmt.setString(i + 1, parameters[i]);
				}
				ResultSet rs = pstmt.executeQuery();
				RowSetFactory factory = RowSetProvider.newFactory();
				CachedRowSet cachedRs = factory.createCachedRowSet();
				cachedRs.populate(rs);
				rs.close();
				return cachedRs;
			}

		} catch (Exception e)
		{
		}

		return null;
	}

}
