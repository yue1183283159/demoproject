package com.spring.annotation;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;

import com.alibaba.druid.pool.DruidDataSource;

@Configuration
@ComponentScan("com.spring.annotation")
@PropertySource("classpath:config.properties")
public class AppRootConfig {// 使用类来取代spring-ico.xml配置文件

	// 当以注解方式整合第三方bean对象时，可以采用如下策略
	@Bean(value = "dataSource",initMethod="init",destroyMethod="close")
	@Lazy(false)
	public DataSource newDruidDataSource(@Value("${driver}") String driver, @Value("${url}") String url,
			@Value("${username}") String userName, @Value("${password}") String password) {
		
		DruidDataSource dSource = new DruidDataSource();
		
		System.out.println(driver);
		
		dSource.setDriverClassName(driver);
		dSource.setUrl(url);
		dSource.setUsername(userName);
		dSource.setPassword(password);
		return dSource;
	}

	@Bean("c3p0DataSource")
	public DataSource newC3p0DataSource() {
		// combxpo
		return null;
	}

}
