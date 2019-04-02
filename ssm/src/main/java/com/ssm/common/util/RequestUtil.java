package com.ssm.common.util;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

/**
 * Request工具类
 */
public class RequestUtil {

	// 移除指定的参数
	public static String removeParam(HttpServletRequest request, String paramName) {
		String queryString = "";
		Enumeration<String> keys = request.getParameterNames();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			if (key.equals(paramName)) {
				continue;
			}
			if ("".equals(queryString)) {
				queryString = key + "=" + request.getParameter(key);
			} else {
				queryString += "&" + key + "=" + request.getParameter(key);
			}
		}

		return queryString;

	}

	public static String getBasePath(HttpServletRequest request) {
		StringBuffer basePath = new StringBuffer();
		String scheme = request.getScheme();
		String domain = request.getServerName();
		int port = request.getServerPort();
		basePath.append(scheme);
		basePath.append("://");
		basePath.append(domain);
		if ("http".equalsIgnoreCase(scheme) && 80 != port) {
			basePath.append(":").append(String.valueOf(port));
		} else if ("https".equalsIgnoreCase(scheme) && 443 != port) {
			basePath.append(":").append(String.valueOf(port));
		}

		return basePath.toString();

	}
}
