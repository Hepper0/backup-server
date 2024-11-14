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
import com.backup.server.domain.BkAgentScheduler;
import com.backup.server.service.IBkAgentSchedulerService;
import com.backup.common.utils.poi.ExcelUtil;
import com.backup.common.core.page.TableDataInfo;

/**
 * 备份计划代理配置Controller
 *
 * @author author
 * @date 2024-11-02
 */
@RestController
@RequestMapping("/server/agent-scheduler")
public class BkAgentSchedulerController extends BaseController
{
    @Resource
    private IBkAgentSchedulerService bkAgentSchedulerService;

    /**
     * 查询备份计划代理配置列表
     */
    @PreAuthorize("@ss.hasPermi('server:scheduler:list')")
    @GetMapping("/list")
    public TableDataInfo list(BkAgentScheduler bkAgentScheduler)
    {
        startPage();
        List<BkAgentScheduler> list = bkAgentSchedulerService.selectBkAgentSchedulerList(bkAgentScheduler);
        return getDataTable(list);
    }

    /**
     * 导出备份计划代理配置列表
     */
    @PreAuthorize("@ss.hasPermi('server:scheduler:export')")
    @Log(title = "备份计划代理配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BkAgentScheduler bkAgentScheduler)
    {
        List<BkAgentScheduler> list = bkAgentSchedulerService.selectBkAgentSchedulerList(bkAgentScheduler);
        ExcelUtil<BkAgentScheduler> util = new ExcelUtil<BkAgentScheduler>(BkAgentScheduler.class);
        util.exportExcel(response, list, "备份计划代理配置数据");
    }

    /**
     * 获取备份计划代理配置详细信息
     */
    @PreAuthorize("@ss.hasPermi('server:scheduler:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(bkAgentSchedulerService.selectBkAgentSchedulerById(id));
    }

    /**
     * 新增备份计划代理配置
     */
    @PreAuthorize("@ss.hasPermi('server:scheduler:add')")
    @Log(title = "备份计划代理配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BkAgentScheduler bkAgentScheduler)
    {
        return toAjax(bkAgentSchedulerService.insertBkAgentScheduler(bkAgentScheduler));
    }

    /**
     * 修改备份计划代理配置
     */
    @PreAuthorize("@ss.hasPermi('server:scheduler:edit')")
    @Log(title = "备份计划代理配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BkAgentScheduler bkAgentScheduler)
    {
        return toAjax(bkAgentSchedulerService.updateBkAgentScheduler(bkAgentScheduler));
    }

    /**
     * 删除备份计划代理配置
     */
    @PreAuthorize("@ss.hasPermi('server:scheduler:remove')")
    @Log(title = "备份计划代理配置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(bkAgentSchedulerService.deleteBkAgentSchedulerByIds(ids));
    }

    @Log(title = "备份计划代理配置", businessType = BusinessType.OTHER)
    @DeleteMapping("/sync")
    public AjaxResult syncScheduler(@RequestBody BkAgentScheduler bkAgentScheduler)
    {
        return toAjax(bkAgentSchedulerService.syncBkAgentScheduler(bkAgentScheduler));
    }
}
