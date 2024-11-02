package com.backup.server.redis;

import com.backup.server.config.RedisConfig;
import com.backup.server.core.RedisMessageHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component
public class RedisReceiver implements MessageListener {

    @Resource
    RedisMessageHandler messageHandler;

    @Value("${spring.redis.database}")
    private String redisDatabase;

    public void onMessage(Message message, byte[] pattern) {
        String event = new String(message.getChannel());
        String data = new String(message.getBody());
        log.info("Received <" + event + ">" + data);
        // key失效处理: 目前处理两种情况: 1 Agent online  2. task running
        if (event.equals(RedisConfig.REDIS_KEY_EXPIRE_EVENT.replace("*", redisDatabase))) {
            messageHandler.handleExpiredKey(data);
        } else {
            // 常规消息处理入口
            messageHandler.handleMessage(data);
        }

    }

}
