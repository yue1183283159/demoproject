package com.mybatis;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

public class MyBatisMainTest {
	private SqlSessionFactory factory;

	@Before
	public void init() throws Exception {
		factory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsReader("mybatis-config2.xml"));
	}

	@Test
	public void testSaveObject() {
		Blog blog = new Blog();
		blog.setTitle("Test Data.");
		blog.setAuthor("Lucy");
		blog.setBrief("Only for test.");
		blog.setContent("It is test data.");
		blog.setReadCount(100);

		SqlSession session = factory.openSession();
		BlogMapper blogMapper = session.getMapper(BlogMapper.class);
		blogMapper.saveBlog(blog);

		// 通过读取mapper中定义的SQL，然后传入参数，执行数据持久化。
		// 这种写法不推荐使用:1. 需要在code中写入较长的硬编码字符串，容易出错，也不灵活。2.不能借助自动提示功能，提高编码速度
		// 推荐使用Mapper方法映射的形式。
		String statement = "com.mybatis.BlogMapper.saveBlog";
		int rows = session.insert(statement, blog);
		// rows是保存到数据库中的行数，一般用不到这个rows，因为成功与否不用rows来判断，如果没有异常就认为是成功的，有异常就认为是失败的。
		System.out.println("new blog id:" + blog.getId());
		session.commit();// 提交事务。如果没有显示提交，关闭session，只会先写入数据库，自增长会执行，但是最后没有提交，则没有插入新记录。
		// 如果不想每次手动提交，可以opensession时指定自动提交。factory.openSession(true);
		session.close();
	}

	@Test
	public void testUpdate() {
		SqlSession session = factory.openSession();
		session.update("com.mybatis.BlogMapper.updateBlogContentByArray", new Object[] { "new test data", 1 });
		System.out.println("update by array parameter.");

		Map<String, Object> updateMap = new HashMap<>();
		updateMap.put("id", 2);
		updateMap.put("content", "new test content data.");
		session.update("com.mybatis.BlogMapper.updateBlogContentByMap", updateMap);
		System.out.println("update by map parameter.");

		session.commit();
		session.close();
	}

	@Test
	public void testDeleteObject() {
		SqlSession session = factory.openSession();
		session.delete("com.mybatis.BlogMapper.deleteBlog", 3);
		// 只有一个参数的时候，直接写参数值既可以。也不需要名称对应。
		System.out.println("delete blog by id.");

		BlogMapper blogMapper = session.getMapper(BlogMapper.class);
		blogMapper.deleteBlog(4);

		session.commit();
		session.close();
	}

	@Test
	public void testQueryObject() {

		SqlSession session = factory.openSession();

		// 直接使用model和结果集进行映射，数据库字段名和model的属性名不一致，所以readCout属性没有赋值
		List<Blog> blogs = session.selectList("com.mybatis.BlogMapper.findAllBlog", Blog.class);
		System.out.println(blogs);

		// 在Mapper中定义一个resultMap，将model属性和表字段进行手动映射。
		blogs = session.selectList("com.mybatis.BlogMapper.findAllBlogEx", Blog.class);
		System.out.println(blogs);

		// 使用mapper接口获取数据
		BlogMapper blogMapper = session.getMapper(BlogMapper.class);
		// 底层仍然调用session.selectList("com.mybatis.BlogMapper.findAllBlogEx")方法
		blogs = blogMapper.findAllBlogEx();
		// mybatis为Mapper接口实现动态代理类，然后执行方法
		System.out.println(blogs);

		session.close();
	}

	// ${}和#{}的使用对比
	@Test
	public void testSort() {
		SqlSession session = factory.openSession();
		OrderCommand orderCommand = new OrderCommand();
		orderCommand.setColumnName("id");// 在mapper中使用${}取值。此时不是创建预编译语句，而是字符串的拼接。一般对应的是列名或表名
		orderCommand.setOrderType("desc");// 在mapper中使用${}取值。此时不是创建预编译语句，而是字符串的拼接。
		orderCommand.setCondition("2");// 在mapper中使用#{}取值。对应的一般是列名需要的值。
		BlogMapper blogMapper = session.getMapper(BlogMapper.class);
		List<Blog> blogs = blogMapper.findSortedBlog(orderCommand);
		for (Blog blog : blogs) {
			System.out.println(blog);
		}
		session.close();
	}

	// 动态sql的使用
	@Test
	public void testDynamicSql() {
		SqlSession session = factory.openSession();
		BlogMapper blogMapper = session.getMapper(BlogMapper.class);
		blogMapper.deleteBlogs(10, 9);
		System.out.println("delete many recored.");
		session.commit();
		session.close();
	}

	// mybatis中的拦截器使用
	@Test
	public void testInterceptor() {
		SqlSession session = factory.openSession();
		BlogMapper blogMapper = session.getMapper(BlogMapper.class);
		blogMapper.findAllBlogEx();
		System.out.println("Test MyBatis Interceptor.");

		session.close();
	}

	@Test
	public void testBatchInsert() {
		//1. 新获取一个模式为BATCH，自动提交为false的session
		// 如果自动提交设置为true,将无法控制提交的条数;设置为false，改为最后统一提交，但是可能导致内存溢出
		SqlSession session = factory.openSession(ExecutorType.BATCH, false);
		BlogMapper blogMapper = session.getMapper(BlogMapper.class);
		for (int i = 1; i <= 100; i++) {
			Blog blog = new Blog();
			blog.setTitle("Test Data." + i);
			blog.setAuthor("Lucy");
			blog.setBrief("Only for test.");
			blog.setContent("It is test data.");
			blog.setReadCount(i);
			blogMapper.saveBlog(blog);
		}
		
		session.commit();//手动每100个一提交，提交后无法回滚
		session.clearCache();//清理缓存，防止溢出
		session.close();
		
		//2.使用foreach动态拼接SQL，然后执行批量插入
		//动态拼接sql,发送到mysql的字节量不能超过1M，如果超过了，也会报异常。
		//所以批量插入，要控制每次插入的数据量
		SqlSession session1 = factory.openSession();
		BlogMapper blogMapper1 =session1.getMapper(BlogMapper.class);
		List<Blog> blogs=new ArrayList<>();
		for (int i = 1; i <= 100; i++) {
			Blog blog = new Blog();
			blog.setTitle("Test Data." + i);
			blog.setAuthor("Lucy");
			blog.setBrief("Only for test.");
			blog.setContent("It is test data.");
			blog.setReadCount(i);
			blogs.add(blog);
		}
		blogMapper1.batchSaveBlogs(blogs);
		session1.commit();
		session1.close();
	}

	// 如何为mapper接口实现代理，获取对应的sql方法名.底层代理对象的产生
	// 只有接口也可以创建代理对象，没有目标对象，就动态创建目标代理对象。
	@Test
	public void testMapperInterfaceProxy() {
		Class<?> cls = BlogMapper.class;
		// 如何为上述接口生产代理对象
		BlogMapper blogMapper = (BlogMapper) Proxy.newProxyInstance(cls.getClassLoader(), new Class[] { cls },
				new MapperProxyHandler());
		// 调用代理对象方法
		blogMapper.findAllBlogEx();
		// mybatis底层也是实现了mapper接口的代理，然后通过代理获取接口类路径然后拼接方法名，调用SqlSession的对应方法。
	}

}

// mapper接口的代理类
class MapperProxyHandler implements InvocationHandler {

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

		System.out.println("MapperProxyHandler invoked");
		System.out.println("MapperProxyHandler method:" + method.getName());

		String clsName = proxy.getClass().getInterfaces()[0].getName();
		String methodName = method.getName();
		String statement = clsName + "." + methodName;
		System.out.println(statement);
		// Object result=session.selectList(statement,args)
		// return result;
		return null;
	}

}
