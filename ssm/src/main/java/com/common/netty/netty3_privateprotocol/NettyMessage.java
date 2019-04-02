package com.common.netty.netty3_privateprotocol;

/**
 *Netty协议栈使用到的数据结构进行定义
 *心跳消息，握手请求，握手应答都使用NettyMessage承载 
 * 
 */
public final class NettyMessage {
	private Header header;
	private Object body;

	public final Header getHeader() {
		return header;
	}

	public final void setHeader(Header header) {
		this.header = header;
	}

	public final Object getBody() {
		return body;
	}

	public final void setBody(Object body) {
		this.body = body;
	}

	@Override
	public String toString() {
		return "NettyMessage [header=" + header + "]";
	}

}
