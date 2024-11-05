package com.backup.server.domain;

import com.backup.common.annotation.Excel;
import com.backup.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 备份资源对象 bk_agent_resource
 *
 * @author author
 * @date 2024-10-29
 */
public class BkAgentResource extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long id;

    /** 代理Id */
    @Excel(name = "代理Id", readConverterExp = "$column.readConverterExp()")
    private String agentId;

    /** 资源Id */
    @Excel(name = "资源Id", readConverterExp = "$column.readConverterExp()")
    private String resourceId;

    private String agentIP;

    public void setAgentIP(String agentIP)
    {
        this.agentIP = agentIP;
    }

    public String getAgentIP()
    {
        return agentIP;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setAgentId(String agentId)
    {
        this.agentId = agentId;
    }

    public String getAgentId()
    {
        return agentId;
    }
    public void setResourceId(String resourceId)
    {
        this.resourceId = resourceId;
    }

    public String getResourceId()
    {
        return resourceId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("agentId", getAgentId())
            .append("resourceId", getResourceId())
            .toString();
    }
}
