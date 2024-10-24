package com.backup.server.service;


import com.backup.server.core.domain.AgentInfo;

import java.util.List;

public interface IAgentService {

    public List<AgentInfo> getAgentList();

    public void sendMessage2Agent();

    public void handleReceiveMessage();

}
