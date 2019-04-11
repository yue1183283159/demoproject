package com.redis.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

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
        byte[] channel = message.getChannel();
        //对于message反序列化，需要采用StringRedisSerializer序列化器，不然会不能反序列化channel
        StringRedisSerializer serializer = (StringRedisSerializer) redisTemplate.getDefaultSerializer();
        String msgBody = (String) serializer.deserialize(body);
        String msgChannel = (String) serializer.deserialize(channel);
        String msgPattern = new String(pattern);
        System.out.println("Message Pattern:" + msgPattern + ", Channel:" + msgChannel + ", Body:" + msgBody);

        //得到channel之后，根据channel类型，结合消息体中的数据，决定将消息发给哪些客户端
        //通过websocket将消息发送到对应的客户端

    }

}
