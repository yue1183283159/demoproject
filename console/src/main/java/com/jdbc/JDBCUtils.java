package com.jdbc;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.List;
import java.util.Properties;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.pool.DruidPooledConnection;

//使用druid数据库连接池提供数据源，提高访问效率
public class JDBCUtils
{
	private static JDBCUtils jdbcUtils = null;
	private static DruidDataSource druidDataSource = null;

	static
	{
		try
		{
			Properties properties = getProperties();
			if (properties != null)
			{
				druidDataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(properties);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private JDBCUtils()
	{
	}

	public static JDBCUtils getInstance()
	{
		if (jdbcUtils == null)
		{
			synchronized (JDBCUtils.class)
			{
				if (jdbcUtils == null)
				{
					jdbcUtils = new JDBCUtils();
				}
			}
		}

		return jdbcUtils;
	}

	public DruidDataSource getDataSource()
	{
		return druidDataSource;
	}

	public DruidPooledConnection getConnection() throws SQLException
	{
		return druidDataSource.getConnection();
	}

	public int executeUpdate(String sql)
	{
		return executeUpdate(sql, null);
	}

	public int executeUpdate(String sql, String[] values)
	{
		DruidPooledConnection druidPooledConnection = null;
		PreparedStatement preparedStatement = null;
		try
		{
			druidPooledConnection = getConnection();
			preparedStatement = druidPooledConnection.prepareStatement(sql);
			if (values != null && values.length > 0)
			{
				for (int i = 1; i <= values.length; i++)
				{
					preparedStatement.setString(i, values[i - 1]);
				}
			}
			int n = preparedStatement.executeUpdate();
			return n;
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			closeAll(null, preparedStatement, druidPooledConnection);
		}

		return 0;
	}

	// 对于批量插入操作，手动使用事物，提高效率
	public int executeBatchInsert(String sql, List<String[]> valueList)
	{
		DruidPooledConnection druidPooledConnection = null;
		PreparedStatement preparedStatement = null;
		try
		{
			druidPooledConnection = getConnection();
			druidPooledConnection.setAutoCommit(false);// 手动管理事物
			preparedStatement = druidPooledConnection.prepareStatement(sql);
			if (valueList != null && valueList.size() > 0)
			{
				for (String[] values : valueList)
				{
					for (int i = 1; i <= values.length; i++)
					{
						preparedStatement.setString(i, values[i - 1]);
					}

					preparedStatement.addBatch();
				}
			}

			int[] nArr = preparedStatement.executeBatch();
			druidPooledConnection.commit();// 提交事物，执行sql语句
			return nArr.length;
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			closeAll(null, preparedStatement, druidPooledConnection);
		}

		return 0;
	}

	// 不推荐使用，因为占用连接，每次读取一条记录，指针都要移动。
	// 返回的ResultSet在连接关闭之后就不能访问了
	public ResultSet executeQuery(String sql)
	{
		DruidPooledConnection druidPooledConnection = null;
		Statement statement = null;
		ResultSet rSet = null;
		try
		{
			druidPooledConnection = getConnection();
			statement = druidPooledConnection.createStatement();
			rSet = statement.executeQuery(sql);
			return rSet;
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			closeAll(rSet, statement, druidPooledConnection);
		}
		return null;
	}

	// 推荐使用，缓存ResultSet对象，将查询数据集放入内存中。
	public CachedRowSet executeQueryEx(String sql)
	{
		return executeQueryEx(sql, null);
	}

	public CachedRowSet executeQueryEx(String sql, String[] values)
	{
		DruidPooledConnection druidPooledConnection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rSet = null;
		try
		{
			druidPooledConnection = getConnection();
			// 先将sql骨架传到数据库，此时SQL语句不完整不能执行
			preparedStatement = druidPooledConnection.prepareStatement(sql);
			if (values != null && values.length > 0)
			{
				for (int i = 1; i <= values.length; i++)
				{
					preparedStatement.setString(i, values[i - 1]);
				}
			}

			rSet = preparedStatement.executeQuery();
			RowSetFactory factory = RowSetProvider.newFactory();
			CachedRowSet cachedRs = factory.createCachedRowSet();
			cachedRs.populate(rSet);
			return cachedRs;
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			closeAll(rSet, preparedStatement, druidPooledConnection);
		}

		return null;
	}

	private void closeAll(ResultSet rSet, Statement statement, DruidPooledConnection druidPooledConnection)
	{
		if (rSet != null)
		{
			try
			{
				rSet.close();
			} catch (Exception e)
			{
				e.printStackTrace();
			} finally
			{
				rSet = null;
			}
		}

		if (statement != null)
		{
			try
			{
				statement.close();
			} catch (Exception e)
			{
				e.printStackTrace();
			} finally
			{
				statement = null;
			}
		}

		if (druidPooledConnection != null)
		{
			try
			{
				druidPooledConnection.close();
			} catch (Exception e)
			{
				e.printStackTrace();
			} finally
			{
				druidPooledConnection = null;
			}
		}

	}

	private static Properties getProperties()
	{
		InputStream inputStream = null;
		try
		{
			Properties properties = new Properties();
			Class clazz = MySqlHelper.class;
			inputStream = clazz.getResourceAsStream("/com/jdbc/druid.properties");
			properties.load(inputStream);
			return properties;
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			if (inputStream != null)
			{
				try
				{
					inputStream.close();
				} catch (IOException e)
				{
					e.printStackTrace();
				} finally
				{
					inputStream = null;
				}
			}

		}
		return null;
	}

}
