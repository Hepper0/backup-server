package com.backup.server.service.impl;

import java.util.List;

import com.backup.common.core.redis.RedisCache;
import com.backup.common.utils.DateUtils;
import com.backup.server.config.RedisConfig;
import com.backup.server.core.domain.BuzMessage;
import com.backup.server.core.domain.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.backup.server.mapper.BkAgentConfigMapper;
import com.backup.server.domain.BkAgentConfig;
import com.backup.server.service.IBkAgentConfigService;

import javax.annotation.Resource;

/**
 * 代理配置Service业务层处理
 *
 * @author author
 * @date 2024-11-02
 */
@Service
public class BkAgentConfigServiceImpl implements IBkAgentConfigService
{
    @Resource
    private BkAgentConfigMapper bkAgentConfigMapper;

    @Resource
    RedisCache redis;

    /**
     * 查询代理配置
     *
     * @param id 代理配置主键
     * @return 代理配置
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

    @Override
    public void broadcast(BkAgentConfig bkAgentConfig) {
        Message message = new Message();
        message.setPayload(new BuzMessage(RedisConfig.REDIS_MSG_TYPE_CONFIG, bkAgentConfig.toMap()));
        redis.publish(RedisConfig.REDIS_BROADCAST_TOPIC, message.toString());
    }

    /**
     * 查询代理配置列表
     *
     * @param bkAgentConfig 代理配置
     * @return 代理配置
     */
    @Override
    public List<BkAgentConfig> selectBkAgentConfigList(BkAgentConfig bkAgentConfig)
    {
        return bkAgentConfigMapper.selectBkAgentConfigList(bkAgentConfig);
    }

    /**
     * 新增代理配置
     *
     * @param bkAgentConfig 代理配置
     * @return 结果
     */
    @Override
    public int insertBkAgentConfig(BkAgentConfig bkAgentConfig)
    {
        bkAgentConfig.setCreateTime(DateUtils.getNowDate());
        return bkAgentConfigMapper.insertBkAgentConfig(bkAgentConfig);
    }

    /**
     * 修改代理配置
     *
     * @param bkAgentConfig 代理配置
     * @return 结果
     */
    @Override
    public int updateBkAgentConfig(BkAgentConfig bkAgentConfig)
    {
        bkAgentConfig.setUpdateTime(DateUtils.getNowDate());
        return bkAgentConfigMapper.updateBkAgentConfig(bkAgentConfig);
    }

    /**
     * 批量删除代理配置
     *
     * @param ids 需要删除的代理配置主键
     * @return 结果
     */
    @Override
    public int deleteBkAgentConfigByIds(Long[] ids)
    {
        return bkAgentConfigMapper.deleteBkAgentConfigByIds(ids);
    }

    /**
     * 删除代理配置信息
     *
     * @param id 代理配置主键
     * @return 结果
     */
    @Override
    public int deleteBkAgentConfigById(Long id)
    {
        return bkAgentConfigMapper.deleteBkAgentConfigById(id);
    }
}
