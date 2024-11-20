package com.backup.server.domain;

import java.util.Date;

import com.backup.common.core.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.backup.common.annotation.Excel;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 备份任务对象 bk_task
 *
 * @author author
 * @date 2024-10-28
 */
@Data
public class BkTask extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 任务Id */
    private String taskId;

    /** agentIP */
    private String agentIP;

    /** 任务类型 */
    private String taskType;

    /** 备份目标 */
    @Excel(name = "备份目标", readConverterExp = "$column.readConverterExp()")
    private String target;

    /** $column.columnComment */
    @Excel(name = "备份路径", readConverterExp = "$column.readConverterExp()")
    private String backupPath;

    /** $column.columnComment */
    @Excel(name = "开始时间", readConverterExp = "$column.readConverterExp()")
    private Date startTime;

    /** $column.columnComment */
    @Excel(name = "结束时间", readConverterExp = "$column.readConverterExp()")
    private Date endTime;

    /** $column.columnComment */
    @Excel(name = "状态", readConverterExp = "$column.readConverterExp()")
    private Long status;

    private Long deleted;

//    public void setTaskId(String taskId)
//    {
//        this.taskId = taskId;
//    }
//
//    public String getTaskId()
//    {
//        return taskId;
//    }
//    public void setTarget(String target)
//    {
//        this.target = target;
//    }
//
//    public String getTarget()
//    {
//        return target;
//    }
//    public void setBackupPath(String backupPath)
//    {
//        this.backupPath = backupPath;
//    }
//
//    public String getBackupPath()
//    {
//        return backupPath;
//    }
//    public void setStartTime(Date startTime)
//    {
//        this.startTime = startTime;
//    }
//
//    public Date getStartTime()
//    {
//        return startTime;
//    }
//    public void setEndTime(Date endTime)
//    {
//        this.endTime = endTime;
//    }
//
//    public Date getEndTime()
//    {
//        return endTime;
//    }
//    public void setStatus(Long status)
//    {
//        this.status = status;
//    }
//
//    public Long getStatus()
//    {
//        return status;
//    }
//    public void setDeleted(Long deleted)
//    {
//        this.deleted = deleted;
//    }
//
//    public Long getDeleted()
//    {
//        return deleted;
//    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("taskId", getTaskId())
            .append("target", getTarget())
            .append("backupPath", getBackupPath())
            .append("startTime", getStartTime())
            .append("endTime", getEndTime())
            .append("status", getStatus())
            .append("remark", getRemark())
            .append("deleted", getDeleted())
            .append("createTime", getCreateTime())
            .append("createBy", getCreateBy())
            .append("updateTime", getUpdateTime())
            .append("updateBy", getUpdateBy())
            .toString();
    }
}
