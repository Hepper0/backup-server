package com.backup.server.service.impl;

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import com.alibaba.fastjson2.JSONObject;
import com.backup.common.core.redis.RedisCache;
import com.backup.common.utils.DateUtils;
import com.backup.server.config.RedisConfig;
import com.backup.server.config.StatusConfig;
import com.backup.server.core.MessageHandler;
import com.backup.server.core.domain.TaskAction;
import org.springframework.stereotype.Service;
import com.backup.server.mapper.BkTaskMapper;
import com.backup.server.domain.BkTask;
import com.backup.server.service.IBkTaskService;

import javax.annotation.Resource;

/**
 * 备份任务Service业务层处理
 *
 * @author author
 * @date 2024-10-28
 */
@Service
public class BkTaskServiceImpl implements IBkTaskService
{
    @Resource
    private BkTaskMapper bkTaskMapper;

    @Resource
    private RedisCache redis;

    /**
     * 查询备份任务
     *
     * @param taskId 备份任务主键
     * @return 备份任务
     */
    @Override
    public BkTask selectBkTaskByTaskId(String taskId)
    {
        return bkTaskMapper.selectBkTaskByTaskId(taskId);
    }

    /**
     * 查询备份任务列表
     *
     * @param bkTask 备份任务
     * @return 备份任务
     */
    @Override
    public List<BkTask> selectBkTaskList(BkTask bkTask)
    {
        List<BkTask> tasks = bkTaskMapper.selectBkTaskList(bkTask);
        tasks.forEach(this::setStatus);
        return tasks;
    }

    /**
     * 新增备份任务
     *
     * @param bkTask 备份任务
     * @return 结果
     */
    @Override
    public int insertBkTask(BkTask bkTask)
    {
        bkTask.setCreateTime(DateUtils.getNowDate());
        return bkTaskMapper.insertBkTask(bkTask);
    }

    /**
     * 修改备份任务
     *
     * @param bkTask 备份任务
     * @return 结果
     */
    @Override
    public int updateBkTask(BkTask bkTask)
    {
        bkTask.setUpdateTime(DateUtils.getNowDate());
        return bkTaskMapper.updateBkTask(bkTask);
    }

    /**
     * 批量删除备份任务
     *
     * @param taskIds 需要删除的备份任务主键
     * @return 结果
     */
    @Override
    public int deleteBkTaskByTaskIds(Long[] taskIds)
    {
        return bkTaskMapper.deleteBkTaskByTaskIds(taskIds);
    }

    /**
     * 删除备份任务信息
     *
     * @param taskId 备份任务主键
     * @return 结果
     */
    @Override
    public int deleteBkTaskByTaskId(Long taskId)
    {
        return bkTaskMapper.deleteBkTaskByTaskId(taskId);
    }

    private boolean getTaskStatusFromCache(String agentIP, String taskId) {
        return redis.getCacheObject(RedisConfig.REDIS_AGENT_RUNNING_PREFIX + agentIP + ":" + taskId) != null;
    }

    public boolean redoBkTask(String serverId){
        BkTask task = new BkTask();
        task.setTarget(serverId);
        task.setStartTime(new Date());
        // 当天有任务,且没有成功的任务, 才可以下发
        List<BkTask> tasks = bkTaskMapper.selectBkTaskList(task);
        if (tasks.size() > 0) {
            AtomicBoolean isSend = new AtomicBoolean(true);
            tasks.forEach(t -> {
                if (t.getStatus().equals(StatusConfig.TASK_RESULT_SUCCESS)) {
                    isSend.set(false);
                }
            });
            if (isSend.get()) {
                TaskAction action = new TaskAction();
                action.setAction("backup");
                action.setServerId(serverId);
                JSONObject resp = (JSONObject) MessageHandler.request(RedisConfig.REDIS_MSG_TYPE_TASK, action, tasks.get(0).getAgentIP());
                return resp.getInteger("code") == 0;
            }
            throw new RuntimeException("当天的备份任务已执行成功,无需重复执行.");
        }
        throw new RuntimeException("当天的备份任务还未执行");
    }

    private void setStatus(BkTask bkTask) {
        // 任务还没有得到运行结果,查询缓存是否在运行中
        if (bkTask.getStatus().equals(StatusConfig.TASK_RESULT_RUNNING)) {
            boolean status = getTaskStatusFromCache(bkTask.getAgentIP(), bkTask.getTaskId());
            // 两边状态不一致,说明任务有问题
            if (!status){
                // 任务已经被中止, 但是server没有收到通知
                bkTask.setStatus(StatusConfig.TASK_RESULT_FAILURE);
                bkTask.setRemark("任务异常中止, 请联系管理员排查.");
//                bkTask.setStatus(new Long(StatusConfig.TASK_RESULT_RUNNING));
            }
        }
    }
}
