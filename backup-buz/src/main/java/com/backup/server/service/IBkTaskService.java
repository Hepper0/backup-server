package com.backup.server.service;

import java.util.List;
import com.backup.server.domain.BkTask;

/**
 * 备份任务Service接口
 *
 * @author author
 * @date 2024-10-28
 */
public interface IBkTaskService
{
    /**
     * 查询备份任务
     *
     * @param taskId 备份任务主键
     * @return 备份任务
     */
    public BkTask selectBkTaskByTaskId(Long taskId);

    /**
     * 查询备份任务列表
     *
     * @param bkTask 备份任务
     * @return 备份任务集合
     */
    public List<BkTask> selectBkTaskList(BkTask bkTask);

    /**
     * 新增备份任务
     *
     * @param bkTask 备份任务
     * @return 结果
     */
    public int insertBkTask(BkTask bkTask);

    /**
     * 修改备份任务
     *
     * @param bkTask 备份任务
     * @return 结果
     */
    public int updateBkTask(BkTask bkTask);

    /**
     * 批量删除备份任务
     *
     * @param taskIds 需要删除的备份任务主键集合
     * @return 结果
     */
    public int deleteBkTaskByTaskIds(Long[] taskIds);

    /**
     * 删除备份任务信息
     *
     * @param taskId 备份任务主键
     * @return 结果
     */
    public int deleteBkTaskByTaskId(Long taskId);
}
