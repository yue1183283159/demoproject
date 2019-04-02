package com.spring.ioc;

import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.alibaba.druid.pool.DruidDataSource;

@ComponentScan("com.spring.ioc")
public class AppConfig
{
	@Bean("sqlSessionTemplate")
	public SqlSessionTemplate newSqlSessionTemplate() throws Exception
	{
		Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
		SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory);

		return sqlSessionTemplate;
	}

	@Bean("druidDataSource")
	public DruidDataSource newDruidDataSource()
	{
		DruidDataSource ds = new DruidDataSource();
		ds.setDriverClassName("com.mysql.jdbc.Driver");
		ds.setUrl("jdbc:mysql://localhost:3306/aiyunnet_db");
		ds.setUsername("root");
		ds.setPassword("root");
		return ds;
	}
}
