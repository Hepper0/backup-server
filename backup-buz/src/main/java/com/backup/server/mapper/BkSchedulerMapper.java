package com.backup.server.mapper;

import java.util.List;
import com.backup.server.domain.BkScheduler;

/**
 * 备份计划Mapper接口
 *
 * @author author
 * @date 2024-10-28
 */
public interface BkSchedulerMapper
{
    /**
     * 查询备份计划
     *
     * @param schedulerId 备份计划主键
     * @return 备份计划
     */
    public BkScheduler selectBkSchedulerBySchedulerId(Long schedulerId);

    public BkScheduler selectBkSchedulerByIP(String ip);


    /**
     * 查询备份计划列表
     *
     * @param bkScheduler 备份计划
     * @return 备份计划集合
     */
    public List<BkScheduler> selectBkSchedulerList(BkScheduler bkScheduler);

    /**
     * 新增备份计划
     *
     * @param bkScheduler 备份计划
     * @return 结果
     */
    public int insertBkScheduler(BkScheduler bkScheduler);

    /**
     * 修改备份计划
     *
     * @param bkScheduler 备份计划
     * @return 结果
     */
    public int updateBkScheduler(BkScheduler bkScheduler);

    /**
     * 删除备份计划
     *
     * @param schedulerId 备份计划主键
     * @return 结果
     */
    public int deleteBkSchedulerBySchedulerId(Long schedulerId);

    /**
     * 批量删除备份计划
     *
     * @param schedulerIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBkSchedulerBySchedulerIds(Long[] schedulerIds);
}
