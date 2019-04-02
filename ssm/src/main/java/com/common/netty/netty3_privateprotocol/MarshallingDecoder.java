package com.common.netty.netty3_privateprotocol;

import io.netty.buffer.ByteBuf;

public class MarshallingDecoder {
	protected Object decode(ByteBuf in) {
		int objectSize=in.readInt();
		ByteBuf buf=in.slice(in.readerIndex(), objectSize);
		//ByteInput input=new ChannelBufferByteInput(buf);
		return null;
	}
}
