package com.ssm.common.exception;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ExceptionHandle {

	// 增加异常日志打印
	private final static Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);
	// 设置异常错误页面
	public final static String DEFAULT_ERROR_VIEW = "error";
	
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public Object handle(HttpServletRequest request, Exception e) {
		if(e instanceof MyException) {
			MyException myException=(MyException)e;
			return ResultUtil.getError(myException.getCode(), myException.getMessage());
		}else {
			//系统异常
			logger.error("[系统异常]={}", e);
			return ResultUtil.getError(ResultEnum.SYSTEM_EXCEPTION.getCode(), ResultEnum.SYSTEM_EXCEPTION.getMsg());
		}
	}
	
	public boolean isAjax(HttpServletRequest request) {
		return (request.getHeader("X-Requested-With")!=null) && "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
	}
}
