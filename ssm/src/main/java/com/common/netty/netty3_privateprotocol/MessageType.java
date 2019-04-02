package com.common.netty.netty3_privateprotocol;

public enum MessageType {
	LOGIN_RESP;

	public static MessageType value(String value) {

		return valueOf(value);
	}
}
