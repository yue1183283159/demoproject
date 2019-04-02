package com.reflect.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//annotation

//指定注解加在方法上
@Target(ElementType.METHOD)
//设置注解的有效期是运行时
@Retention(RetentionPolicy.RUNTIME)
public @interface Test
{

}
