package com.backup.server.core.domain;

import com.backup.server.core.MessageHandler;

public class AgentInfo {
    int id;
    String ip;
    String hostName;
    String status;
    AgentTaskInfo agentTaskInfo;
    MessageHandler messageHandler;
}
