package com.backup.server.service.impl;

import java.util.Collection;
import java.util.List;

import com.backup.common.core.redis.RedisCache;
import com.backup.common.utils.DateUtils;
import com.backup.server.config.RedisConfig;
import com.backup.server.config.StatusConfig;
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
    @Resource
    private BkAgentMapper bkAgentMapper;

    @Resource
    private RedisCache redis;

    /**
     * 查询代理
     *
     * @param agentId 代理主键
     * @return 代理
     */
    @Override
    public BkAgent selectBkAgentByAgentId(Long agentId)
    {
        BkAgent agent = bkAgentMapper.selectBkAgentByAgentId(agentId);
        setStatus(agent);
        return agent;
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

        List<BkAgent> agents = bkAgentMapper.selectBkAgentList(bkAgent);
        agents.forEach(this::setStatus);
        return agents;
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

    private Integer getAgentStatusFromCache(String ip) {
        return redis.getCacheObject(RedisConfig.REDIS_AGENT_ONLINE_PREFIX + ip);
    }

    private void setStatus(BkAgent agent) {
        String ip = agent.getIp();
        Integer status = getAgentStatusFromCache(ip);
        if (status != null){
            // 在线状态, 获取是否有任务在运行
            if (status.equals(StatusConfig.SERVER_ONLINE)) {
                Collection<String> keys = redis.keys(RedisConfig.REDIS_AGENT_RUNNING_PREFIX + ip + ":*");
                if (keys.size() > 0) {
                    agent.setTaskStatus(new Long(StatusConfig.SERVER_STATUS_RUNNING));
                } else {
                    agent.setTaskStatus(new Long(StatusConfig.SERVER_STATUS_IDLE));
                }
            }
            agent.setStatus(new Long(status));
        }
    }

}
