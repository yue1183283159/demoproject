package com.ssm.common.interceptor;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *在跳转页面的方法上加@Token(create=true)
 *在提交的action方法上加@Token(remove=true) 
 * 
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Token {
	boolean create() default false;

	boolean remove() default false;

}
