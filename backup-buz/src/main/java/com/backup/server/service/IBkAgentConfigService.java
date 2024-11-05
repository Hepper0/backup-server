package com.backup.server.service;

import java.util.List;
import com.backup.server.domain.BkAgentConfig;

/**
 * 代理配置Service接口
 *
 * @author author
 * @date 2024-11-02
 */
public interface IBkAgentConfigService
{
    /**
     * 查询代理配置
     *
     * @param id 代理配置主键
     * @return 代理配置
     */
    public BkAgentConfig selectBkAgentConfigById(Long id);
    public BkAgentConfig selectAvailableBkAgentConfig();

    public void broadcast(BkAgentConfig bkAgentConfig);

    /**
     * 查询代理配置列表
     *
     * @param bkAgentConfig 代理配置
     * @return 代理配置集合
     */
    public List<BkAgentConfig> selectBkAgentConfigList(BkAgentConfig bkAgentConfig);

    /**
     * 新增代理配置
     *
     * @param bkAgentConfig 代理配置
     * @return 结果
     */
    public int insertBkAgentConfig(BkAgentConfig bkAgentConfig);

    /**
     * 修改代理配置
     *
     * @param bkAgentConfig 代理配置
     * @return 结果
     */
    public int updateBkAgentConfig(BkAgentConfig bkAgentConfig);

    /**
     * 批量删除代理配置
     *
     * @param ids 需要删除的代理配置主键集合
     * @return 结果
     */
    public int deleteBkAgentConfigByIds(Long[] ids);

    /**
     * 删除代理配置信息
     *
     * @param id 代理配置主键
     * @return 结果
     */
    public int deleteBkAgentConfigById(Long id);
}
