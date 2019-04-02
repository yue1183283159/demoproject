package com.common.netty.netty3_linebasedecoder;

import java.util.Date;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

//未考虑tcp粘包
public class TimeServerHandler extends ChannelInboundHandlerAdapter {

	private int counter;

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf buf = (ByteBuf) msg;
		byte[] req = new byte[buf.readableBytes()];
		buf.readBytes(req);
		String body = new String(req, "UTF-8").substring(0, req.length - System.getProperty("line.separator").length());
		System.out.println("The time server receive order:" + body + "; the counter is: " + (++counter));

		String currentTime = new Date().toString();
		currentTime = currentTime + System.getProperty("line.separator");//增加回车换行符
		ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
		ctx.writeAndFlush(resp);

	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.close();
	}

}
