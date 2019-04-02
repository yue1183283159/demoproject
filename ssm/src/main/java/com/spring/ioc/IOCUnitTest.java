package com.spring.ioc;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.sql.ResultSet;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import org.junit.Test;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;

public class IOCUnitTest
{
	@Test
	public void testStart()
	{
		// 初始化spring容器
		// ApplicationContext ctx= //抽象，父类，没有关闭方法，需要使用子类
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring-ioc.xml");
		System.out.println(ctx);
		// 获取我们需要的对象
		Date date1 = (Date) ctx.getBean("date1");
		Date date2 = ctx.getBean("date1", Date.class);
		System.out.println(date1);
		System.out.println(date2);
		ctx.close();
	}

	private Map<String, Object> beanMap = new HashMap();

	/*
	 * <T>写到方法的返回值左边时，表示此方法为一个泛型方法，具体类型有相关参数类型决定 何为泛型？编译时的一种类型，运行时无效 应用场景？项目中通用编程
	 * 泛型类型？泛型方法，泛型类 类型擦除？
	 * 
	 */
	// spring context中getbean("",Date.class)的实现
	public <T> T getBean(String key, Class<T> cls)
	{
		return (T) beanMap.get(key);
	}
	

	/*
	 * 测试泛型在运行时无效 绕过编译阶段，把200加入string list集合。在运行时通过反射加入
	 */
	@Test
	public void testGeneric() throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException
	{
		ArrayList<String> list = new ArrayList<String>();
		list.add("A");
		list.add("B");
		// list.add(200);
		Class<?> class1 = list.getClass();
		Method method = class1.getDeclaredMethod("add", Object.class);
		method.invoke(list, 200);
		System.out.println(list);

	}

	/*
	 * 类的类对象（字节码对象），类的实例对象
	 */
	@Test
	public void testBeansClass() throws ClassNotFoundException
	{
		// 获取Points类的类对象（字节码对象），同一个类的字节码对象是同一个值
		// 一个类型只有一份类对象
		Class<?> c1 = Points.class;
		Class<?> c2 = Class.forName("com.spring.ioc.Points");
		System.out.println(c1 == c2);// true

		// 类的实例对象
		Points p3 = new Points();
		Class<?> c3 = p3.getClass();
		System.out.println(c2 == c3);// true
		Points p4 = new Points();
		Class<?> c4 = p4.getClass();
		System.out.println(c3 == c4);// true
	}

	
	/*
	 * 构建bean对象 1. 抽象类构造对象时，xml中使用factory-method
	 */
	// @Test
	public void testBeanConstrunct()
	{

		System.out.println("构建bean对象的方法");

		// Calendar c1=Calendar.getInstance();
		// System.out.println(c1.toString());
		// Calendar c2=new GregorianCalendar();
		// System.out.println(c2.toString());
		//

		ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext(
				"spring-ioc.xml");
		Calendar calendar1 = (Calendar) classPathXmlApplicationContext.getBean("c1");
		Calendar calendar2 = (Calendar) classPathXmlApplicationContext.getBean("c2");
		System.out.println(calendar1.DATE);
		System.out.println(calendar2.DATE);

		// Date date1=calendar2.getTime();

		Date date2 = classPathXmlApplicationContext.getBean("date2", Date.class);
		System.out.println(date2);

		System.out.println("构建bean方法 结束");
		classPathXmlApplicationContext.close();
	}

	/*
	 * 默认情况下，使用单例模式，一个bean只初始化一次， 修改scope="prototype"，多例模式，每次获取都是一个新对象
	 * 
	 * bean的生命周期,lazy-init 懒加载
	 */
	// @Test
	public void testBeanScope()
	{
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring-ioc.xml");
		// 初始化的时候调用bean的init方法
		HelloService helloService = ctx.getBean("helloService", HelloService.class);
		HelloService helloService1 = ctx.getBean("helloService", HelloService.class);

		ctx.close(); // invoke bean的destroy方法
	}

	/*
	 * 依赖注入
	 */
	@Test
	public void testDI()
	{
		// ClassPathXmlApplicationContext ctx = new
		// ClassPathXmlApplicationContext("applicationContext.xml");
		//
		// SimpleDateFormat sdf = ctx.getBean("sdf", SimpleDateFormat.class);
		// String date = sdf.format(new Date());
		// System.out.println(date);
		// ctx.close();

		// 多属性注入
		ClassPathXmlApplicationContext ctxEx = new ClassPathXmlApplicationContext("spring-ioc.xml");
		DataSource dataSource = ctxEx.getBean("dataSource", DataSource.class);
		System.out.println(dataSource);
		System.out.println(dataSource.getDriver());
		System.out.println(dataSource.getUrl());
		System.out.println(dataSource.toString());

	com.spring.ioc.JdbcTemplate jdbcTemplate = ctxEx.getBean("jdbcTemplate", com.spring.ioc.JdbcTemplate.class);
		System.out.println(jdbcTemplate.getDataSource().toString());

		DataSource dSource = ctxEx.getBean("datasource1", DataSource.class);
		System.out.println(dSource.toString());
		//System.out.println(dSource.getCoreSize());

		System.out.println("==============complex object Dependency Injection ================");
		ComplexObject complexObject = ctxEx.getBean("complexObject", ComplexObject.class);
		System.out.println(Arrays.toString(complexObject.getStrArr()));
		System.out.println(complexObject.getStrList());
		System.out.println(complexObject.gettMap());
		// System.out.println(complexObject.getConfigs().get("driver"));

		System.out.println("===========================");
		ComplexObject co1 = ctxEx.getBean("complexObject1", ComplexObject.class);
		System.out.println(co1.getDataSource().toString());

		ctxEx.close();
	}

