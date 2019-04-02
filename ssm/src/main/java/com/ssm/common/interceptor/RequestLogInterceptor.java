package com.ssm.common.interceptor;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.EnumerationUtils;
import org.omg.CORBA.StringHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssm.pojo.RequestLog;
import com.ssm.service.RequestLogService;

/**
 * 记录请求，参数，以及执行的时间
 **/
public class RequestLogInterceptor implements HandlerInterceptor {

	private final static String REQUEST_START = "request_starttime";

	@Autowired
	private RequestLogService requestLogService;

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception exception) throws Exception {

	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView arg3)
			throws Exception {

		long endTime = System.currentTimeMillis();
		long startTime = (long) request.getAttribute(REQUEST_START);
		long elapsedTime = endTime - startTime;

		String url = request.getRequestURI();
		String paramJson = null;

		Enumeration<String> keys = request.getParameterNames();
		Map<String, String> dataMap = new HashMap<>();

		while (keys.hasMoreElements()) {
			String key = (String) keys.nextElement();
			dataMap.put(key, request.getParameter(key));
		}
		if (dataMap.size() > 0) {
			ObjectMapper objectMapper = new ObjectMapper();
			paramJson = objectMapper.writeValueAsString(dataMap);
		}

		RequestLog log = new RequestLog();
		log.setUrl(url);
		log.setData(paramJson);
		log.setElapsedTime((int)elapsedTime);

		requestLogService.saveRequestLog(log);
	}

	/**
	 * 在Controller方法执行之前进行拦截。 只记录开始时间，不做其他处理
	 **/
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		long startTime = System.currentTimeMillis();
		//将开始时间存入request域中，然后当请求执行完返回之后，从postHandle中取出时间，计算整个请求执行的时间。
		request.setAttribute(REQUEST_START, startTime);
		return true;// 不管怎么样都放行
	}

}
