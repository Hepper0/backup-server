package com.backup.server.core.domain;

import com.alibaba.fastjson2.JSONObject;
import lombok.Data;

@Data
public class BuzMessage {
    String eventType;
    Object data;

    public BuzMessage(JSONObject msg) {
        eventType = msg.getString("eventType");
        data = msg.get("data");
    }

    public BuzMessage(String eventType, Object data) {
        this.eventType = eventType;
        this.data = data;
    }
}
