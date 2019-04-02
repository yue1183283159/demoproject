package com.minimybatis;

public enum QueryType {
	SELECT, UPDATE, INSERT, DELETE;
	
	public static QueryType value(String value) {
		return valueOf(value.toUpperCase());
	}

}
