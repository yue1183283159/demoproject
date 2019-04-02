package com.minimybatis;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.dom4j.*;
import org.dom4j.io.SAXReader;

//SqlMapperHolder用来读取mapper的信息，使用枚举单例实现
public enum SqlMapperHolder {
	INSTANCE;
	private Map<String, MapperInfo> mapInfo = null;

	// 省略了private。
	SqlMapperHolder() {
		if (mapInfo != null) {
			return;
		}

		mapInfo = new HashMap<>();
		File dir = new File(
				SqlMapperHolder.class.getClassLoader().getResource(Config.DEFAULT.getMapperPath()).getFile());
		// 用dom4j解析mapper文件
		SAXReader reader = new SAXReader();
		try {
			for (String file : dir.list()) {
				if (!file.endsWith("xml")) {
					continue;
				}
				Document document = reader.read(new File(dir, file));
				Element root = document.getRootElement();
				String className = root.attributeValue("namespace");
				for (Object obj : root.elements()) {
					Element element = (Element) obj;
					MapperInfo info = new MapperInfo();
					info.setQueryType(QueryType.value(element.getName()));// select,update,delete,update
					info.setInterfaceName(className);
					info.setMethodName(element.attributeValue("id"));
					info.setResultType(element.attributeValue("resultType"));
					info.setSql(element.getText());

					mapInfo.put(idOf(className, element.attributeValue("id")), info);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public MapperInfo getMapperInfo(String className, String methodName) {
		return mapInfo.get(idOf(className, methodName));
	}

	private String idOf(String className, String methodName) {
		return className + "." + methodName;
	}
}
