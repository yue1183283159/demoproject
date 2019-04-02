package com.common.netty.netty3_privateprotocol;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class LoginAuthReqHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		ctx.writeAndFlush(buildLoginReq());
	}

	private Object buildLoginReq() {
		
		return null;
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		NettyMessage message=(NettyMessage)msg;
		//如果是握手应答消息，需要判断是否认证成功
		//if(message.getHeader()!=null && message.getHeader().getType()==MessageType.LOGIN_RESP.value()) {
			
		//}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// TODO Auto-generated method stub
		super.exceptionCaught(ctx, cause);
	}

}
