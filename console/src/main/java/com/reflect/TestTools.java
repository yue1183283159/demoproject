package com.reflect;

import java.io.File;
import java.lang.reflect.Method;

import com.reflect.annotation.Test;

public class TestTools
{
	public static void main(String[] args)
	{
		try
		{
			// 遍历test目录下的所有测试类
			// 执行类中测试方法
			File rootFile = new File("src/com/reflect/test");
			File[] classFiles = rootFile.listFiles();
			for (File testClassFile : classFiles)
			{
				String fileName = testClassFile.getName();
				
				if (fileName.startsWith("Test"))
				{
					String className = fileName.substring(0, fileName.length() - 5);
					System.out.println();
					System.out.println("======================");
					// need package path to get the class path
					className = "com.reflect.test." + className;
					Class classInfo = Class.forName(className);
					Object object = classInfo.newInstance();
					Method[] methods = classInfo.getMethods();
					for (Method method : methods)
					{
						String methodName = method.getName();
						Test test = method.getAnnotation(Test.class);
						
						if (methodName.startsWith("test") || test != null)
						{
							Object value = method.invoke(object, null);

							if ((boolean) value == false)
							{
								System.out.println(className + " --> " + methodName + ": test failed.");
							} else
							{
								System.out.println(className + " --> " + methodName + ": test success.");
							}
						}
					}
				}

			}

		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
