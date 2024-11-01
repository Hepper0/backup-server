package com.backup.server.service.impl;

import java.util.List;
import com.backup.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.backup.server.mapper.BkAgentSchedulerMapper;
import com.backup.server.domain.BkAgentScheduler;
import com.backup.server.service.IBkAgentSchedulerService;

/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author author
 * @date 2024-11-02
 */
@Service
public class BkAgentSchedulerServiceImpl implements IBkAgentSchedulerService
{
    @Autowired
    private BkAgentSchedulerMapper bkAgentSchedulerMapper;

    /**
     * 查询【请填写功能名称】
     *
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public BkAgentScheduler selectBkAgentSchedulerById(Long id)
    {
        return bkAgentSchedulerMapper.selectBkAgentSchedulerById(id);
    }

    @Override
    public BkAgentScheduler selectBkAgentSchedulerByIP(String ip)
    {
        return bkAgentSchedulerMapper.selectBkAgentSchedulerByIP(ip);
    }

    /**
     * 查询【请填写功能名称】列表
     *
     * @param bkAgentScheduler 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<BkAgentScheduler> selectBkAgentSchedulerList(BkAgentScheduler bkAgentScheduler)
    {
        return bkAgentSchedulerMapper.selectBkAgentSchedulerList(bkAgentScheduler);
    }

    /**
     * 新增【请填写功能名称】
     *
     * @param bkAgentScheduler 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertBkAgentScheduler(BkAgentScheduler bkAgentScheduler)
    {
        bkAgentScheduler.setCreateTime(DateUtils.getNowDate());
        return bkAgentSchedulerMapper.insertBkAgentScheduler(bkAgentScheduler);
    }

    /**
     * 修改【请填写功能名称】
     *
     * @param bkAgentScheduler 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateBkAgentScheduler(BkAgentScheduler bkAgentScheduler)
    {
        bkAgentScheduler.setUpdateTime(DateUtils.getNowDate());
        return bkAgentSchedulerMapper.updateBkAgentScheduler(bkAgentScheduler);
    }

    /**
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteBkAgentSchedulerByIds(Long[] ids)
    {
        return bkAgentSchedulerMapper.deleteBkAgentSchedulerByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     *
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteBkAgentSchedulerById(Long id)
    {
        return bkAgentSchedulerMapper.deleteBkAgentSchedulerById(id);
    }
}
