package com.backup.server.service.impl;

import java.util.List;
import com.backup.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.backup.server.mapper.BkAgentConfigMapper;
import com.backup.server.domain.BkAgentConfig;
import com.backup.server.service.IBkAgentConfigService;

/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author author
 * @date 2024-11-02
 */
@Service
public class BkAgentConfigServiceImpl implements IBkAgentConfigService
{
    @Autowired
    private BkAgentConfigMapper bkAgentConfigMapper;

    /**
     * 查询【请填写功能名称】
     *
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public BkAgentConfig selectBkAgentConfigById(Long id)
    {
        return bkAgentConfigMapper.selectBkAgentConfigById(id);
    }

    @Override
    public BkAgentConfig selectAvailableBkAgentConfig() {
        return bkAgentConfigMapper.selectAvailableBkAgentConfig();
    }

    /**
     * 查询【请填写功能名称】列表
     *
     * @param bkAgentConfig 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<BkAgentConfig> selectBkAgentConfigList(BkAgentConfig bkAgentConfig)
    {
        return bkAgentConfigMapper.selectBkAgentConfigList(bkAgentConfig);
    }

    /**
     * 新增【请填写功能名称】
     *
     * @param bkAgentConfig 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertBkAgentConfig(BkAgentConfig bkAgentConfig)
    {
        bkAgentConfig.setCreateTime(DateUtils.getNowDate());
        return bkAgentConfigMapper.insertBkAgentConfig(bkAgentConfig);
    }

    /**
     * 修改【请填写功能名称】
     *
     * @param bkAgentConfig 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateBkAgentConfig(BkAgentConfig bkAgentConfig)
    {
        bkAgentConfig.setUpdateTime(DateUtils.getNowDate());
        return bkAgentConfigMapper.updateBkAgentConfig(bkAgentConfig);
    }

    /**
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteBkAgentConfigByIds(Long[] ids)
    {
        return bkAgentConfigMapper.deleteBkAgentConfigByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     *
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteBkAgentConfigById(Long id)
    {
        return bkAgentConfigMapper.deleteBkAgentConfigById(id);
    }
}
