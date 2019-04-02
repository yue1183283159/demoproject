package com.minimybatis;

public class SqlSessionFactory {
	static {
		SqlMapperHolder inst = SqlMapperHolder.INSTANCE;
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SqlSession openSession() {
		return new SqlSession();
	}
}
