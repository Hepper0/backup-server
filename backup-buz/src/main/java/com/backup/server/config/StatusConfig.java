package com.backup.server.config;

public interface StatusConfig {

    Integer SERVER_STATUS_IDLE = 0;
    Integer SERVER_STATUS_RUNNING = 1;

    Integer SERVER_ONLINE = 1;
    Integer SERVER_OFFLINE = 0;

    Long TASK_RESULT_RUNNING = 0L;
    Long TASK_RESULT_SUCCESS = 1L;
    Long TASK_RESULT_FAILURE = -1L;
}
