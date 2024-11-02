package com.backup.server.domain;

import com.backup.common.annotation.Excel;
import com.backup.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.HashMap;
import java.util.Map;

/**
 * 代理配置对象 bk_agent_config
 *
 * @author author
 * @date 2024-11-02
 */
public class BkAgentConfig extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String sfHost;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String sfPort;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String sfAk;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String sfSk;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long taskTimeout;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String redisHost;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String redisPort;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String redisDb;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String redisPassword;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long state;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setSfHost(String sfHost)
    {
        this.sfHost = sfHost;
    }

    public String getSfHost()
    {
        return sfHost;
    }
    public void setSfPort(String sfPort)
    {
        this.sfPort = sfPort;
    }

    public String getSfPort()
    {
        return sfPort;
    }
    public void setSfAk(String sfAk)
    {
        this.sfAk = sfAk;
    }

    public String getSfAk()
    {
        return sfAk;
    }
    public void setSfSk(String sfSk)
    {
        this.sfSk = sfSk;
    }

    public String getSfSk()
    {
        return sfSk;
    }
    public void setTaskTimeout(Long taskTimeout)
    {
        this.taskTimeout = taskTimeout;
    }

    public Long getTaskTimeout()
    {
        return taskTimeout;
    }
    public void setRedisHost(String redisHost)
    {
        this.redisHost = redisHost;
    }

    public String getRedisHost()
    {
        return redisHost;
    }
    public void setRedisPort(String redisPort)
    {
        this.redisPort = redisPort;
    }

    public String getRedisPort()
    {
        return redisPort;
    }
    public void setRedisDb(String redisDb)
    {
        this.redisDb = redisDb;
    }

    public String getRedisDb()
    {
        return redisDb;
    }
    public void setRedisPassword(String redisPassword)
    {
        this.redisPassword = redisPassword;
    }

    public String getRedisPassword()
    {
        return redisPassword;
    }
    public void setState(Long state)
    {
        this.state = state;
    }

    public Long getState()
    {
        return state;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("sfHost", getSfHost())
            .append("sfPort", getSfPort())
            .append("sfAk", getSfAk())
            .append("sfSk", getSfSk())
            .append("taskTimeout", getTaskTimeout())
            .append("redisHost", getRedisHost())
            .append("redisPort", getRedisPort())
            .append("redisDb", getRedisDb())
            .append("redisPassword", getRedisPassword())
            .append("state", getState())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", getId());
        map.put("sfHost", getSfHost());
        map.put("sfPort", getSfPort());
        map.put("sfAk", getSfAk());
        map.put("sfSk", getSfSk());
        map.put("taskTimeout", getTaskTimeout());
        map.put("redisHost", getRedisHost());
        map.put("redisPort", getRedisPort());
        map.put("redisDb", getRedisDb());
        map.put("redisPassword", getRedisPassword());
        map.put("state", getState());
        map.put("createTime", getCreateTime());
        return map;
    }
}
