package com.backup.server.config;

public interface RedisConfig {
    // 订阅主题
    String REDIS_SERVER_2_AGENT_TOPIC_PREFIX = "backup:2agent:";
    String REDIS_AGENT_2_SERVER_TOPIC = "backup:2server";
    String REDIS_BROADCAST_TOPIC = "backup:broadcast";

    // KEY
    String REDIS_AGENT_ONLINE_PREFIX = "backup:online:";
    String REDIS_AGENT_RUNNING_PREFIX = "backup:running:";

    // EVENT
    String REDIS_AGENT_ONLINE_EVENT = "backup:event:online";

    // KEY EXPIRE EVENT
    String REDIS_KEY_EXPIRE_EVENT = "__keyevent@*__:expired";

    String REDIS_MSG_TYPE_SCHEDULER = "scheduler";
    String REDIS_MSG_TYPE_RESOURCE = "resource";
    String REDIS_MSG_TYPE_CONFIG = "config";
}
