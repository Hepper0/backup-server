package com.backup.server.core;

import com.alibaba.fastjson2.JSONObject;
import com.backup.common.core.redis.RedisCache;
import com.backup.common.utils.uuid.IdUtils;
import com.backup.server.config.RedisConfig;
import com.backup.server.core.domain.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

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

    public void push(String data) {
        Message message = createMessage(data);
        sendMessage(message);
    }

    public void response(String data) {
        Message message = createMessage(data, MESSAGE_TYPE_RESPONSE);
        sendMessage(message);
    }

    public Object takeMessage() throws Exception {
        return pendingMessage.take();
    }

    public Message request(String data) {
        countDownLatch = new CountDownLatch(1);
        Message message = createMessage(data, MESSAGE_TYPE_REQUEST);
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

    public static void recv(String msg) {
        MessageHandler handler;
        JSONObject msgJson = JSONObject.parseObject(msg);
        Message message = new Message();
        String ip = msgJson.getString("ip");
        int messageType = msgJson.getInteger("type");
        message.setIp(ip);
        message.setType(messageType);
        message.setData(msgJson.getJSONObject("data"));
        if (messageType == MESSAGE_TYPE_RESPONSE) {
            String uuid = msgJson.getString("uuid");
            handler = MSG_MAP.get(uuid);
            if (handler == null) {
                throw new RuntimeException("Agent message handler is not found!");
            }
            handler.handleResponseMessage(message);
        } else {
            handler = agentReceiverMap.get(ip);
            if (handler == null) {
                handler = new MessageHandler(ip);
            }
            handler.handleMessage(message);
        }
    }

    public Message createMessage(String data) {
        uuid = IdUtils.simpleUUID();
        Message message = new Message();
        message.setIp(ip);
        message.setUuid(uuid);
        message.setData(data);
        return message;
    }

    public Message createMessage(String data, int type) {
        Message message = createMessage(data);
        message.setType(type);
        return message;
    }

    public static MessageHandler getHandler(String ip) {
        return agentReceiverMap.get(ip);
    }

    public void setSender(RedisCache redisCache) {
        this.sender = redisCache;
    }

    private String getAgentTopic() {
        return RedisConfig.REDIS_SERVER_2_AGENT_TOPIC_PREFIX + ip;
    }

}
