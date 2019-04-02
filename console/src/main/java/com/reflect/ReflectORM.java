package com.reflect;

import java.lang.reflect.Field;

public class ReflectORM
{
	public static void main(String[] args)
	{
		Order order = new Order();
		order.id = 1001;
		order.payment = 30000;
		DBTools.insert(order);
	}
}

class DBTools
{
	public static void insert(Object object)
	{
		// order[id:1,payment:3000]
		// generate:insert into order(id,payment) values(1, 3000)
		try
		{
			// 得到类信息
			// 得到类名
			// 得到所有属性名
			// 得到所有属性值class Order
			Class classInfo = object.getClass();
			String className = classInfo.getSimpleName();
			StringBuilder sBuilder = new StringBuilder();
			sBuilder.append("insert into ");
			sBuilder.append(className + "(");
			Field[] fields = classInfo.getFields();

			// for (Field field : fields)
			// {
			// String fieldName = field.getName();
			// sBuilder.append(fieldName + ",");
			// }
			// sBuilder.deleteCharAt(sBuilder.length() - 1);//移除最后一个逗号
			// sBuilder.append(") values(");
			// for (Field field : fields)
			// {
			// Object value = field.get(object);
			// sBuilder.append(value + ",");
			// }
			// sBuilder.deleteCharAt(sBuilder.length() - 1);

			String fieldsStr = "";
			String valuesStr = "";
			for (Field field : fields)
			{
				String fieldName = field.getName();
				fieldsStr += fieldName + ",";
				Object value = field.get(object);
				valuesStr += value + ",";
			}

			sBuilder.append(fieldsStr.substring(0, fieldsStr.length() - 1));
			sBuilder.append(") values(");
			sBuilder.append(valuesStr.substring(0, valuesStr.length() - 1));

			sBuilder.append(")");

			System.out.println(sBuilder.toString());
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}

class Order
{
	public int id;
	public double payment;
}

class User
{
	public String username;
	public String password;
}
