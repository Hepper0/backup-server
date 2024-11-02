package com.backup.server.core.domain;

import com.alibaba.fastjson2.JSONObject;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

// 消息报的业务层数据
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

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("eventType", getEventType());
        map.put("data", getData());
        return map;
    }
}
