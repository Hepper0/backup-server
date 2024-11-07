package com.backup.server.service.impl;

import java.util.List;

import com.backup.common.core.redis.RedisCache;
import com.backup.common.utils.DateUtils;
import com.backup.server.config.RedisConfig;
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

    private Integer getAgentStatusFromCache(String agentIP, String taskId) {
        return redis.getCacheObject(RedisConfig.REDIS_AGENT_RUNNING_PREFIX + agentIP + ":" + taskId);
    }

    private void setStatus(BkTask bkTask) {
        Integer status = getAgentStatusFromCache(bkTask.getAgentIP(), bkTask.getTaskId());
        if (status != null){
            bkTask.setStatus(new Long(status));
        }
    }
}
