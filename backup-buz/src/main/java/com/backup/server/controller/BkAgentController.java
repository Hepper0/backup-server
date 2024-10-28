package com.backup.server.controller;

import java.util.List;
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
import com.backup.server.domain.BkAgent;
import com.backup.server.service.IBkAgentService;
import com.backup.common.utils.poi.ExcelUtil;
import com.backup.common.core.page.TableDataInfo;

/**
 * 代理Controller
 *
 * @author author
 * @date 2024-10-27
 */
@RestController
@RequestMapping("/server/agent")
public class BkAgentController extends BaseController
{
    @Autowired
    private IBkAgentService bkAgentService;

    /**
     * 查询代理列表
     */
    @PreAuthorize("@ss.hasPermi('server:agent:list')")
    @GetMapping("/list")
    public TableDataInfo list(BkAgent bkAgent)
    {
        startPage();
        List<BkAgent> list = bkAgentService.selectBkAgentList(bkAgent);
        return getDataTable(list);
    }

    /**
     * 导出代理列表
     */
    @PreAuthorize("@ss.hasPermi('server:agent:export')")
    @Log(title = "代理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BkAgent bkAgent)
    {
        List<BkAgent> list = bkAgentService.selectBkAgentList(bkAgent);
        ExcelUtil<BkAgent> util = new ExcelUtil<BkAgent>(BkAgent.class);
        util.exportExcel(response, list, "代理数据");
    }

    /**
     * 获取代理详细信息
     */
    @PreAuthorize("@ss.hasPermi('server:agent:query')")
    @GetMapping(value = "/{agentId}")
    public AjaxResult getInfo(@PathVariable("agentId") Long agentId)
    {
        return success(bkAgentService.selectBkAgentByAgentId(agentId));
    }

    /**
     * 新增代理
     */
    @PreAuthorize("@ss.hasPermi('server:agent:add')")
    @Log(title = "代理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BkAgent bkAgent)
    {
        return toAjax(bkAgentService.insertBkAgent(bkAgent));
    }

    /**
     * 修改代理
     */
    @PreAuthorize("@ss.hasPermi('server:agent:edit')")
    @Log(title = "代理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BkAgent bkAgent)
    {
        return toAjax(bkAgentService.updateBkAgent(bkAgent));
    }

    /**
     * 删除代理
     */
    @PreAuthorize("@ss.hasPermi('server:agent:remove')")
    @Log(title = "代理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{agentIds}")
    public AjaxResult remove(@PathVariable Long[] agentIds)
    {
        return toAjax(bkAgentService.deleteBkAgentByAgentIds(agentIds));
    }
}
