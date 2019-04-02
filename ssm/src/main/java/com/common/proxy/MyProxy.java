package com.common.proxy;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;

import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

public class MyProxy {
	public static Object newProxyInstance(Class clazz, InvocationHandler h) throws Exception {
		// 代理类类名
		String cname = clazz.getName().substring(clazz.getName().lastIndexOf(".") + 1) + "$Proxy0";
		// 手写代理类源码
		StringBuilder source = getSource(clazz, h, cname);
		// 产生代理类的java文件
		String filename = Thread.currentThread().getContextClassLoader().getResource("").getPath()
				+ clazz.getPackage().getName().replaceAll("\\.", "/") + "/" + cname + ".java";
		System.out.println(filename);
		// 把源码写入到文件中
		File file = new File(filename);
		// FileUtil.writeStringToFile(file, source.toString());
		// 编译
		// 拿到编译器
		JavaCompiler complier = ToolProvider.getSystemJavaCompiler();
		// 文件管理者
		StandardJavaFileManager fileMgr = complier.getStandardFileManager(null, null, null);
		// 获取文件
		Iterable units = fileMgr.getJavaFileObjects(filename);
		// 编译任务
		CompilationTask t = complier.getTask(null, fileMgr, null, null, null, units);
		// 进行编译
		t.call();
		fileMgr.close();
		// load 到内存
		ClassLoader cl = ClassLoader.getSystemClassLoader();
		Class<?> c = cl.loadClass(clazz.getPackage().getName() + "." + cname);
		// 获取构造函数Constructor
		Constructor<?> ctr = c.getConstructor(h.getClass());
		return ctr.newInstance(h);

	}

	// 手写代理类源码
	private static StringBuilder getSource(Class clazz, InvocationHandler handler, String cname) {
		String hname = handler.getClass().getName();
		// 换行符
		String line = "\r\n";
		String space = " ";
		StringBuilder source = new StringBuilder();
		// 包声明
		source.append("package" + space + clazz.getPackage().getName() + ";").append(line);
		// 获取类的名称
		source.append(Modifier.toString(clazz.getModifiers()) + space + "class" + space + cname + space);
		// 继承接口
		source.append("implements" + space);
		Class[] interfaces = clazz.getInterfaces();
		for (int i = 0; i < interfaces.length; i++) {
			source.append(interfaces[i].getName());
			if (i != interfaces.length - 1) {
				source.append(",");
			}
		}

		source.append("{").append(line);
		// 声明变量
		source.append("private " + handler + " h;").append(line);
		// 构造方法
		source.append("public " + cname + "(" + handler + " h){").append(line);
		source.append("this.h=h;").append(line).append("}").append(line);
		// 实现所有的方法
		Method[] methods = clazz.getDeclaredMethods();
		for (Method method : methods) {
			// 获取方法返回类型
			Class returnType = method.getReturnType();
			// 获取方法上的所有注解
			Annotation[] annotations = method.getDeclaredAnnotations();
			for (Annotation annotation : annotations) {
				// 打印注解声明
				source.append("@" + annotation.annotationType().getName()).append(line);
			}
			// 打印方法声明
			source.append(Modifier.toString(method.getModifiers()) + " " + returnType.getName() + " " + method.getName()
					+ "(");
			// 获取方法的所有参数
			Parameter[] parameters = method.getParameters();
			// 参数字符串
			StringBuilder args = new StringBuilder();
			for (int i = 0; i < parameters.length; i++) {
				// 参数的类型，形参（全是arg123..）
				source.append(parameters[i].getType().getName() + " " + parameters[i].getName());
				args.append(parameters[i].getName());
				if (i != parameters.length - 1) {
					source.append(",");
					args.append(",");
				}
			}

			source.append("){").append(line);
			// 方法逻辑
			source.append("Object obj=null; \n try {").append(line);
			source.append("Class[] args = new Class[]{" + args + "};").append(line);
			source.append(Method.class.getName() + " method = " + clazz.getName() + ".class.getMethod(\""
					+ method.getName() + "\",args);").append(line);
			source.append("obj = h.invoke(this,method,args);").append(line);
			source.append("} catch (Exception e) {\n" + "e.printStackTrace();\n" + "}catch (Throwable throwable) {\n"
					+ "throwable.printStackTrace();\n" + "}").append(line);
			// 方法结束
			source.append("return (obj!=null)?(" + returnType.getName() + ")obj:null;\n}").append(line);

		}

		// 类结束
		source.append("}");
		return source;
	}
}
