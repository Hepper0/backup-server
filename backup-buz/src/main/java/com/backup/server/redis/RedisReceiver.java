package com.backup.server.redis;

import com.alibaba.fastjson2.JSONObject;
import com.backup.common.core.redis.RedisCache;
import com.backup.server.config.RedisConfig;
import com.backup.server.core.MessageHandler;
import com.backup.server.core.RedisMessageHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class RedisReceiver implements MessageListener {

    @Resource
    RedisMessageHandler messageHandler;

    @Value("${spring.redis.database}")
    private String redisDatabase;

    public void onMessage(Message message, byte[] pattern) {
        String event = new String(message.getChannel());
        String data = new String(message.getBody());
        System.out.println("Received <" + event + ">" + data);
        // key失效处理: 目前处理两种情况: 1 Agent online  2. task running
        if (event.equals(RedisConfig.REDIS_KEY_EXPIRE_EVENT.replace("*", redisDatabase))) {
            messageHandler.handleExpiredKey(data);
        } else {
            messageHandler.recv(data);
        }

    }

}
