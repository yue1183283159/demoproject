package com.redis.component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class WebSocketHandlerEx extends TextWebSocketHandler {

	// 在线用户列表
	private static final Map<String, WebSocketSession> users;

	private static final String CLIENT_ID = "staff_id";

	static {
		users = new HashMap<>();
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		System.out.println("connection closed....");
		users.remove(getClientId(session));
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {

		System.out.println("websocket connection successfull...");
		String username = getClientId(session);
		if (username != null) {
			users.put(username, session);
			session.sendMessage(new TextMessage("ok"));
		}
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		System.out.println(message.getPayload());
		WebSocketMessage message2 = new TextMessage("server:" + message);
		try {
			session.sendMessage(message2);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		if (session.isOpen()) {
			session.close();
		}

		System.out.println("connected error.");
		users.remove(getClientId(session));
	}

	@Override
	public boolean supportsPartialMessages() {
		return false;
	}

	/**
	 *将消息发给指定的用户
	 **/
	public boolean sendMessageToUser(String clientId, String message) {
		if (users.get(clientId) == null) {
			return false;
		}

		WebSocketSession session = users.get(clientId);
		if (!session.isOpen()) {
			return false;
		}

		TextMessage messageBoy = new TextMessage(message);
		try {
			session.sendMessage(messageBoy);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	/**
	 * 将消息发给所有用户
	 */
	public boolean sendMessageToAllUser(String message) {
		boolean allSendSuccess = true;
		TextMessage messageBody = new TextMessage(message);
		Set<String> clientIds = users.keySet();
		WebSocketSession session = null;
		for (String clientId : clientIds) {
			try {
				session = users.get(clientId);
				if (session.isOpen()) {
					session.sendMessage(messageBody);
				}
			} catch (Exception e) {
				e.printStackTrace();
				allSendSuccess = false;
			}
		}

		return allSendSuccess;
	}

	private String getClientId(WebSocketSession session) {
		try {
			String clientId = String.valueOf(session.getAttributes().get(CLIENT_ID));
			return clientId;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
