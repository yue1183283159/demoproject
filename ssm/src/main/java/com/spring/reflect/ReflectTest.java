package com.spring.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.jdbc.Null;
import org.junit.Test;

public class ReflectTest {

	@Test
	public void testConstructor1() throws Exception {
		// 1. 获取类对象（字节码对象）
		Class<?> c = Class.forName("com.spring.reflect.Container");
		// List<? extends Object> list=new ArrayList<String>();
		// 2. 通过类对象获取没有参数的构造方法对象
		Constructor<?> constructor = c.getConstructor();
		// 3. 设置构造方法的可访问性（因为类中的无参构造方法是私有的）
		constructor.setAccessible(true);
		// 4. 通过构造方法对象构建类的对象（类的实例）
		Object instance = constructor.newInstance();
		System.out.println(instance);
	}

	@Test
	public void testConstructor2() throws Exception {
		// 1. 获取类对象（字节码对象）
		Class<?> c = Class.forName("com.spring.reflect.Container");
		// 2. 通过类对象获取没有参数的构造方法对象
		Constructor<?> constructor = c.getConstructor(int.class, String.class);
		// 3. 设置构造方法的可访问性（因为类中的无参构造方法是私有的）
		constructor.setAccessible(true);
		// 4. 通过构造方法对象构建类的对象（类的实例）
		Object instance = constructor.newInstance(12, "test");
		System.out.println(instance);
	}

	@Test
	public void testReflectConstructor() throws Exception {
		Class clazz = Class.forName("com.spring.reflect.Student");
		System.out.println("**********************所有公有构造方法*********************************");
		Constructor[] constructors = clazz.getConstructors();
		for (Constructor con : constructors) {
			System.out.println(con);
		}

		System.out.println("************所有的构造方法(包括：私有、受保护、默认、公有)***************");
		constructors = clazz.getDeclaredConstructors();
		for (Constructor c : constructors) {
			System.out.println(c);
		}

		System.out.println("*****************获取公有、无参的构造方法*******************************");
		// 1>、因为是无参的构造方法所以类型是一个null,不写也可以：这里需要的是一个参数的类型，切记是类型
		// 2>、返回的是描述这个无参构造函数的类对象。
		Constructor constructor = clazz.getConstructor(null);
		System.out.println("constructor = " + constructor);
		// 调用构造方法
		Object obj = constructor.newInstance();
		System.out.println(obj);

		System.out.println("******************获取私有构造方法，并调用*******************************");
		constructor = clazz.getDeclaredConstructor(char.class);
		System.out.println(constructor);
		// 调用构造方法
		constructor.setAccessible(true);// 暴力访问(忽略掉访问修饰符)
		obj = constructor.newInstance('男');

	}

	@Test
	public void testReflectFields() throws Exception {
		Class clazz = Class.forName("com.spring.reflect.Student");
		System.out.println("************获取所有公有的字段********************");
		Field[] fields = clazz.getFields();
		for (Field field : fields) {
			System.out.println(field);
		}

		System.out.println("************获取所有的字段(包括私有、受保护、默认的)********************");
		fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			System.out.println(field);
		}

		System.out.println("*************获取公有字段**并调用***********************************");
		Field field = clazz.getField("name");
		System.out.println(field);

		Object object = clazz.getConstructor().newInstance();
		field.set(object, "刘德华");
		Student stu = (Student) object;
		System.out.println("验证姓名：" + stu.name);

		System.out.println("**************获取私有字段****并调用********************************");

		field = clazz.getDeclaredField("phoneNum");
		System.out.println(field);
		field.setAccessible(true);// 暴力反射，解除私有限定
		field.set(object, "18888889999");
		System.out.println("验证电话：" + stu);
	}

	@Test
	public void testReflectMethod() throws Exception {
		Class stuClass = Class.forName("com.spring.reflect.Student");

		System.out.println("***************获取所有的”公有“方法*******************");
		Method[] methodArray = stuClass.getMethods();
		for (Method m : methodArray) {
			System.out.println(m);
		}

		System.out.println("***************获取所有的方法，包括私有的*******************");
		methodArray = stuClass.getDeclaredMethods();
		for (Method m : methodArray) {
			System.out.println(m);
		}

		System.out.println("***************获取公有的show1()方法*******************");
		Method m = stuClass.getMethod("show1", String.class);
		System.out.println(m);

		// 实例化一个Student对象
		Object obj = stuClass.getConstructor().newInstance();
		m.invoke(obj, "刘德华");

		System.out.println("***************获取私有的show4()方法******************");
		m = stuClass.getDeclaredMethod("show4", int.class);
		System.out.println(m);
		m.setAccessible(true);// 解除私有限定
		Object result = m.invoke(obj, 20);// 需要两个参数，一个是要调用的对象（获取有反射），一个是实参
		System.out.println("返回值：" + result);
	}

	@Test
	public void testReflect() throws Exception {
		// 通过反射越过泛型检查
		ArrayList<String> strList = new ArrayList<>();
		strList.add("aaa");
		strList.add("bbb");
		// strList.add(100);
		// 获取ArrayList的Class对象，反向的调用add()方法，添加数据

		Class listClass = strList.getClass(); // 得到 strList 对象的字节码 对象
		// 获取add()方法
		Method m = listClass.getMethod("add", Object.class);
		// 调用add()方法
		m.invoke(strList, 100);
		// 遍历集合
		for (Object obj : strList) {
			System.out.println(obj);
		}
	}

	@Test
	public void testReflectAnnotation() throws Exception {
		Class<?> c = Class.forName("com.spring.reflect.TestClass");
		// 获取修饰类的注解信息
		CETypeAnnotation ceTypeAnnotation = c.getAnnotation(CETypeAnnotation.class);
		if (ceTypeAnnotation != null) {
			System.out.println("class value:" + ceTypeAnnotation.value());
		}
		Object testClassObj = null;
		// 获取修饰构造器类型的注解信息
		Constructor<?>[] constructors = c.getConstructors();
		for (Constructor<?> constructor : constructors) {
			CEConstructorAnnotation constructorAnnotation = constructor.getAnnotation(CEConstructorAnnotation.class);
			if (constructorAnnotation != null) {
				System.out.println("constructor desc:" + constructorAnnotation.value());
			}

			testClassObj = constructor.newInstance();
			System.out.println(testClassObj.getClass().getName());
		}

		// 获取修饰字段类型的注解信息
		Field[] fields = c.getDeclaredFields();
		for (Field field : fields) {
			CEFieldAnnotation fieldAnnotation = field.getAnnotation(CEFieldAnnotation.class);
			if (fieldAnnotation != null) {
				System.out.println(
						field.getName() + " desc: " + fieldAnnotation.value() + ", value: " + field.get(testClassObj));
			}
		}
		// 获取修饰方法类型的注解信息
		// 获取修饰方法参数类型的注解信息
		Method[] methods = c.getDeclaredMethods();
		for (Method method : methods) {
			CEMethodAnnotation methodAnnotation = method.getAnnotation(CEMethodAnnotation.class);
			if (methodAnnotation != null) {
				System.out.println(method.getName() + " desc: " + methodAnnotation.description());
			}

			for (Parameter parameter : method.getParameters()) {
				CEParameterAnnotation parameterAnnotation = parameter.getAnnotation(CEParameterAnnotation.class);
				if (parameterAnnotation != null) {
					System.out.println(parameter.getName() + " desc: " + parameterAnnotation.value());
				}
			}
		}

	}

}
