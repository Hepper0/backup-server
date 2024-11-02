package com.backup.server.domain;

import com.backup.common.annotation.Excel;
import com.backup.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.HashMap;
import java.util.Map;

/**
 * 备份计划对象 bk_scheduler
 *
 * @author author
 * @date 2024-10-28
 */
public class BkScheduler extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long schedulerId;

    /** $column.columnComment */
    @Excel(name = "计划名称", readConverterExp = "$column.readConverterExp()")
    private String name;

    /** $column.columnComment */
    @Excel(name = "分", readConverterExp = "$column.readConverterExp()")
    private String min;

    /** $column.columnComment */
    @Excel(name = "时", readConverterExp = "$column.readConverterExp()")
    private String hour;

    /** $column.columnComment */
    @Excel(name = "日", readConverterExp = "$column.readConverterExp()")
    private String day;

    /** $column.columnComment */
    @Excel(name = "月", readConverterExp = "$column.readConverterExp()")
    private String mon;

    /** $column.columnComment */
    @Excel(name = "周", readConverterExp = "$column.readConverterExp()")
    private String week;

    /** $column.columnComment */
    @Excel(name = "状态", readConverterExp = "$column.readConverterExp()")
    private Long status;

    /** $column.columnComment */
//    @Excel(name = "删除", readConverterExp = "$column.readConverterExp()")
    private Long deleted;

    public void setSchedulerId(Long schedulerId)
    {
        this.schedulerId = schedulerId;
    }

    public Long getSchedulerId()
    {
        return schedulerId;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
    public void setMin(String min)
    {
        this.min = min;
    }

    public String getMin()
    {
        return min;
    }
    public void setHour(String hour)
    {
        this.hour = hour;
    }

    public String getHour()
    {
        return hour;
    }
    public void setDay(String day)
    {
        this.day = day;
    }

    public String getDay()
    {
        return day;
    }
    public void setMon(String mon)
    {
        this.mon = mon;
    }

    public String getMon()
    {
        return mon;
    }
    public void setWeek(String week)
    {
        this.week = week;
    }

    public String getWeek()
    {
        return week;
    }
    public void setStatus(Long status)
    {
        this.status = status;
    }

    public Long getStatus()
    {
        return status;
    }
    public void setDeleted(Long deleted)
    {
        this.deleted = deleted;
    }

    public Long getDeleted()
    {
        return deleted;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("schedulerId", getSchedulerId())
            .append("name", getName())
            .append("min", getMin())
            .append("hour", getHour())
            .append("day", getDay())
            .append("mon", getMon())
            .append("week", getWeek())
            .append("status", getStatus())
            .append("remark", getRemark())
            .append("deleted", getDeleted())
            .append("createTime", getCreateTime())
            .append("createBy", getCreateBy())
            .append("updateTime", getUpdateTime())
            .append("updateBy", getUpdateBy())
            .toString();
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("schedulerId", getSchedulerId());
        map.put("name", getName());
        map.put("min", getMin());
        map.put("hour", getHour());
        map.put("day", getDay());
        map.put("mon", getMon());
        map.put("week", getWeek());
        map.put("status", getStatus());
        map.put("remark", getRemark());
        map.put("createTime", getCreateTime());
        return map;
    }
}