	//@Test
	public void testReflectDemo() throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		Scanner sc = new Scanner(System.in);
		System.out.println("please input class");
		String pkgCls = sc.nextLine();
		System.out.println("please input method");
		String methodName = sc.nextLine();

		//构造类对象，类的字节码对象。
		Class<?> c = Class.forName(pkgCls);
		Method m = c.getDeclaredMethod(methodName);
		Object obj = c.newInstance();
		Object result = m.invoke(obj);
		System.out.println(result);
	}	
	
	@Test
	public void testXMLIOC()
	{
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring-ioc.xml");
		HelloService helloService = ctx.getBean("helloService", HelloService.class);
		helloService.sayHello();
		ctx.close();
	}

	@Test
	public void testTestService()
	{
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring-ioc.xml");
		TestService testService = ctx.getBean("testService", TestService.class);
		testService.hello();
		ctx.close();
	}

	/*
	 * @Test public void testC3p0ConnectPool() throws SQLException {
	 * ClassPathXmlApplicationContext ctx = new
	 * ClassPathXmlApplicationContext("applicationContext.xml"); // c3p0连接池
	 * ComboPooledDataSource ds = ctx.getBean("c3p0DataSource",
	 * ComboPooledDataSource.class); Connection conn = ds.getConnection(); Statement
	 * stm = conn.createStatement(); ResultSet rs =
	 * stm.executeQuery("select now()"); System.out.
	 * println("===================Spring C3p0  Demo=========================");
	 * while (rs.next()) { System.out.println(rs.getString(1)); } rs.close();
	 * conn.close();
	 * 
	 * ctx.close(); }
	 */

	@Test
	public void testDruidConnectPool() throws Exception
	{
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring-ioc.xml");
		DruidDataSource druidDataSource = ctx.getBean("druidDataSource", DruidDataSource.class);
		DruidPooledConnection con = druidDataSource.getConnection();
		Statement stmt = con.createStatement();
		ResultSet rs1 = stmt.executeQuery("select now()");
		System.out.println("===================Spring Druid Demo=========================");
		while (rs1.next())
		{
			System.out.println(rs1.getString(1));
		}
		rs1.close();
		con.close();
		ctx.close();
	}

	@Test
	public void testMybatisInsert() throws Exception
	{
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring-ioc.xml");
		// error, 类型错误
		// SqlSessionFactoryBean sqlSessionFactoryBean =
		// ctx.getBean("sqlSessionFactoryBean", SqlSessionFactoryBean.class);
		// SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBean.getObject();

		SqlSessionFactory sqlSessionFactory = ctx.getBean("sqlSessionFactoryBean", SqlSessionFactory.class);
		SqlSession session = sqlSessionFactory.openSession();
		//WebNews b = new WebNews();
		//b.setTitle("Test Spring Mybatis");
		//b.setBrief("It is spring mybatis demo.");
		//int blogID = session.insert("aiyunnet.entity.DomainMapper.insertBlog", b);
		session.commit();
		System.out.println("===================Spring Mybatis Insert Demo=========================");
		//System.out.println("New inserted blog id: " + blogID);
		ctx.close();
	}

	@Test
	public void testMybatisTemplate() throws Exception
	{
		// String config = "mybatis-config.xml";
		// Reader reader = Resources.getResourceAsReader(config);
		// SqlSessionFactory sqlSessionFactory = new
		// SqlSessionFactoryBuilder().build(reader);
		// SqlSessionTemplate sqlSessionTemplate = new
		// SqlSessionTemplate(sqlSessionFactory);
		// Blog blog =
		// (Blog)sqlSessionTemplate.selectOne("aiyunnet.entity.DomainMapper.selectBlogById",
		// 1);

		// 用spring整合SqlSessionTemplate
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring-ioc.xml");
		SqlSessionTemplate sqlSessionTemplate = ctx.getBean("sqlSessionTemplate", SqlSessionTemplate.class);
		//Blog blog = (Blog) sqlSessionTemplate.selectOne("aiyunnet.entity.DomainMapper.selectBlogById", 1);
		System.out.println("===================Spring Mybatis SqlSessionTemplate Query Demo=========================");
		//System.out.println(blog.getId() + ", " + blog.getTitle() + ", " + blog.getBrief());
		ctx.close();
	}

	@Test
	public void testJdbcTemplate()
	{
		// JdbcTemplate jdbcTmp=new JdbcTemplate();
		// jdbcTmp.setDataSource(dataSource);
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring-ioc.xml");
		JdbcTemplate jdbcTemplate = ctx.getBean("jdbcTemplate2", JdbcTemplate.class);
		System.out.println("===================Spring JdbcTemplate Insert and Query Demo=========================");
		//jdbcTemplate.execute(
				//"insert into blog(title, brief) values('Test Spring JdbcTemplate','It is spring jdbctemplate demo.')");
		//String title = jdbcTemplate.queryForObject("select title from blog where id=2", String.class);
		//System.out.println(title);
		ctx.close();
	}

	@Test
	public void testClassIOC()
	{
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
		HelloService helloService = ctx.getBean("helloService", HelloService.class);
		helloService.sayHello();
		ctx.close();
	}
}

class Points
{
}
