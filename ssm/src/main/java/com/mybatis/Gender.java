package com.mybatis;

public enum Gender {
	MALE(1, "男"), FEMALE(2, "女"), NONE(3, "无");

	private int code;
	private String name;

	private Gender(int code, String name) {
		this.code = code;
		this.name = name();
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static Gender valueOf(int code) {
		switch (code) {
		case 1:
			return MALE;
		case 2:
			return FEMALE;
		default:
			return NONE;
		}
	}
}
