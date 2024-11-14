package com.backup.server.core;

import com.alibaba.fastjson2.JSONObject;
import com.backup.common.core.redis.RedisCache;
import com.backup.common.utils.uuid.IdUtils;
import com.backup.server.config.RedisConfig;
import com.backup.server.core.domain.BuzMessage;
import com.backup.server.core.domain.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

// 抽象消息发送与处理逻辑
@Slf4j
public class MessageHandler {

    public static final int MESSAGE_TYPE_PUSH = 0; // default
    public static final int MESSAGE_TYPE_REQUEST = 1;
    public static final int MESSAGE_TYPE_RESPONSE = 2;

    private final String ip;
    private String uuid;
    private Long timeout = 10000L;
    private CountDownLatch countDownLatch;
    private Message responseMessage;
    public BlockingQueue<Object> pendingMessage = new LinkedBlockingQueue<>();
    private RedisCache sender;

    private static final Map<String, MessageHandler> agentReceiverMap = new HashMap<>();
    public static final ConcurrentHashMap<String, MessageHandler> MSG_MAP = new ConcurrentHashMap<>();


    public MessageHandler(String ip) {
        this.ip = ip;
        MessageHandler oldHandler = agentReceiverMap.get(ip);
        if (oldHandler != null) {
            oldHandler.teardown();
        } else {
            agentReceiverMap.put(ip, this);
        }
    }

    public MessageHandler(String ip, RedisCache redisCache) {
        this(ip);
        this.sender = redisCache;
    }

    public void teardown() {
        try {
            pendingMessage.put(-1);
        } catch (Exception ignored) {}
        agentReceiverMap.remove(ip);
        log.info("{} teardown", ip);
    }

    public void push(Object data) {
        Message message = createMessage(data);
        sendMessage(message);
    }

    public void response(Object data) {
        Message message = createResponseMessage(data);
        sendMessage(message);
    }

    public Object takeMessage() throws Exception {
        return pendingMessage.take();
    }

    public Message request(Object data) {
        countDownLatch = new CountDownLatch(1);
        Message message = createRequestMessage(data);
        try {
            MSG_MAP.put(message.getUuid(), this);
            sendMessage(message);
            countDownLatch.await(timeout, TimeUnit.MILLISECONDS);
            countDownLatch = null;
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage = null;
        } finally {
            MSG_MAP.remove(message.getUuid());
        }
        return responseMessage;
    }

    private void sendMessage(Message message) {
        sender.publish(getAgentTopic(), message.toString());
    }

    public void handleResponseMessage(Message message) {
        Assert.notNull(countDownLatch);
        this.responseMessage = message;
        countDownLatch.countDown();
    }

    public void handleMessage(Message message) {
        try {
            pendingMessage.put(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Message createMessage(Object payload) {
        uuid = IdUtils.simpleUUID();
        Message message = new Message();
        message.setIp(ip);
        message.setUuid(uuid);
        message.setPayload(payload);
        return message;
    }

    public Message createPushMessage(Object data) {
        Message message = createMessage(data);
        message.setMsgType(MESSAGE_TYPE_PUSH);
        return message;
    }

    public Message createRequestMessage(Object data) {
        Message message = createMessage(data);
        message.setMsgType(MESSAGE_TYPE_REQUEST);
        return message;
    }

    public Message createResponseMessage(Object data) {
        Message message = createMessage(data);
        message.setMsgType(MESSAGE_TYPE_RESPONSE);
        return message;
    }

    public static MessageHandler getHandler(String ip) {
        return agentReceiverMap.get(ip);
    }

    public static void broadcast(String eventType, Object data, List<String> ipList) {
        BuzMessage payload = new BuzMessage(eventType, data);
        for (String ip : ipList) {
            MessageHandler handler = getHandler(ip);
            if (handler != null) {
                handler.push(payload);
            } else {
                log.warn("The handler of {} is not found!", ip);
            }
        }
    }

    public static Object request(String eventType, Object data, String ip) {
        BuzMessage payload = new BuzMessage(eventType, data);
        MessageHandler handler = getHandler(ip);
        if (handler != null) {
            Message respMsg = handler.request(payload);
            return respMsg.getData();
        } else {
            throw new RuntimeException("handler of "+ ip + " is not found");
        }
    }

    public static void push(String eventType, Object data, String ip) {
        BuzMessage payload = new BuzMessage(eventType, data);
        MessageHandler handler = getHandler(ip);
        if (handler != null) {
            handler.push(payload);
        } else {
            throw new RuntimeException("handler of "+ ip + " is not found");
        }
    }

    public void setSender(RedisCache redisCache) {
        this.sender = redisCache;
    }

    private String getAgentTopic() {
        return RedisConfig.REDIS_SERVER_2_AGENT_TOPIC_PREFIX + ip;
    }

}
