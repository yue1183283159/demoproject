package com.minimybatis;

//mapper文件作用是管理SQL语句与接口方法的映射
//在使用MyBatis框架的时候，会先从mapper中读取映射信息，包括接口名，方法名，查询返回的数据类型，SQL语句等
public class MapperInfo {
	private QueryType queryType;
	private String interfaceName;
	private String methodName;
	private String sql;
	private String resultType;

	public QueryType getQueryType() {
		return queryType;
	}

	public void setQueryType(QueryType queryType) {
		this.queryType = queryType;
	}

	public String getInterfaceName() {
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public String getResultType() {
		return resultType;
	}

	public void setResultType(String resultType) {
		this.resultType = resultType;
	}

	@Override
	public String toString() {
		return "MapperInfo{" + "queryType=" + queryType + ", interfaceName='" + interfaceName + '\'' + ", methodName='"
				+ methodName + '\'' + ", sql='" + sql + '\'' + ", resultType='" + resultType + '\'' + '}';
	}

}
