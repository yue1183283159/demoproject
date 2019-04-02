package com.minispringmvc;

import java.io.File;
import java.io.FileFilter;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ClassUtil {

	// 获取某个接口下所有实现这个接口的类
	public static List<Class> getAllClassByInterface(Class c) {
		List<Class> classes = null;
		if (c.isInterface()) {
			// 获取当前包名
			String packageName = c.getPackage().getName();
			// 获取当前包下以及子包下所有类
			List<Class<?>> allClass = getClass(packageName);
			if (allClass != null) {
				classes = new ArrayList<>();
				for (Class clazz : allClass) {
					// 判断是否同一个接口
					if (c.isAssignableFrom(clazz)) {
						// 本身不加入
						if (!c.equals(clazz)) {
							classes.add(clazz);
						}
					}
				}
			}

		}
		return classes;
	}

	// 获得某个类所在package的所有类名
	public static String[] getPackageAllClassName(String classLocation, String packageName) {
		// 将packageName分解
		String[] packagePathSplit = packageName.split("\\.");
		String realClassLocation = classLocation;
		int packageLength = packagePathSplit.length;
		for (int i = 0; i < packageLength; i++) {
			realClassLocation = realClassLocation + File.separator + packagePathSplit[i];
		}

		File packageDir = new File(realClassLocation);
		if (packageDir.isDirectory()) {
			String[] allClassName = packageDir.list();
			return allClassName;
		}

		return null;
	}

	// 获取指定package下所有的class
	public static List<Class<?>> getClass(String packageName) {
		// 第一个class类集合
		List<Class<?>> classes = new ArrayList<>();
		// 是否循环迭代
		boolean isRecursive = true;
		// 获取包名并进行替换
		String packageDirName = packageName.replace(".", "/");
		// 定义一个枚举集合，并进行循环处理这个目录下的things
		Enumeration<URL> dirs;
		try {
			dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
			while (dirs.hasMoreElements()) {
				// 获取下一个元素
				URL url = (URL) dirs.nextElement();
				// 得到协议的名称
				String protocol = url.getProtocol();
				// 如果是以文件的形式保存在服务器
				if ("file".equals(protocol)) {
					// 获取包的物理路径
					String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
					// 以文件的方式扫描整个包下的文件，并添加到集合中
					findAndAddClassInPackageByFile(packageName, filePath, isRecursive, classes);
				} else if ("jar".equals(protocol)) {
					// 如果是jar文件，定义一个jarfile
					JarFile jarFile;
					try {
						jarFile = ((JarURLConnection) url.openConnection()).getJarFile();
						// 从此jar包中得到一个枚举类
						Enumeration<JarEntry> jarEntries = jarFile.entries();
						while (jarEntries.hasMoreElements()) {
							// 获取jar里的一个实体，可以是目录和一些jar包里的其他文件，如META-INF等文件
							JarEntry jarEntry = jarEntries.nextElement();
							String name = jarEntry.getName();
							// 如果以/开头的
							if (name.charAt(0) == '/') {
								name = name.substring(1);
							}

							// 如果前半部分和定义的包名相同
							if (name.startsWith(packageDirName)) {
								int idx = name.lastIndexOf('/');
								// 如果以'/'结尾是一个包
								if (idx != -1) {
									// 获取包名把'/'替换成'.'
									packageName = name.substring(0, idx).replace('/', '.');
								}

								// 如果可以迭代下去并且是一个包
								if (idx != -1 || isRecursive) {
									// 如果是一个.class文件而且不是目录
									if (name.endsWith(".class") && !jarEntry.isDirectory()) {
										// 去掉后面的".class"获取真的类名
										String className = name.substring(packageName.length() + 1, name.length() - 6);
										try {
											classes.add(Class.forName(packageName + "." + className));
										} catch (Exception e) {
											e.printStackTrace();
										}
									}
								}
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return classes;
	}

	// 以文件的形式来获取包下所有的class
	public static void findAndAddClassInPackageByFile(String packageName, String packagePath, boolean isRecursive,
			List<Class<?>> classes) {

		// 获取此包的目录建立一个File
		File dir = new File(packagePath);
		// 如果不存在或者不是目录直接返回
		if (!dir.exists() || !dir.isDirectory()) {
			return;
		}
		// 如果存在，就获取包下所有文件包括目录
		File[] dirFiles = dir.listFiles(new FileFilter() {
			// 自定义过滤规则 如果可以循环(包含子目录) 或者是以.class结尾的文件(编译好的java类文件)
			@Override
			public boolean accept(File file) {
				return (isRecursive && file.isDirectory()) || file.getName().endsWith(".class");
			}
		});

		for (File file : dirFiles) {
			// 如果是目录，则继续递归扫描
			if (file.isDirectory()) {
				findAndAddClassInPackageByFile(packageName + "." + file.getName(), file.getAbsolutePath(), isRecursive,
						classes);
			} else {
				// 如果是java类文件，去掉后面的.class只留下类名
				String className = file.getName().substring(0, file.getName().length() - 6);
				try {
					classes.add(Class.forName(packageName + "." + className));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

	}

	public static String toLowerCaseFirstOne(String s) {
		if (Character.isLowerCase(s.charAt(0))) {
			return s;
		} else {
			return (new StringBuilder())
					.append(Character.toLowerCase(s.charAt(0)))
					.append(s.substring(1))
					.toString();
		}
	}

	public static Object newInstance(Class<?> classInfo) throws Exception {
		return classInfo.newInstance();
	}

}
