package com.ssm.common.interceptor;

import java.lang.reflect.Method;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 利用Spring MVC拦截器及token传递验证来实现防止表单重复提交
 *
 **/
public class TokenInterceptor extends HandlerInterceptorAdapter {

	private Logger logger = Logger.getLogger(TokenInterceptor.class);

	private static final String TOKEN = "token";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (handler instanceof HandlerMethod) {
			Method method = ((HandlerMethod) handler).getMethod();
			Token tokenAnnotation = method.getAnnotation(Token.class);
			if (tokenAnnotation != null) {
				HttpSession session = request.getSession();
				// 创建token
				boolean create = tokenAnnotation.create();
				if (create) {
					session.setAttribute(TOKEN, UUID.randomUUID().toString());
					return true;
				}

				boolean remove = tokenAnnotation.remove();
				if (remove) {
					if (isRepeatSubmit(request)) {
						logger.warn("表单不能重复提交：" + request.getRequestURI());
						return false;
					}
					session.removeAttribute(TOKEN);
				}
			}
		} else {
			super.preHandle(request, response, handler);
		}

		return true;
	}

	private boolean isRepeatSubmit(HttpServletRequest request) {
		String token = (String) request.getSession().getAttribute(TOKEN);
		if (token == null) {
			return true;
		}

		String reqToken = request.getParameter(TOKEN);
		if (reqToken == null) {
			return true;
		}

		//前后两次token不一致，就是重复提交
		if (!token.equals(reqToken)) {
			return true;
		}

		return false;
	}

}
