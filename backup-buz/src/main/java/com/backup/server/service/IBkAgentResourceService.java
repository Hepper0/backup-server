package com.backup.server.service;

import java.util.List;
import com.backup.server.domain.BkAgentResource;

/**
 * 【请填写功能名称】Service接口
 * 
 * @author author
 * @date 2024-10-29
 */
public interface IBkAgentResourceService 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public BkAgentResource selectBkAgentResourceById(Long id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param bkAgentResource 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<BkAgentResource> selectBkAgentResourceList(BkAgentResource bkAgentResource);

    /**
     * 新增【请填写功能名称】
     * 
     * @param bkAgentResource 【请填写功能名称】
     * @return 结果
     */
    public int insertBkAgentResource(BkAgentResource bkAgentResource);

    /**
     * 修改【请填写功能名称】
     * 
     * @param bkAgentResource 【请填写功能名称】
     * @return 结果
     */
    public int updateBkAgentResource(BkAgentResource bkAgentResource);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】主键集合
     * @return 结果
     */
    public int deleteBkAgentResourceByIds(Long[] ids);

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteBkAgentResourceById(Long id);
}
