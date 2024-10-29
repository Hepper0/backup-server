package com.backup.server.service.impl;

import java.util.List;
import com.backup.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.backup.server.mapper.BkAgentMapper;
import com.backup.server.domain.BkAgent;
import com.backup.server.service.IBkAgentService;

import javax.annotation.Resource;

/**
 * 代理Service业务层处理
 *
 * @author author
 * @date 2024-10-27
 */
@Service
public class BkAgentServiceImpl implements IBkAgentService
{
    @Autowired
    private BkAgentMapper bkAgentMapper;

    /**
     * 查询代理
     *
     * @param agentId 代理主键
     * @return 代理
     */
    @Override
    public BkAgent selectBkAgentByAgentId(Long agentId)
    {
        return bkAgentMapper.selectBkAgentByAgentId(agentId);
    }

    @Override
    public BkAgent selectBkAgentByAgentIP(String IP)
    {
        return bkAgentMapper.selectBkAgentByAgentIP(IP);
    }

    /**
     * 查询代理列表
     *
     * @param bkAgent 代理
     * @return 代理
     */
    @Override
    public List<BkAgent> selectBkAgentList(BkAgent bkAgent)
    {
        return bkAgentMapper.selectBkAgentList(bkAgent);
    }

    /**
     * 新增代理
     *
     * @param bkAgent 代理
     * @return 结果
     */
    @Override
    public int insertBkAgent(BkAgent bkAgent)
    {
        bkAgent.setCreateTime(DateUtils.getNowDate());
        return bkAgentMapper.insertBkAgent(bkAgent);
    }

    /**
     * 修改代理
     *
     * @param bkAgent 代理
     * @return 结果
     */
    @Override
    public int updateBkAgent(BkAgent bkAgent)
    {
        bkAgent.setUpdateTime(DateUtils.getNowDate());
        return bkAgentMapper.updateBkAgent(bkAgent);
    }

    /**
     * 批量删除代理
     *
     * @param agentIds 需要删除的代理主键
     * @return 结果
     */
    @Override
    public int deleteBkAgentByAgentIds(Long[] agentIds)
    {
        return bkAgentMapper.deleteBkAgentByAgentIds(agentIds);
    }

    /**
     * 删除代理信息
     *
     * @param agentId 代理主键
     * @return 结果
     */
    @Override
    public int deleteBkAgentByAgentId(Long agentId)
    {
        return bkAgentMapper.deleteBkAgentByAgentId(agentId);
    }
}
