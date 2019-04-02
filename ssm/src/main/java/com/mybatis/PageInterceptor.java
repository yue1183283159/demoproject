package com.mybatis;

import java.sql.Connection;
import java.util.Properties;

import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;

@Intercepts({
		@Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class, Integer.class }) })
public class PageInterceptor implements Interceptor {

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		System.out.println("PageInterceptor");
		RoutingStatementHandler target = (RoutingStatementHandler) invocation.getTarget();
		System.out.println(target.getBoundSql().getSql());//拿到要执行的sql，可以在SQL上做一些优化处理
		return invocation.proceed();
	}

	@Override
	public Object plugin(Object target) {

		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {
		System.out.println("setProperties " + properties);
	}

}
