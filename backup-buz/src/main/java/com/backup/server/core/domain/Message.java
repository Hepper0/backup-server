package com.backup.server.core.domain;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

// 消息报的外层数据
@Data
public class Message {
    String ip;
    String uuid;
    int msgType; // 0 push 1 request 2 response
    Object payload;

    public String toString() {
        JSONObject json = new JSONObject();
        json.put("ip", getIp());
        json.put("uuid", getUuid());
        json.put("msgType", getMsgType());
        json.put("payload", getPayload());
        return json.toString();
    }

    // 如果是响应数据, 直接获取返回数据
    public Object getData() {
        BuzMessage buzMessage = new BuzMessage((JSONObject) payload);
        return buzMessage.getData();
    }
}
