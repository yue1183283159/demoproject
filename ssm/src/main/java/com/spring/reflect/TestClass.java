package com.spring.reflect;

@CETypeAnnotation("test")
public class TestClass {
	@CEFieldAnnotation(value = "姓名")
	public String name = "sa";

	@CEConstructorAnnotation("构造函数注解")
	public TestClass() {
		System.out.println("Init TestClass");
	}

	@CEMethodAnnotation(description = "hello method")
	public void hello(@CEParameterAnnotation("ids") String idString) {
		System.out.println("invoke hello method.");
		System.out.println("ids : " + idString);
	}
}
