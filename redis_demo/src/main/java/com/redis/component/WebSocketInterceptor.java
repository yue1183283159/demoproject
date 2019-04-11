package com.redis.component;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

//记录用户标识，便于后面向特定用户发送消息
@Component
public class WebSocketInterceptor implements HandshakeInterceptor{

	@Override
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler handler, Exception arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler handler,
			Map<String, Object> map) throws Exception {
		
		if (request instanceof ServletServerHttpRequest ) {
			ServletServerHttpRequest serverHttpRequest=(ServletServerHttpRequest)request;
			HttpSession session=serverHttpRequest.getServletRequest().getSession();
			if(session!=null) {
				map.put("staff_id", "1");
			}
		}
		
		return true;
	}

}
