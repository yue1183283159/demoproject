package com.minimybatis;

public class MinMyBatis {
	public static void main(String[] args) {
		SqlSessionFactory factory = new SqlSessionFactory();
		SqlSession session = factory.openSession();
		CityDao cityDao = session.getMapper(CityDao.class);
		System.out.println(cityDao.findCityById(1));
	}
}
