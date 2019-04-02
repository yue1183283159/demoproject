package com.ssm.common.util;

import java.util.List;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Jackson json序列化和反序列工具类
 */
public class JsonUtil {
	private static final ObjectMapper mapper = new ObjectMapper();

	public static String toJson(Object data) {
		try {
			String jsonStr = mapper.writeValueAsString(data);
			return jsonStr;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static <T> T toPojo(String jsonStr, Class<T> beanType) {
		try {
			T t = mapper.readValue(jsonStr, beanType);
			return t;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public static <T> List<T> toList(String jsonStr, Class<T> beanType) {
		JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, beanType);
		try {
			List<T> list = mapper.readValue(jsonStr, javaType);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

}
