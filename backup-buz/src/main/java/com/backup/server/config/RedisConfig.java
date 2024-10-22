package com.backup.server.config;

public interface RedisConfig {
    // 订阅主题
    String REDIS_SERVER_2_AGENT_TOPIC_PREFIX = "backup:2agent:";
    String REDIS_AGENT_2_SERVER_TOPIC_PREFIX = "backup:2server:";
    String REDIS_BROADCAST_TOPIC = "backup:broadcast";

    // KEY
    String REDIS_AGENT_ONLINE_PREFIX = "backup:online:";

    // EVENT
    String REDIS_AGENT_ONLINE_EVENT = "backup:event:online";
}
