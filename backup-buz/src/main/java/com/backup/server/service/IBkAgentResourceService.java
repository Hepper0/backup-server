package com.backup.server.service;

import java.util.List;
import com.backup.server.domain.BkAgentResource;

/**
 * 备份资源Service接口
 * 
 * @author author
 * @date 2024-10-29
 */
public interface IBkAgentResourceService 
{
    /**
     * 查询备份资源
     * 
     * @param id 备份资源主键
     * @return 备份资源
     */
    public BkAgentResource selectBkAgentResourceById(Long id);

    /**
     * 查询备份资源列表
     * 
     * @param bkAgentResource 备份资源
     * @return 备份资源集合
     */
    public List<BkAgentResource> selectBkAgentResourceList(BkAgentResource bkAgentResource);

    public List<String> selectBkAgentResourceListByIP(String ip);

    /**
     * 新增备份资源
     * 
     * @param bkAgentResource 备份资源
     * @return 结果
     */
    public int insertBkAgentResource(BkAgentResource bkAgentResource);

    /**
     * 修改备份资源
     * 
     * @param bkAgentResource 备份资源
     * @return 结果
     */
    public int updateBkAgentResource(BkAgentResource bkAgentResource);

    /**
     * 批量删除备份资源
     * 
     * @param ids 需要删除的备份资源主键集合
     * @return 结果
     */
    public int deleteBkAgentResourceByIds(Long[] ids);

    /**
     * 删除备份资源信息
     * 
     * @param id 备份资源主键
     * @return 结果
     */
    public int deleteBkAgentResourceById(Long id);

    public boolean send2Agent(String ip);
}
