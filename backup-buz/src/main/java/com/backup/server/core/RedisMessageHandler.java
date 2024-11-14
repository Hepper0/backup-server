package com.backup.server.core;

import com.alibaba.fastjson2.JSONObject;
import com.backup.common.core.redis.RedisCache;
import com.backup.server.config.RedisConfig;
import com.backup.server.core.domain.BuzMessage;
import com.backup.server.core.domain.Message;
import com.backup.server.domain.BkAgent;
import com.backup.server.domain.BkTask;
import com.backup.server.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Collection;

// 实际的消息处理逻辑
@Slf4j
@Component
public class RedisMessageHandler {

    @Resource
    RedisCache redisCache;

    @Resource
    IBkAgentService agentService;

    @Resource
    IBkTaskService taskService;

    @Resource
    IBkAgentConfigService configService;

    @Resource
    IBkSchedulerService schedulerService;

    @Resource
    IBkAgentResourceService agentResourceService;

    @PostConstruct
    public void init() {
        Collection<String> onlineAgentKeys = redisCache.keys(RedisConfig.REDIS_AGENT_ONLINE_PREFIX + "*");
        for (String key : onlineAgentKeys){
            String[] keyArr = key.split(":");
            String ip = keyArr[2];
            getMessageHandler(ip);
        }
    }

    // 处理Redis订阅的消息
    public synchronized void handleMessage(String msg) {
        MessageHandler handler;
        JSONObject msgJson = JSONObject.parseObject(msg);
        com.backup.server.core.domain.Message message = new com.backup.server.core.domain.Message();
        String ip = msgJson.getString("ip");
        int messageType = msgJson.getInteger("msgType");
        message.setIp(ip);
        message.setMsgType(messageType);
        message.setPayload(msgJson.getJSONObject("payload"));
        // JAVA端向Agent发送消息后的响应消息
        if (messageType == MessageHandler.MESSAGE_TYPE_RESPONSE) {
            String uuid = msgJson.getString("uuid");
            handler = MessageHandler.MSG_MAP.get(uuid);
            if (handler == null) {
                throw new RuntimeException("Agent message handler is not found!");
            }
            handler.handleResponseMessage(message);
        } else {
            // Agent主动向JAVA端推送的消息
            handler = getMessageHandler(ip);
            handler.handleMessage(message);
        }
    }

    public MessageHandler getMessageHandler(String ip) {
        MessageHandler handler = MessageHandler.getHandler(ip);
        if (handler == null) {
            handler = new MessageHandler(ip, redisCache);
            listen(ip);
        }
        return handler;
    }

    // 监听Agent主动向JAVA端推送的消息
    public void listen(String ip) {
        // 每个handler需要一个线程阻塞等待Agent的消息, 并且可以调用到Service
        new Thread(() -> {
            while(true) {
                try {
                    MessageHandler messageHandler = MessageHandler.getHandler(ip);
                    Object msg = messageHandler.takeMessage();
                    log.info("收到消息: {}", msg.toString());
                    if (msg instanceof Message) {
                        Message message = (Message)msg;
                        if (message.getMsgType() == MessageHandler.MESSAGE_TYPE_REQUEST) {
                            Object respObj = handleRequest(message);
                            log.info("回复消息: {}", respObj);
                            messageHandler.response(respObj);
                        } else if (((Message) msg).getMsgType() == MessageHandler.MESSAGE_TYPE_PUSH) {
                            handlePush(ip, msg);
                        }
                    } else {
                        // 收到非格式化的消息都认为是退出
                        break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            log.info("{} 消息接收线程退出", ip);
        }).start();
    }

    public void handleExpiredKey (String key) {
        String[] keyArr = key.split(":");
        String eventType = keyArr[1];
        String ip = keyArr[2];
        MessageHandler messageHandler = MessageHandler.getHandler(ip);
        switch (eventType) {
            case "online":
                messageHandler.teardown();
                break;
            case "running":
                String taskId;
                if (keyArr.length > 3) {
                    taskId = keyArr[3];
                    BkTask task = taskService.selectBkTaskByTaskId(taskId);
                    // 任务未正常结束, 但是运行状态中止了
                    if (task != null && task.getStatus() == 0) {
                        log.info("task " + taskId + " aborts unexpectedly");
                        task.setStatus(-1L);
                        task.setRemark("Aborts unexpectedly");
                        taskService.updateBkTask(task);
                    }
                } else {
                    throw new RuntimeException("Lack of the device id");
                }
                break;
        }
    }

    // 处理收到的请求逻辑
    public Object handleRequest(Object message) {
        Message msg = (Message)message;
        String ip = msg.getIp();
        JSONObject json = (JSONObject) msg.getPayload();
        BuzMessage buzMessage = new BuzMessage(json);
        switch (buzMessage.getEventType()) {
            case "config":
                buzMessage.setData(configService.selectAvailableBkAgentConfig().toMap());
                return buzMessage.toMap();
            case "scheduler":
                buzMessage.setData(schedulerService.selectBkSchedulerByIP(ip).toMap());
                return buzMessage.toMap();
            case "resource":
                buzMessage.setData(agentResourceService.selectBkAgentResourceListByIP(ip));
                return buzMessage.toMap();
        }
        return null;
    }

    // 处理收到的推送消息
    public void handlePush(String ip, Object msg) {
        /*
        1. Agent 空间不足
        2. 任务完成
        3. Agent启动
        */
        JSONObject data = (JSONObject) ((Message) msg).getPayload();
        BuzMessage buzMessage = new BuzMessage(data);
        String eventType = buzMessage.getEventType();
        JSONObject eventData = (JSONObject)buzMessage.getData();
        switch (eventType) {
            // Agent上线
            case "agentInfo":
                BkAgent agent = agentService.selectBkAgentByAgentIP(ip);
                if (agent == null) {
                    BkAgent newAgent = new BkAgent();
                    newAgent.setIp(ip);
                    newAgent.setHostname(eventData.getString("hostname"));
                    agentService.insertBkAgent(newAgent);
                }
                break;
            // Task 运行消息同步
            case "taskInfo":
                String taskId = eventData.getString("taskId");
                BkTask task = taskService.selectBkTaskByTaskId(taskId);
                if (task == null) {
                    task = new BkTask();
                    task.setTaskId(taskId);
                    task.setAgentIP(ip);
                    task.setBackupPath(eventData.getString("backupPath"));
                    task.setTarget(eventData.getString("target"));
                    task.setStartTime(eventData.getDate("startTime"));
                    task.setEndTime(eventData.getDate("endTime"));
                    task.setStatus(eventData.getLong("status"));
                    task.setRemark(eventData.getString("remark"));
                    taskService.insertBkTask(task);
                } else {
                    task.setEndTime(eventData.getDate("endTime"));
                    task.setStatus(eventData.getLong("status"));
                    task.setRemark(eventData.getString("remark"));
                    taskService.updateBkTask(task);
                }
                break;
            case "alarm":
                break;
        }
    }

}
