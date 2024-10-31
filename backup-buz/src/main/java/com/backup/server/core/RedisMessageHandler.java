package com.backup.server.core;

import com.alibaba.fastjson2.JSONObject;
import com.backup.common.core.redis.RedisCache;
import com.backup.common.utils.bean.BeanUtils;
import com.backup.server.core.domain.AgentInfo;
import com.backup.server.core.domain.AgentTaskInfo;
import com.backup.server.core.domain.Message;
import com.backup.server.domain.BkAgent;
import com.backup.server.domain.BkTask;
import com.backup.server.service.IAgentService;
import com.backup.server.service.IBkAgentService;
import com.backup.server.service.IBkTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component
public class RedisMessageHandler {

    @Resource
    RedisCache redisCache;

    @Resource
    IBkAgentService agentService;

    @Resource
    IBkTaskService taskService;

    public void handleMessage(String msg) {
        MessageHandler handler;
        JSONObject msgJson = JSONObject.parseObject(msg);
        com.backup.server.core.domain.Message message = new com.backup.server.core.domain.Message();
        String ip = msgJson.getString("ip");
        int messageType = msgJson.getInteger("type");
        message.setIp(ip);
        message.setType(messageType);
        message.setPayload(msgJson.getJSONObject("payload"));
        if (messageType == MessageHandler.MESSAGE_TYPE_RESPONSE) {
            String uuid = msgJson.getString("uuid");
            handler = MessageHandler.MSG_MAP.get(uuid);
            if (handler == null) {
                throw new RuntimeException("Agent message handler is not found!");
            }
            handler.handleResponseMessage(message);
        } else {
            handler = MessageHandler.getHandler(ip);
            if (handler == null) {
                handler = new MessageHandler(ip, redisCache);
                listen(ip);
            }
            handler.handleMessage(message);
        }
    }

    public void listen(String ip) {
        // 每个handler需要一个线程阻塞等待Agent的消息, 并且可以掉用到Service
        new Thread(() -> {
            while(true) {
                try {
                    MessageHandler messageHandler = MessageHandler.getHandler(ip);
                    Object msg = messageHandler.takeMessage();
                    if (msg instanceof Message) {
                        log.info("收到消息: {}", msg.toString());
                        if (((Message) msg).getType() == MessageHandler.MESSAGE_TYPE_REQUEST) {
                            String resp = "我收到啦";
                            log.info("回复消息: {}", resp);
                            messageHandler.response(resp);
                        } else if (((Message) msg).getType() == MessageHandler.MESSAGE_TYPE_PUSH) {
                            /*
                            1. Agent 空间不足
                            2. 任务完成
                            3. Agent启动
                            */
                            JSONObject data = (JSONObject) ((Message) msg).getPayload();
                            String eventType = data.getString("eventType");
                            JSONObject eventData = data.getJSONObject("data");
                            switch (eventType) {
                                case "agentInfo":
                                    BkAgent agent = agentService.selectBkAgentByAgentIP(ip);
                                    if (agent == null) {
                                        BkAgent newAgent = new BkAgent();
                                        newAgent.setIp(ip);
                                        newAgent.setHostname(eventData.getString("hostname"));
                                        agentService.insertBkAgent(newAgent);
                                    }
                                    break;
                                case "taskInfo":
                                    String taskId = eventData.getString("taskId");
                                    BkTask task = taskService.selectBkTaskByTaskId(taskId);
                                    if (task == null) {
                                        task = new BkTask();
                                        task.setTaskId(taskId);
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
                    } else {
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
                String dev;
                if (keyArr.length > 3) {
                    dev = keyArr[3];
                    log.info(dev + "running abort");
                    // todo record running exception
                } else {
                    throw new RuntimeException("Lack of the device id");
                }
                break;
        }
    }

}
