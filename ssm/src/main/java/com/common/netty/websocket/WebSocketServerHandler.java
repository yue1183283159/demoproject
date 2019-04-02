package com.common.netty.websocket;

import java.util.Date;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
import io.netty.util.CharsetUtil;

public class WebSocketServerHandler extends ChannelInboundHandlerAdapter {

	private WebSocketServerHandshaker handshaker = null;

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// 传统http接入
		if (msg instanceof FullHttpRequest) {
			handleHttpRequest(ctx, (FullHttpRequest) msg);
		} else if (msg instanceof WebSocketFrame) {// websocket 接入

			handleWebSocketFrame(ctx, (WebSocketFrame) msg);
		}

	}

	private void handleWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) {
		// 判断是否是关闭链路的指令
		if (frame instanceof CloseWebSocketFrame) {
			handshaker.close(ctx.channel(), (CloseWebSocketFrame) frame.retain());
			return;
		}

		// 判断是否是Ping消息
		if (frame instanceof PingWebSocketFrame) {
			ctx.channel().write(new PongWebSocketFrame(frame.content().retain()));
			return;
		}

		// 只支持文本消息，不支持二进制消息
		if (!(frame instanceof TextWebSocketFrame)) {
			throw new UnsupportedOperationException(
					String.format("%s frame types not supported", frame.getClass().getName()));
		}

		// 返回应答消息
		String request = ((TextWebSocketFrame) frame).text();
		System.out.println(String.format("%s received %s", ctx.channel(), request));
		ctx.channel().write(request + " , welcome use netty websocket service, now time is:" + new Date().toString());

	}

	private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest req) {
		// 如果http解码失败，返回http异常
		if (!req.getDecoderResult().isSuccess() || (!"websocket".equals(req.headers().get("Upgrade")))) {
			sendHttpResponse(ctx, req,
					new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
			return;
		}

		// 构造握手响应返回。本机测试
		WebSocketServerHandshakerFactory wServerHandshakerFactory = new WebSocketServerHandshakerFactory(
				"ws://localhost:8080/websocket", null, false);
		handshaker = wServerHandshakerFactory.newHandshaker(req);
		if (handshaker == null) {
			WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
		} else {
			handshaker.handshake(ctx.channel(), req);
		}
	}

	private void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest req,
			DefaultFullHttpResponse defaultFullHttpResponse) {

		FullHttpResponse response = (FullHttpResponse) defaultFullHttpResponse;
		// 返回应答给客户端
		if (response.status().code() != 200) {
			ByteBuf buf = Unpooled.copiedBuffer(response.status().toString(), CharsetUtil.UTF_8);
			response.content().writeBytes(buf);
			buf.release();
			setContentLength(response, response.content().readableBytes());
		}

		// 如果是非keep-alive，关闭连接
		ChannelFuture future = ctx.channel().writeAndFlush(response);
		if (!isKeepAlive(req) || response.status().code() != 200) {
			future.addListener(ChannelFutureListener.CLOSE);
		}

	}

	private boolean isKeepAlive(FullHttpRequest req) {
	
		return false;
	}

	private void setContentLength(FullHttpResponse response, int readableBytes) {
		

	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

	}

}
