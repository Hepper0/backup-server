package com.backup.server.service.impl;

import java.util.List;

import com.alibaba.fastjson2.JSONObject;
import com.backup.server.core.MessageHandler;
import com.backup.server.domain.BkAgent;
import com.backup.server.mapper.BkAgentMapper;
import org.springframework.stereotype.Service;
import com.backup.server.mapper.BkAgentResourceMapper;
import com.backup.server.domain.BkAgentResource;
import com.backup.server.service.IBkAgentResourceService;

import javax.annotation.Resource;

/**
 * 【请填写功能名称】Service业务层处理
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
     * 查询【请填写功能名称】
     *
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public BkAgentResource selectBkAgentResourceById(Long id)
    {
        return bkAgentResourceMapper.selectBkAgentResourceById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     *
     * @param bkAgentResource 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<BkAgentResource> selectBkAgentResourceList(BkAgentResource bkAgentResource)
    {
        if (bkAgentResource.getAgentId().contains(".")) {
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
     * 新增【请填写功能名称】
     *
     * @param bkAgentResource 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertBkAgentResource(BkAgentResource bkAgentResource)
    {
        if (bkAgentResource.getAgentId().contains(".")) {
            String agentIP = bkAgentResource.getAgentId();
            BkAgent agent = agentMapper.selectBkAgentByAgentIP(agentIP);
            bkAgentResource.setAgentId(agent.getAgentId() + "");
        }
        return bkAgentResourceMapper.insertBkAgentResource(bkAgentResource);
    }

    /**
     * 修改【请填写功能名称】
     *
     * @param bkAgentResource 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateBkAgentResource(BkAgentResource bkAgentResource)
    {
        return bkAgentResourceMapper.updateBkAgentResource(bkAgentResource);
    }

    /**
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteBkAgentResourceByIds(Long[] ids)
    {
        return bkAgentResourceMapper.deleteBkAgentResourceByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     *
     * @param id 【请填写功能名称】主键
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
        JSONObject resp = (JSONObject) MessageHandler.request(JSONObject.toJSONString(resourceList), ip);
        return resp.getInteger("code") == 0;
    }
}
