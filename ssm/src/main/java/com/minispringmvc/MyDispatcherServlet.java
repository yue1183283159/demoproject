package com.minispringmvc;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义前端控制器(手写springmvc)原理分析 1.创建一个前端控制器MyDispatcherServlet拦截所有请求
 * 2.初始化操作,重写servlet init 方法 2.1
 * 扫描指定包的所有的类,注入到springmvc容器，存放在Map集合中，key为默认类名小写，value为对象 2.2 将url映射和方法进行关联 2.3
 * 判断类上是否有注解,使用java反射机制循环遍历方法 ；判断方法上是否存在注解，进行封装url和方法对应存入集合中
 * 3.处理请求，重写doGet或者是doPost方法
 * 获取请求url,从urlBeans集合获取实例对象,获取成功实例对象后,调用urlMethods集合获取方法名称,使用反射机制执行方法
 */
// https://blog.csdn.net/hellozpc/article/details/80471909
public class MyDispatcherServlet extends HttpServlet {
	// springmvc beans容器，存放controller实例。key：类名id，value：类实例
	private ConcurrentHashMap<String, Object> controllerBeans = new ConcurrentHashMap<>();

	// springmvc urlbeans容器，存放请求地址和类名的映射，key：请求地址，value： 类实例
	private ConcurrentHashMap<String, Object> urlBeans = new ConcurrentHashMap<>();

	// springmvc urlmethods容器，存放请求地址和方法名的映射。key：请求地址，value：方法名称
	private ConcurrentHashMap<String, String> urlMethods = new ConcurrentHashMap<>();

	public void init() throws ServletException {
		// 1. 获取当前controller包下的所有类
		List<Class<?>> classes = ClassUtil.getClass("com.minispringmv.controller");
		// 2. 将扫包范围内所有类，注入到springmvc容器（controllerbeans）中
		try {
			findClassMVCAnnotation(classes);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//3. 将url映射和方法进行关联
		handleMapping();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html;charset=UTF-8");
		// 1. 获取请求url地址
		String requestURI = req.getRequestURI();
		if (requestURI == null || requestURI.length() == 0) {
			return;
		}

		// 2. 从map集合中获取控制对象
		Object object = urlBeans.get(requestURI);
		if (object == null) {
			resp.getWriter().println("404, 您访问的资源不存在");
			return;
		}

		// 3. 使用url地址获取方法
		String methodName = urlMethods.get(requestURI);
		if (methodName == null) {
			resp.getWriter().println("404, 您访问的资源不存在");
			return;
		}
		// 4. 使用反射机制调用方法
		String resultPage = (String) methodInvoke(object, methodName);
		// 5. 调用视图转换器渲染页面展示
		myResourceViewResolver(resultPage, req, resp);
	}

	private void myResourceViewResolver(String resultPage, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String prefix = "/";
		String suffix = ".jsp";
		req.getRequestDispatcher(prefix + resultPage + suffix).forward(req, resp);
	}

	private Object methodInvoke(Object object, String methodName) {
		try {
			Class<? extends Object> classInfo = object.getClass();
			Method method = classInfo.getMethod(methodName);
			Object result = method.invoke(object);
			return result;
		} catch (Exception e) {
			return null;
		}
	}

	// 将扫包范围的所有类，注入到springmvc容器里面，存放在map集合中
	private void findClassMVCAnnotation(List<Class<?>> classes) throws Exception {
		for (Class<?> classInfo : classes) {
			// 判断类上是否有加注解
			MyController myController = classInfo.getDeclaredAnnotation(MyController.class);
			if (myController != null) {
				// 默认类名首字母小写
				String beanId = ClassUtil.toLowerCaseFirstOne(classInfo.getSimpleName());
				// 实例化对象
				Object object = ClassUtil.newInstance(classInfo);
				controllerBeans.put(beanId, object);
			}
		}

	}

	// 将url映射和方法进行关联
	public void handleMapping() {
		// 1. 遍历controller bean容器，判断类上是否有url映射注解
		for (Map.Entry<String, Object> mvcBean : controllerBeans.entrySet()) {
			// 2. 遍历所有方法上是否有url映射注解
			Object object = mvcBean.getValue();
			// 3. 判断类上是否有url映射注解
			Class<? extends Object> classInfo = object.getClass();
			MyRequestMapping requestMapping = classInfo.getDeclaredAnnotation(MyRequestMapping.class);
			String baseUrl = "";
			if (requestMapping != null) {
				baseUrl = requestMapping.value();// 获取类上url映射地址
			}
			// 4. 判断方法上是否有加url映射
			Method[] declaredMethods = classInfo.getDeclaredMethods();
			for (Method method : declaredMethods) {
				// 判断方法上是否有加url映射注解
				MyRequestMapping methodRequestMapping = method.getDeclaredAnnotation(MyRequestMapping.class);
				if (methodRequestMapping != null) {
					String methodUrl = baseUrl + methodRequestMapping.value();
					urlBeans.put(methodUrl, object);
					urlMethods.put(methodUrl, method.getName());
				}
			}
		}
	}
}
