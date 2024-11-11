package com.backup.server.service.impl;

import java.util.List;
import com.backup.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.backup.server.mapper.BkAgentSchedulerMapper;
import com.backup.server.domain.BkAgentScheduler;
import com.backup.server.service.IBkAgentSchedulerService;

import javax.annotation.Resource;

/**
 * 代理计划Service业务层处理
 *
 * @author author
 * @date 2024-11-02
 */
@Service
public class BkAgentSchedulerServiceImpl implements IBkAgentSchedulerService
{
    @Resource
    private BkAgentSchedulerMapper bkAgentSchedulerMapper;

    /**
     * 查询代理计划
     *
     * @param id 代理计划主键
     * @return 代理计划
     */
    @Override
    public BkAgentScheduler selectBkAgentSchedulerById(Long id)
    {
        return bkAgentSchedulerMapper.selectBkAgentSchedulerById(id);
    }

    /**
     * 查询代理计划列表
     *
     * @param bkAgentScheduler 代理计划
     * @return 代理计划
     */
    @Override
    public List<BkAgentScheduler> selectBkAgentSchedulerList(BkAgentScheduler bkAgentScheduler)
    {
        return bkAgentSchedulerMapper.selectBkAgentSchedulerList(bkAgentScheduler);
    }

    /**
     * 新增代理计划
     *
     * @param bkAgentScheduler 代理计划
     * @return 结果
     */
    @Override
    public int insertBkAgentScheduler(BkAgentScheduler bkAgentScheduler)
    {
        bkAgentScheduler.setCreateTime(DateUtils.getNowDate());
        return bkAgentSchedulerMapper.insertBkAgentScheduler(bkAgentScheduler);
    }

    /**
     * 修改代理计划
     *
     * @param bkAgentScheduler 代理计划
     * @return 结果
     */
    @Override
    public int updateBkAgentScheduler(BkAgentScheduler bkAgentScheduler)
    {
        bkAgentScheduler.setUpdateTime(DateUtils.getNowDate());
        return bkAgentSchedulerMapper.updateBkAgentScheduler(bkAgentScheduler);
    }

    /**
     * 批量删除代理计划
     *
     * @param ids 需要删除的代理计划主键
     * @return 结果
     */
    @Override
    public int deleteBkAgentSchedulerByIds(Long[] ids)
    {
        return bkAgentSchedulerMapper.deleteBkAgentSchedulerByIds(ids);
    }

    /**
     * 删除代理计划信息
     *
     * @param id 代理计划主键
     * @return 结果
     */
    @Override
    public int deleteBkAgentSchedulerById(Long id)
    {
        return bkAgentSchedulerMapper.deleteBkAgentSchedulerById(id);
    }
}
