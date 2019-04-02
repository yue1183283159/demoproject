package com.common.netty.netty3_privateprotocol;

import io.netty.buffer.ByteBuf;

public class MarshallingEncoder {
	
	private static final byte[] LENGTH_PLACEHOLDER=new byte[4];
	
	//Marshaller marshaller;
	
	public MarshallingEncoder() {
		
	}
	
	protected void encode(Object msg, ByteBuf out) {
		
	}
}
