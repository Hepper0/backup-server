package com.backup.server.service.impl;

import com.backup.common.core.redis.RedisCache;
import com.backup.server.core.MessageHandler;
import com.backup.server.core.domain.AgentInfo;
import com.backup.server.service.IAgentService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class AgentServiceImpl implements IAgentService {

    @Resource
    RedisCache redisCache;

    @PostConstruct
    public void init() {
        System.out.println("service init");
    }

    @Override
    public List<AgentInfo> getAgentList() {
        return null;
    }

    @Override
    public void sendMessage2Agent() {

    }

    @Override
    public void handleReceiveMessage() {

    }
}
