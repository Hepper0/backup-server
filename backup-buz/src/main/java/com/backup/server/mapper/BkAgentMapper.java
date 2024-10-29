package com.backup.server.mapper;

import java.util.List;
import com.backup.server.domain.BkAgent;

/**
 * 代理Mapper接口
 *
 * @author author
 * @date 2024-10-27
 */
public interface BkAgentMapper
{
    /**
     * 查询代理
     *
     * @param agentId 代理主键
     * @return 代理
     */
    public BkAgent selectBkAgentByAgentId(Long agentId);

    public BkAgent selectBkAgentByAgentIP(String IP);

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
     * 删除代理
     *
     * @param agentId 代理主键
     * @return 结果
     */
    public int deleteBkAgentByAgentId(Long agentId);

    /**
     * 批量删除代理
     *
     * @param agentIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBkAgentByAgentIds(Long[] agentIds);
}
