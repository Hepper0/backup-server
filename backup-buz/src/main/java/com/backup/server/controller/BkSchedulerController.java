package com.backup.server.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.backup.server.domain.BkAgentConfig;
import com.backup.server.domain.BkSchedulerBroadcast;
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
import com.backup.server.domain.BkScheduler;
import com.backup.server.service.IBkSchedulerService;
import com.backup.common.utils.poi.ExcelUtil;
import com.backup.common.core.page.TableDataInfo;

/**
 * 备份计划Controller
 *
 * @author author
 * @date 2024-10-28
 */
@RestController
@RequestMapping("/server/scheduler")
public class BkSchedulerController extends BaseController
{
    @Autowired
    private IBkSchedulerService bkSchedulerService;

    /**
     * 查询备份计划列表
     */
    @PreAuthorize("@ss.hasPermi('server:scheduler:list')")
    @GetMapping("/list")
    public TableDataInfo list(BkScheduler bkScheduler)
    {
        startPage();
        List<BkScheduler> list = bkSchedulerService.selectBkSchedulerList(bkScheduler);
        return getDataTable(list);
    }

    /**
     * 导出备份计划列表
     */
    @PreAuthorize("@ss.hasPermi('server:scheduler:export')")
    @Log(title = "备份计划", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BkScheduler bkScheduler)
    {
        List<BkScheduler> list = bkSchedulerService.selectBkSchedulerList(bkScheduler);
        ExcelUtil<BkScheduler> util = new ExcelUtil<BkScheduler>(BkScheduler.class);
        util.exportExcel(response, list, "备份计划数据");
    }

    /**
     * 获取备份计划详细信息
     */
    @PreAuthorize("@ss.hasPermi('server:scheduler:query')")
    @GetMapping(value = "/{schedulerId}")
    public AjaxResult getInfo(@PathVariable("schedulerId") Long schedulerId)
    {
        return success(bkSchedulerService.selectBkSchedulerBySchedulerId(schedulerId));
    }

    /**
     * 新增备份计划
     */
    @PreAuthorize("@ss.hasPermi('server:scheduler:add')")
    @Log(title = "备份计划", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BkScheduler bkScheduler)
    {
        return toAjax(bkSchedulerService.insertBkScheduler(bkScheduler));
    }

    /**
     * 修改备份计划
     */
    @PreAuthorize("@ss.hasPermi('server:scheduler:edit')")
    @Log(title = "备份计划", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BkScheduler bkScheduler)
    {
        return toAjax(bkSchedulerService.updateBkScheduler(bkScheduler));
    }

    /**
     * 删除备份计划
     */
    @PreAuthorize("@ss.hasPermi('server:scheduler:remove')")
    @Log(title = "备份计划", businessType = BusinessType.DELETE)
	@DeleteMapping("/{schedulerIds}")
    public AjaxResult remove(@PathVariable Long[] schedulerIds)
    {
        return toAjax(bkSchedulerService.deleteBkSchedulerBySchedulerIds(schedulerIds));
    }

    /**
     * 广播代理配置
     */
    @PreAuthorize("@ss.hasPermi('server:scheduler:broadcast')")
    @Log(title = "备份计划", businessType = BusinessType.OTHER)
    @PostMapping
    public AjaxResult broadcast(@RequestBody BkScheduler bkScheduler)
    {
        bkSchedulerService.broadcast(bkScheduler);
        return success();
    }

    /**
     * 局部广播代理配置
     */
    @PreAuthorize("@ss.hasPermi('server:scheduler:broadcast')")
    @Log(title = "备份计划", businessType = BusinessType.OTHER)
    @PostMapping
    public AjaxResult broadcast(@RequestBody BkSchedulerBroadcast bkSchedulerBroadcast)
    {
        bkSchedulerService.broadcast(bkSchedulerBroadcast);
        return success();
    }
}
