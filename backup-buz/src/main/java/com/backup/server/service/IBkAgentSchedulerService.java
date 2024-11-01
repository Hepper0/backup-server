package com.backup.server.service;

import java.util.List;
import com.backup.server.domain.BkAgentScheduler;

/**
 * 代理计划Service接口
 *
 * @author author
 * @date 2024-11-02
 */
public interface IBkAgentSchedulerService
{
    /**
     * 查询代理计划
     *
     * @param id 代理计划主键
     * @return 代理计划
     */
    public BkAgentScheduler selectBkAgentSchedulerById(Long id);

    /**
     * 查询代理计划列表
     *
     * @param bkAgentScheduler 代理计划
     * @return 代理计划集合
     */
    public List<BkAgentScheduler> selectBkAgentSchedulerList(BkAgentScheduler bkAgentScheduler);

    /**
     * 新增代理计划
     *
     * @param bkAgentScheduler 代理计划
     * @return 结果
     */
    public int insertBkAgentScheduler(BkAgentScheduler bkAgentScheduler);

    /**
     * 修改代理计划
     *
     * @param bkAgentScheduler 代理计划
     * @return 结果
     */
    public int updateBkAgentScheduler(BkAgentScheduler bkAgentScheduler);

    /**
     * 批量删除代理计划
     *
     * @param ids 需要删除的代理计划主键集合
     * @return 结果
     */
    public int deleteBkAgentSchedulerByIds(Long[] ids);

    /**
     * 删除代理计划信息
     *
     * @param id 代理计划主键
     * @return 结果
     */
    public int deleteBkAgentSchedulerById(Long id);
}
