package com.minimybatis;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

//在invoke方法中，根据传入的方法类获取接口名与方法名，进而通过SqlMappersHolder获取MapperInfo
//根据配置连接数据库，获取到一个PreparedStatement对象
//结合MapperInfo和参数列表设置PreparedStatement的参数，执行
//获取执行结果，通过反射技术将结果映射到实体类
public class SqlExecuteHandler implements InvocationHandler {

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		MapperInfo info = getMapperInfo(method);
		return executeSql(info, args);
	}

	private Object executeSql(MapperInfo info, Object[] params) throws Exception {
		Object result = null;
		PreparedStatement pStatement = ConnectionManager.get().prepareStatement(info.getSql());
		for (int i = 0; i < params.length; i++) {
			pStatement.setObject(i + 1, params[i]);
		}
		if (info.getQueryType() == QueryType.SELECT) {
			ResultSet rSet = pStatement.executeQuery();
			rSet.first();
			// 将查询结果映射java类或是基本数据类型。简化版只支持string和int类型
			if (rSet.getMetaData().getColumnCount() == 1) {
				switch (info.getResultType()) {
				case "int":
					result = rSet.getInt(1);
					break;

				default:
					result = rSet.getString(1);
					break;
				}
			} else {
				Class<?> resTypeClass = Class.forName(info.getResultType());
				Object inst = resTypeClass.newInstance();
				for (Field field : resTypeClass.getDeclaredFields()) {
					String setterName = "set" + field.getName().substring(0, 1).toUpperCase()
							+ field.getName().substring(1);
					Method method;
					switch (field.getType().getSimpleName()) {
					case "int":
						method = resTypeClass.getMethod(setterName, new Class[] { int.class });
						method.invoke(inst, rSet.getInt(field.getName()));
						break;

					default:
						method = resTypeClass.getMethod(setterName, new Class[] { String.class });
						method.invoke(inst, rSet.getString(field.getName()));
						break;
					}
				}
				result = inst;
			}
		} else {
			result = pStatement.executeUpdate();
		}

		return result;
	}

	private MapperInfo getMapperInfo(Method method) throws Exception {
		MapperInfo info = SqlMapperHolder.INSTANCE.getMapperInfo(method.getDeclaringClass().getName(),
				method.getName());

		if (info == null) {
			throw new Exception(
					"Mapper not found for method: " + method.getDeclaringClass().getName() + "." + method.getName());
		}
		return info;
	}

}
