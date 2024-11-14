package com.backup.server.service.impl;

import java.util.List;

import com.alibaba.fastjson2.JSONObject;
import com.backup.server.config.RedisConfig;
import com.backup.server.core.MessageHandler;
import com.backup.server.domain.BkAgent;
import com.backup.server.mapper.BkAgentMapper;
import org.springframework.stereotype.Service;
import com.backup.server.mapper.BkAgentResourceMapper;
import com.backup.server.domain.BkAgentResource;
import com.backup.server.service.IBkAgentResourceService;

import javax.annotation.Resource;

/**
 * 代理备份资源配置Service业务层处理
 *
 * @author author
 * @date 2024-10-29
 */
@Service
public class BkAgentResourceServiceImpl implements IBkAgentResourceService
{
    @Resource
    private BkAgentResourceMapper bkAgentResourceMapper;

    @Resource
    private BkAgentMapper agentMapper;

    /**
     * 查询代理备份资源配置
     *
     * @param id 代理备份资源配置主键
     * @return 代理备份资源配置
     */
    @Override
    public BkAgentResource selectBkAgentResourceById(Long id)
    {
        return bkAgentResourceMapper.selectBkAgentResourceById(id);
    }

    /**
     * 查询代理备份资源配置列表
     *
     * @param bkAgentResource 代理备份资源配置
     * @return 代理备份资源配置
     */
    @Override
    public List<BkAgentResource> selectBkAgentResourceList(BkAgentResource bkAgentResource)
    {
        if (bkAgentResource.getAgentId() != null && bkAgentResource.getAgentId().contains(".")) {
            String agentIP = bkAgentResource.getAgentId();
            BkAgent agent = agentMapper.selectBkAgentByAgentIP(agentIP);
            bkAgentResource.setAgentId(agent.getAgentId() + "");
        }
        return bkAgentResourceMapper.selectBkAgentResourceList(bkAgentResource);
    }

    @Override
    public List<String> selectBkAgentResourceListByIP(String ip)
    {
        return bkAgentResourceMapper.selectBkAgentResourceListByIP(ip);
    }

    /**
     * 新增代理备份资源配置
     *
     * @param bkAgentResource 代理备份资源配置
     * @return 结果
     */
    @Override
    public int insertBkAgentResource(BkAgentResource bkAgentResource)
    {
        if (bkAgentResource.getAgentId() != null && bkAgentResource.getAgentId().contains(".")) {
            String agentIP = bkAgentResource.getAgentId();
            BkAgent agent = agentMapper.selectBkAgentByAgentIP(agentIP);
            bkAgentResource.setAgentId(agent.getAgentId() + "");
        }
        return bkAgentResourceMapper.insertBkAgentResource(bkAgentResource);
    }

    @Override
    public int insertBkAgentResources(String agentId, List<BkAgentResource> bkAgentResources) {
        if (agentId.contains(".")) {
            BkAgent agent = agentMapper.selectBkAgentByAgentIP(agentId);
            agentId = agent.getAgentId() + "";
        }
        String agentID = agentId;
        bkAgentResources.forEach(r -> {
            r.setAgentId(agentID);
        });
        return bkAgentResourceMapper.insertBkAgentResources(bkAgentResources);
    }

    /**
     * 修改代理备份资源配置
     *
     * @param bkAgentResource 代理备份资源配置
     * @return 结果
     */
    @Override
    public int updateBkAgentResource(BkAgentResource bkAgentResource)
    {
        return bkAgentResourceMapper.updateBkAgentResource(bkAgentResource);
    }

    /**
     * 批量删除代理备份资源配置
     *
     * @param ids 需要删除的代理备份资源配置主键
     * @return 结果
     */
    @Override
    public int deleteBkAgentResourceByIds(Long[] ids)
    {
        return bkAgentResourceMapper.deleteBkAgentResourceByIds(ids);
    }

    /**
     * 删除代理备份资源配置信息
     *
     * @param id 代理备份资源配置主键
     * @return 结果
     */
    @Override
    public int deleteBkAgentResourceById(Long id)
    {
        return bkAgentResourceMapper.deleteBkAgentResourceById(id);
    }

    @Override
    public boolean send2Agent(String ip) {
        List<String> resourceList = bkAgentResourceMapper.selectBkAgentResourceListByIP(ip);
        JSONObject resp = (JSONObject) MessageHandler.request(RedisConfig.REDIS_MSG_TYPE_RESOURCE, resourceList, ip);
        return resp.getInteger("code") == 0;
    }
}
