package com.backup.server.domain;

import lombok.Data;

import java.util.List;

@Data
public class BkSchedulerBroadcast {

    List<String> ipList;
    BkScheduler bkScheduler;
}
