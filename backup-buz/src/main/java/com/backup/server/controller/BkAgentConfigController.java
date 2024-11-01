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
import com.backup.server.domain.BkAgentConfig;
import com.backup.server.service.IBkAgentConfigService;
import com.backup.common.utils.poi.ExcelUtil;
import com.backup.common.core.page.TableDataInfo;

/**
 * 代理配置Controller
 *
 * @author author
 * @date 2024-11-02
 */
@RestController
@RequestMapping("/system/config")
public class BkAgentConfigController extends BaseController
{
    @Autowired
    private IBkAgentConfigService bkAgentConfigService;

    /**
     * 查询代理配置列表
     */
    @PreAuthorize("@ss.hasPermi('system:config:list')")
    @GetMapping("/list")
    public TableDataInfo list(BkAgentConfig bkAgentConfig)
    {
        startPage();
        List<BkAgentConfig> list = bkAgentConfigService.selectBkAgentConfigList(bkAgentConfig);
        return getDataTable(list);
    }

    /**
     * 导出代理配置列表
     */
    @PreAuthorize("@ss.hasPermi('system:config:export')")
    @Log(title = "代理配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BkAgentConfig bkAgentConfig)
    {
        List<BkAgentConfig> list = bkAgentConfigService.selectBkAgentConfigList(bkAgentConfig);
        ExcelUtil<BkAgentConfig> util = new ExcelUtil<BkAgentConfig>(BkAgentConfig.class);
        util.exportExcel(response, list, "代理配置数据");
    }

    /**
     * 获取代理配置详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:config:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(bkAgentConfigService.selectBkAgentConfigById(id));
    }

    /**
     * 新增代理配置
     */
    @PreAuthorize("@ss.hasPermi('system:config:add')")
    @Log(title = "代理配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BkAgentConfig bkAgentConfig)
    {
        return toAjax(bkAgentConfigService.insertBkAgentConfig(bkAgentConfig));
    }

    /**
     * 修改代理配置
     */
    @PreAuthorize("@ss.hasPermi('system:config:edit')")
    @Log(title = "代理配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BkAgentConfig bkAgentConfig)
    {
        return toAjax(bkAgentConfigService.updateBkAgentConfig(bkAgentConfig));
    }

    /**
     * 删除代理配置
     */
    @PreAuthorize("@ss.hasPermi('system:config:remove')")
    @Log(title = "代理配置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(bkAgentConfigService.deleteBkAgentConfigByIds(ids));
    }
}
