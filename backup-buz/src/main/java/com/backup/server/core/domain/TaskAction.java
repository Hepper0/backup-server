package com.backup.server.core.domain;

import lombok.Data;

@Data
public class TaskAction {
    String action;
    String serverId;
    String azId;
    String backupDir;
}
