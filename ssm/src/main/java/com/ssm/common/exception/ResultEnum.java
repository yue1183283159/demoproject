package com.ssm.common.exception;

public enum ResultEnum {
	SUCCESS(200, "sucess"), 
	SYSTEM_EXCEPTION(-1, "system exception"), 
	UNKNOWN_EXCEPTION(1, "unknown exception"),
	SERVICE_EXCEPTION(2, "service exception"),
	MY_EXCEPTION(3, "business exception"),
	INFO_EXCEPTION(4, "info exception"),
	DB_EXCEPTION(5,	"database exception"),
	PARAM_EXCEPTION(6, "parameter exception");

	private Integer code;
	private String msg;

	public Integer getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

	ResultEnum(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}
}
