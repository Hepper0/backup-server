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
 * 【请填写功能名称】Controller
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
     * 查询【请填写功能名称】列表
     */
    @PreAuthorize("@ss.hasPermi('system:scheduler:list')")
    @GetMapping("/list")
    public TableDataInfo list(BkAgentScheduler bkAgentScheduler)
    {
        startPage();
        List<BkAgentScheduler> list = bkAgentSchedulerService.selectBkAgentSchedulerList(bkAgentScheduler);
        return getDataTable(list);
    }

    /**
     * 导出【请填写功能名称】列表
     */
    @PreAuthorize("@ss.hasPermi('system:scheduler:export')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BkAgentScheduler bkAgentScheduler)
    {
        List<BkAgentScheduler> list = bkAgentSchedulerService.selectBkAgentSchedulerList(bkAgentScheduler);
        ExcelUtil<BkAgentScheduler> util = new ExcelUtil<BkAgentScheduler>(BkAgentScheduler.class);
        util.exportExcel(response, list, "【请填写功能名称】数据");
    }

    /**
     * 获取【请填写功能名称】详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:scheduler:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(bkAgentSchedulerService.selectBkAgentSchedulerById(id));
    }

    /**
     * 新增【请填写功能名称】
     */
    @PreAuthorize("@ss.hasPermi('system:scheduler:add')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BkAgentScheduler bkAgentScheduler)
    {
        return toAjax(bkAgentSchedulerService.insertBkAgentScheduler(bkAgentScheduler));
    }

    /**
     * 修改【请填写功能名称】
     */
    @PreAuthorize("@ss.hasPermi('system:scheduler:edit')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BkAgentScheduler bkAgentScheduler)
    {
        return toAjax(bkAgentSchedulerService.updateBkAgentScheduler(bkAgentScheduler));
    }

    /**
     * 删除【请填写功能名称】
     */
    @PreAuthorize("@ss.hasPermi('system:scheduler:remove')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(bkAgentSchedulerService.deleteBkAgentSchedulerByIds(ids));
    }
}
