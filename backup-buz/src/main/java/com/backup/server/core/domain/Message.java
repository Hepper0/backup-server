package com.backup.server.core.domain;

import lombok.Data;

@Data
public class Message {
    String ip;
    String uuid;
    int type; // 0 push 1 request 2 response
    Object payload;

//    public String toString() {
//        return String.format("{\"ip\": \"%s\", \"uuid\": \"%s\", \"type\":%s, \"data\": %s}", ip, uuid, type, data.toString());
//    }
}
