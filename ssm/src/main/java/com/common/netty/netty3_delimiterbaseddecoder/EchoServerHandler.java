package com.common.netty.netty3_delimiterbaseddecoder;

import java.util.Date;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

//未考虑tcp粘包
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

	private int counter;

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		
		String body = (String)msg;
		System.out.println("The time server receive order:" + body + "; the counter is: " + (++counter));

		String currentTime = new Date().toString();
		currentTime = currentTime + "$_";//增加自定义分隔符
		ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
		ctx.writeAndFlush(resp);

	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.close();
	}

}
