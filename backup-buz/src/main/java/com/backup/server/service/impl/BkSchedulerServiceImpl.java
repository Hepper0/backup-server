package com.backup.server.service.impl;

import java.util.List;
import com.backup.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.backup.server.mapper.BkSchedulerMapper;
import com.backup.server.domain.BkScheduler;
import com.backup.server.service.IBkSchedulerService;

/**
 * 备份计划Service业务层处理
 *
 * @author author
 * @date 2024-10-28
 */
@Service
public class BkSchedulerServiceImpl implements IBkSchedulerService
{
    @Autowired
    private BkSchedulerMapper bkSchedulerMapper;

    /**
     * 查询备份计划
     *
     * @param schedulerId 备份计划主键
     * @return 备份计划
     */
    @Override
    public BkScheduler selectBkSchedulerBySchedulerId(Long schedulerId)
    {
        return bkSchedulerMapper.selectBkSchedulerBySchedulerId(schedulerId);
    }

    /**
     * 查询备份计划列表
     *
     * @param bkScheduler 备份计划
     * @return 备份计划
     */
    @Override
    public List<BkScheduler> selectBkSchedulerList(BkScheduler bkScheduler)
    {
        return bkSchedulerMapper.selectBkSchedulerList(bkScheduler);
    }

    /**
     * 新增备份计划
     *
     * @param bkScheduler 备份计划
     * @return 结果
     */
    @Override
    public int insertBkScheduler(BkScheduler bkScheduler)
    {
        bkScheduler.setCreateTime(DateUtils.getNowDate());
        return bkSchedulerMapper.insertBkScheduler(bkScheduler);
    }

    /**
     * 修改备份计划
     *
     * @param bkScheduler 备份计划
     * @return 结果
     */
    @Override
    public int updateBkScheduler(BkScheduler bkScheduler)
    {
        bkScheduler.setUpdateTime(DateUtils.getNowDate());
        return bkSchedulerMapper.updateBkScheduler(bkScheduler);
    }

    /**
     * 批量删除备份计划
     *
     * @param schedulerIds 需要删除的备份计划主键
     * @return 结果
     */
    @Override
    public int deleteBkSchedulerBySchedulerIds(Long[] schedulerIds)
    {
        return bkSchedulerMapper.deleteBkSchedulerBySchedulerIds(schedulerIds);
    }

    /**
     * 删除备份计划信息
     *
     * @param schedulerId 备份计划主键
     * @return 结果
     */
    @Override
    public int deleteBkSchedulerBySchedulerId(Long schedulerId)
    {
        return bkSchedulerMapper.deleteBkSchedulerBySchedulerId(schedulerId);
    }
}
