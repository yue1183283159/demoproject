package com.redis.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;

public class RedisMessageListener implements MessageListener {

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	public RedisTemplate<String, Object> getRedisTemplate() {
		return redisTemplate;
	}

	public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	@Override
	public void onMessage(Message message, byte[] pattern) {
		byte[] body = message.getBody();
		String msgBody = (String) getRedisTemplate().getValueSerializer().deserialize(body);
		System.out.println(msgBody);
		byte[] channel = message.getChannel();
		String msgChannel = (String) getRedisTemplate().getValueSerializer().deserialize(channel);
		System.out.println(msgChannel);
		String msgPattern = new String(pattern);
		System.out.println(msgPattern);

		//通过websocket将消息发送到对应的客户端
		
	}

}
