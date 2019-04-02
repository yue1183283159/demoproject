package com.common.netty.netty3_delimiterbaseddecoder;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class EchoClientHandler extends ChannelInboundHandlerAdapter {

	private int counter;

	private final String REQ="Hi Netty.$_";

	public EchoClientHandler() {
		
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		
		for (int i = 0; i < 100; i++) {
			
			ctx.writeAndFlush(Unpooled.copiedBuffer(REQ.getBytes()));
		}
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		
		String body = (String)msg;
		System.out.println("Now is:" + body + ";the counter is :" + (++counter));
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.close();
		System.out.println(cause.getMessage());
	}

}
