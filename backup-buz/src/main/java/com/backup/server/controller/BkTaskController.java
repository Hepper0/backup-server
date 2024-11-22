package com.backup.server.controller;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.backup.common.annotation.Log;
import com.backup.common.core.controller.BaseController;
import com.backup.common.core.domain.AjaxResult;
import com.backup.common.enums.BusinessType;
import com.backup.server.domain.BkTask;
import com.backup.server.service.IBkTaskService;
import com.backup.common.utils.poi.ExcelUtil;
import com.backup.common.core.page.TableDataInfo;

/**
 * 备份任务Controller
 *
 * @author author
 * @date 2024-10-28
 */
@RestController
@RequestMapping("/server/task")
public class BkTaskController extends BaseController
{
    @Resource
    private IBkTaskService bkTaskService;

    /**
     * 查询备份任务列表
     */
    @PreAuthorize("@ss.hasPermi('server:task:list')")
    @GetMapping("/list")
    public TableDataInfo list(BkTask bkTask)
    {
        startPage();
        List<BkTask> list = bkTaskService.selectBkTaskList(bkTask);
        return getDataTable(list);
    }

    /**
     * 导出备份任务列表
     */
    @PreAuthorize("@ss.hasPermi('server:task:export')")
    @Log(title = "备份任务", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BkTask bkTask)
    {
        List<BkTask> list = bkTaskService.selectBkTaskList(bkTask);
        ExcelUtil<BkTask> util = new ExcelUtil<BkTask>(BkTask.class);
        util.exportExcel(response, list, "备份任务数据");
    }

    /**
     * 获取备份任务详细信息
     */
    @PreAuthorize("@ss.hasPermi('server:task:query')")
    @GetMapping(value = "/{taskId}")
    public AjaxResult getInfo(@PathVariable("taskId") String taskId)
    {
        return success(bkTaskService.selectBkTaskByTaskId(taskId));
    }

    /**
     * 新增备份任务
     */
    @PreAuthorize("@ss.hasPermi('server:task:add')")
    @Log(title = "备份任务", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BkTask bkTask)
    {
        return toAjax(bkTaskService.insertBkTask(bkTask));
    }

    /**
     * 修改备份任务
     */
    @PreAuthorize("@ss.hasPermi('server:task:edit')")
    @Log(title = "备份任务", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BkTask bkTask)
    {
        return toAjax(bkTaskService.updateBkTask(bkTask));
    }

    /**
     * 删除备份任务
     */
    @PreAuthorize("@ss.hasPermi('server:task:remove')")
    @Log(title = "备份任务", businessType = BusinessType.DELETE)
	@DeleteMapping("/{taskIds}")
    public AjaxResult remove(@PathVariable Long[] taskIds)
    {
        return toAjax(bkTaskService.deleteBkTaskByTaskIds(taskIds));
    }

    @PreAuthorize("@ss.hasPermi('server:task:add')")
    @Log(title = "备份任务", businessType = BusinessType.INSERT)
    @PostMapping("/redo")
    public AjaxResult redo(String serverId)
    {
        return toAjax(bkTaskService.redoBkTask(serverId));
    }
}
