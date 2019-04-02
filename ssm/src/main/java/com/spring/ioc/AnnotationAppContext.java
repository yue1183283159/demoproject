package com.spring.ioc;

import java.io.File;
import java.io.FileFilter;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

//扫描包中的类，然后创建对象。理解spring 注解构造对象的原理
public class AnnotationAppContext
{
	private Map<String, Object> beanMap = new HashMap<>();

	public AnnotationAppContext()
	{
		String pkg = "project";
		scanPkg(pkg);
	}

	// 扫描指定包，找到包中的类文件
	private void scanPkg(String pkg)
	{
		// 替换包名中的.，将包结构转换为目录结构
		String pkgDir = pkg.replaceAll("\\.", "/");
		// 获取目录结构在类路径中的位置（其中URL中封装了具体资源的路径）
		URL url = getClass().getClassLoader().getResource(pkgDir);
		// 基于这个路径资源构建一个文件对象
		File file = new File(url.getFile());
		// 获得此目录中指定标准（.class）的文件
		File[] files = file.listFiles(new FileFilter()
		{
			@Override
			public boolean accept(File file)
			{
				// 获取文件名
				String fName = file.getName();// UserController.class
				if (file.isDirectory())
				{
					scanPkg(pkg + "." + fName);
				} else
				{
					if (fName.endsWith(".class"))
					{
						return true;
					}
				}
				return false;
			}

		});

		for (File f : files)
		{
			String fName = f.getName();
			// 获取去除.class以后的文件名
			fName = fName.substring(0, fName.lastIndexOf("."));
			// 类名，首字母小写，当作map的key
			String key = String.valueOf(fName.charAt(0)).toLowerCase() + fName.substring(1);
			//获取包名，类名
			String clsName = pkg + "." + fName;
			try
			{
				//通过反射构建对象
				Object obj = Class.forName(clsName).newInstance();
				//将构造的对象放入map中
				beanMap.put(key, obj);

			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}

	}

	public Object getBean(String key)
	{
		return beanMap.get(key);
	}

	public void close()
	{
		beanMap.clear();
		beanMap = null;
	}

	public static void main(String[] args)
	{
		AnnotationAppContext ctx = new AnnotationAppContext();
		Object obj = ctx.getBean("userController");
		System.out.println(obj);
		ctx.close();
	}
}
