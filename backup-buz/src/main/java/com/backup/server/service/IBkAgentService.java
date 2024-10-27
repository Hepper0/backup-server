package com.backup.server.service;

import java.util.List;
import com.backup.server.domain.BkAgent;

/**
 * 代理Service接口
 * 
 * @author author
 * @date 2024-10-27
 */
public interface IBkAgentService 
{
    /**
     * 查询代理
     * 
     * @param agentId 代理主键
     * @return 代理
     */
    public BkAgent selectBkAgentByAgentId(Long agentId);

    /**
     * 查询代理列表
     * 
     * @param bkAgent 代理
     * @return 代理集合
     */
    public List<BkAgent> selectBkAgentList(BkAgent bkAgent);

    /**
     * 新增代理
     * 
     * @param bkAgent 代理
     * @return 结果
     */
    public int insertBkAgent(BkAgent bkAgent);

    /**
     * 修改代理
     * 
     * @param bkAgent 代理
     * @return 结果
     */
    public int updateBkAgent(BkAgent bkAgent);

    /**
     * 批量删除代理
     * 
     * @param agentIds 需要删除的代理主键集合
     * @return 结果
     */
    public int deleteBkAgentByAgentIds(Long[] agentIds);

    /**
     * 删除代理信息
     * 
     * @param agentId 代理主键
     * @return 结果
     */
    public int deleteBkAgentByAgentId(Long agentId);
}
