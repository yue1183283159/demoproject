package com.spring.ioc;

import java.util.List;
import java.util.Map;
import java.util.Properties;

public class ComplexObject
{
	private String[] strArr;
	private List<String> strList;
	private Map<String, Integer> tMap;
	private Properties configs;
	private DataSource dataSource;
	private int id;

	public ComplexObject()
	{
	}

	public ComplexObject(int id, DataSource dataSource)
	{
		this.id = id;
		this.dataSource = dataSource;
	}

	public DataSource getDataSource()
	{
		return dataSource;
	}

	public void setDataSource(DataSource dataSource)
	{
		this.dataSource = dataSource;
	}

	public Properties getConfigs()
	{
		return configs;
	}

	public void setConfigs(Properties configs)
	{
		this.configs = configs;
	}

	public String[] getStrArr()
	{
		return strArr;
	}

	public void setStrArr(String[] strArr)
	{
		this.strArr = strArr;
	}

	public List<String> getStrList()
	{
		return strList;
	}

	public void setStrList(List<String> strList)
	{
		this.strList = strList;
	}

	public Map<String, Integer> gettMap()
	{
		return tMap;
	}

	public void settMap(Map<String, Integer> tMap)
	{
		this.tMap = tMap;
	}

}
