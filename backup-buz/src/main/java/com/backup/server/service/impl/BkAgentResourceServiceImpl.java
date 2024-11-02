package com.backup.server.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.backup.server.mapper.BkAgentResourceMapper;
import com.backup.server.domain.BkAgentResource;
import com.backup.server.service.IBkAgentResourceService;

/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author author
 * @date 2024-10-29
 */
@Service
public class BkAgentResourceServiceImpl implements IBkAgentResourceService
{
    @Autowired
    private BkAgentResourceMapper bkAgentResourceMapper;

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
}
