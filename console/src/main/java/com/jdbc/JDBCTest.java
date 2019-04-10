package com.jdbc;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.sql.rowset.CachedRowSet;

import org.apache.commons.dbutils.*;
import org.junit.Before;
import org.junit.Test;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.sun.org.apache.bcel.internal.generic.NEW;

public class JDBCTest
{
	private static String driverClass = null;
	private static String url = null;
	private static String password = null;
	private static String user = null;

	@Before
	public void init()
	{
		try
		{
			Properties properties = new Properties();
			/**
			 * 相对路径： . 这个点代表当前目录。当前目录本质上是java命令运行的目录 java项目： 在ecplise中，当前目录指向项目的根目录。 web项目：
			 * 当前目录指向%tomcat%/bin目录 1)结论： 在web项目不能使用相对路径
			 * 
			 * web项目中加载配置文件： ServletContext.getRealPath() / getResourceAsStream()
			 * 这种方式对于jdbcUtil这个工具而言，放到java项目中找不到ServletContext对象，不通用的！
			 * 2)不能使用ServletContext读取文件
			 * 
			 * 3）使用类路径方式读取配置文件
			 * 
			 */
			Class clazz = JDBCTest.class;
			/**
			 * 这个斜杠：代表项目的类路径的根目录。 类路径： 查询类的目录/路径 java项目下： 类路径的根目录，指向项目的bin目录
			 * web项目下：类路径的根目录，指向项目的WEB-INF/classes目录
			 * 
			 * 只有把配置文件放在src目录的根目录下，那么这些文件就会自动拷贝到项目的类路径根目录下。
			 */
			InputStream in = clazz.getResourceAsStream("/com/jdbc/jdbc.properties");
			properties.load(in);

			url = properties.getProperty("url");
			user = properties.getProperty("username");
			password = properties.getProperty("password");
			driverClass = properties.getProperty("driver");

			in.close();
			// System.out.println(url);

		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	// @Test
	public void testInsert() throws Exception
	{
		Connection connection = MySqlHelper.getConnection();
		String sql = "insert into sys_role(name,parent_id,addon) values(?,?,now())";
		PreparedStatement pStatement = connection.prepareStatement(sql);
		pStatement.setString(1, "编辑管理员");
		pStatement.setInt(2, 0);

		int n = pStatement.executeUpdate();
		// pStatement.execute();
		pStatement.close();
		connection.close();
	}

	// @Test
	public void testBatchInsert() throws Exception
	{
		Connection connection = MySqlHelper.getConnection();
		Statement statement = connection.createStatement();
		for (int i = 0; i < 100; i++)
		{
			String sql = "insert into sys_role(name,parent_id,addon) values('test',0,now())";
			statement.addBatch(sql);
		}
		int[] nArr = statement.executeBatch();
		System.out.println(nArr.length);
		statement.execute("truncate table sys_role");
		statement.close();
		connection.close();
	}

	/*
	 * 执行批处理的时候，需要多次打开关闭数据库进行网络传输，效率低
	 */
	// @Test
	public void testPreparedBatchInsert() throws Exception
	{
		Connection connection = MySqlHelper.getConnection();
		String sql = "insert into sys_role(name,parent_id,addon) values(?,?,now())";
		PreparedStatement pStatement = connection.prepareStatement(sql);
		long start = System.currentTimeMillis();
		for (int i = 0; i < 100; i++)
		{
			pStatement.setString(1, "Test" + i);
			pStatement.setInt(2, 0);
			pStatement.addBatch();
		}
		int[] nArr = pStatement.executeBatch();
		long end = System.currentTimeMillis();
		System.out.println("insert " + nArr.length + " record, cost " + (end - start));
		pStatement.close();
		connection.close();
	}

	/*
	 * 使用事物，手动提交事物，执行批处理的时候效率更高。因为只有一次打开关闭数据库和网络传输
	 */
	// @Test
	public void testJdbcTransaction() throws Exception
	{
		Connection connection = MySqlHelper.getConnection();
		connection.setAutoCommit(false);// 手动管理jdbc事物
		String sql = "insert into sys_role(name,parent_id,addon) values(?,?,now())";
		PreparedStatement pStatement = connection.prepareStatement(sql);
		long start = System.currentTimeMillis();
		for (int i = 0; i < 100; i++)
		{
			pStatement.setString(1, "Test" + i);
			pStatement.setInt(2, 0);
			pStatement.addBatch();
		}
		int[] nArr = pStatement.executeBatch();
		connection.commit();// 提交事物
		long end = System.currentTimeMillis();
		System.out.println("use transaction, insert " + nArr.length + " record, cost " + (end - start));
		pStatement.close();
		connection.close();
	}

	// @Test
	public void testCreateTable()
	{
		Connection conn = null;
		Statement stmt = null;
		try
		{
			Class.forName(driverClass);
			conn = DriverManager.getConnection(url, user, password);
			stmt = conn.createStatement();
			String sql = "create table sys_node(\r\n" + "	id int not null primary key ,\r\n"
					+ "	name varchar(50) not null,\r\n" + "	comments varchar(100),\r\n"
					+ "	flag_delete tinyint default 0\r\n" + ")engine=innodb charset=utf8;";

			boolean result = stmt.execute(sql);
			System.out.println(result);
			stmt.close();
			conn.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	// @Test
	public void testMySqlHelper()
	{
		Connection connection = MySqlHelper.getConnection();
		System.out.println(connection);
	}

	// @Test
	public void testQuery()
	{
		Connection conn = null;
		Statement stmt = null;
		ResultSet rSet = null;
		try
		{
			Class.forName(driverClass);
			conn = DriverManager.getConnection(url, user, password);
			stmt = conn.createStatement();
			rSet = stmt.executeQuery("select now() as time");
			while (rSet.next())
			{
				System.out.println(rSet.getString(1));
				System.out.println(rSet.getString("time"));
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			if (rSet != null)// 防止出现空指针异常
			{
				try
				{
					rSet.close();
				} catch (SQLException e)
				{
					e.printStackTrace();
				} finally
				{
					rSet = null;
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

	}

	// @Test
	public void testMyPool() throws Exception
	{
		MyConnectionPool pool = new MyConnectionPool();
		Connection connection = pool.getConnection();
		Statement statement = connection.createStatement();
		String sql = "select now()";
		ResultSet rSet = statement.executeQuery(sql);
		while (rSet.next())
		{
			System.out.println(rSet.getString(1));
		}
		rSet.close();
		statement.close();
		connection.close();
	}

	// @Test
	public void testDruidPool() throws Exception
	{
		DruidPool druidPool = DruidPool.getInstance();
		System.out.println("Connection Number:" + druidPool.getDruidDataSource().getInitialSize());
		DruidPooledConnection connection = druidPool.getConnection();
		Statement statement = connection.createStatement();
		String sql = "select now()";
		ResultSet rSet = statement.executeQuery(sql);
		while (rSet.next())
		{
			System.out.println(rSet.getString(1));
		}
		rSet.close();
		statement.close();
		connection.close();
	}

	@Test
	public void testJdbcUtils() throws Exception
	{
		JDBCUtils jdbcUtils = JDBCUtils.getInstance();
		// 因为在JDBCUtils中返回结果之前已经关闭了连接，所以ResultSet不能再移动指针获取数据
		// ResultSet rSet = jdbcUtils.executeQuery("select now() time");
		// while (rSet.next())
		// {
		// System.out.println(rSet.getString("time"));
		// }

		int n = jdbcUtils.executeUpdate("insert into sys_role(name,parent_id,addon) values('TestData',0,now())");
		System.out.println(n);

		String sql = "insert into sys_role(name,parent_id,addon) values(?,?,now())";
		String[] values = { "TestData1", "0" };
		n = jdbcUtils.executeUpdate(sql, values);
		System.out.println(n);

		CachedRowSet cachedRowSet = jdbcUtils.executeQueryEx("select now() time");
		while (cachedRowSet.next())
		{
			System.out.println(cachedRowSet.getString("time"));
		}

		sql = "select * from sys_role where parent_id=?";
		values = new String[] { "0" };
		cachedRowSet = jdbcUtils.executeQueryEx(sql, values);
		while (cachedRowSet.next())
		{
			int id = cachedRowSet.getInt("id");
			String name = cachedRowSet.getString("name");
			int parentId = cachedRowSet.getInt("parent_id");
			String addon = cachedRowSet.getString("addon");
			System.out.println(id + " " + name + " " + parentId + " " + addon);
		}

		jdbcUtils.executeUpdate("truncate table sys_role");
		System.out.println("--------END---------");
	}

	@Test
	public void testBatchInsertData()
	{
		String sql = "insert into sys_role(name,parent_id,addon) values(?,?,now())";
		List<String[]> valueList = new ArrayList<>();
		String[] values = null;
		for (int i = 0; i < 100; i++)
		{
			values = new String[2];
			values[0] = "TestData" + i;
			values[1] = "0";
			valueList.add(values);
		}
		JDBCUtils jdbcUtils = JDBCUtils.getInstance();
		int n = jdbcUtils.executeBatchInsert(sql, valueList);
		System.out.println("Insert Record: " + n);
		jdbcUtils.executeUpdate("truncate table sys_role");
		System.out.println("--------END---------");
	}
	
	@Test
	public void testApachedbutils()
	{
		QueryRunner runner=new QueryRunner(DruidPool.getInstance().getDruidDataSource());
		
		String addSql="insert into sys_role(name,parent_id,addon) values(?,?,now())";
		Object[] params= {"Test",0};
		int n=0;
		try
		{
			//n=runner.update(addSql, params);
			System.out.println(n);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		
		String delSql="delete from sys_role where name=?";
		try
		{
			runner.update(delSql, "Test");
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		
		String updateSql="update sys_role set name=?,parent_id=? where id=?";
		Object[] params1= {"新闻编辑员",0,2};
		try
		{
			runner.update(updateSql, params1);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		
		//String selSql="select id,name from sys_role limit 1";
		try
		{
			//SysRole sysRole=(SysRole)runner.query(selSql, new BeanHandler(SysRole.class))	;
			//System.out.println(sysRole);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		//runner.query(sql, rsh)
	}

}

class SysRole
{
	private int id;
	private String name;
	
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
}

