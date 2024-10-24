package com.backup.server.redis;


import com.backup.server.config.RedisConfig;
import com.backup.server.core.MessageHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import javax.annotation.Resource;
import java.util.Arrays;

@Configuration
public class RedisListenerConfig {

    @Resource
    private RedisConnectionFactory redisConnectionFactory;

    @Resource
    private RedisReceiver receiver;

    @Bean
    RedisMessageListenerContainer redisContainer(MessageListenerAdapter messageListenerAdapter) {
        System.out.println("RedisMessageListenerContainer bean");
        final RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);
        container.addMessageListener(messageListenerAdapter, Arrays.asList(
                new ChannelTopic(RedisConfig.REDIS_AGENT_2_SERVER_TOPIC),  // 全称匹配
                new PatternTopic(RedisConfig.REDIS_KEY_EXPIRE_EVENT) // 正则匹配
        ));
        return container;
    }

    @Bean
    MessageListenerAdapter messageListenerAdapter() {
        System.out.println("MessageListenerAdapter bean");
        return new MessageListenerAdapter(receiver, "onMessage");
    }

}
