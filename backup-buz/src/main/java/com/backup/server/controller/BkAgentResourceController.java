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
import com.backup.server.domain.BkAgentResource;
import com.backup.server.service.IBkAgentResourceService;
import com.backup.common.utils.poi.ExcelUtil;
import com.backup.common.core.page.TableDataInfo;

/**
 * 备份资源Controller
 *
 * @author author
 * @date 2024-10-29
 */
@RestController
@RequestMapping("/server/resource")
public class BkAgentResourceController extends BaseController
{
    @Autowired
    private IBkAgentResourceService bkAgentResourceService;

    /**
     * 查询备份资源列表
     */
    @PreAuthorize("@ss.hasPermi('server:resource:list')")
    @GetMapping("/list")
    public TableDataInfo list(BkAgentResource bkAgentResource)
    {
        String agentIP = bkAgentResource.getAgentIP();
        List<BkAgentResource> list;
        if (agentIP != null){
            return getDataTable(bkAgentResourceService.selectBkAgentResourceListByIP(agentIP));
        } else {
            startPage();
            list = bkAgentResourceService.selectBkAgentResourceList(bkAgentResource);
            return getDataTable(list);
        }
    }

    /**
     * 导出备份资源列表
     */
    @PreAuthorize("@ss.hasPermi('server:resource:export')")
    @Log(title = "备份资源", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BkAgentResource bkAgentResource)
    {
        List<BkAgentResource> list = bkAgentResourceService.selectBkAgentResourceList(bkAgentResource);
        ExcelUtil<BkAgentResource> util = new ExcelUtil<BkAgentResource>(BkAgentResource.class);
        util.exportExcel(response, list, "备份资源数据");
    }

    /**
     * 获取备份资源详细信息
     */
    @PreAuthorize("@ss.hasPermi('server:resource:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(bkAgentResourceService.selectBkAgentResourceById(id));
    }

    /**
     * 新增备份资源
     */
    @PreAuthorize("@ss.hasPermi('server:resource:add')")
    @Log(title = "备份资源", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BkAgentResource bkAgentResource)
    {
        return toAjax(bkAgentResourceService.insertBkAgentResource(bkAgentResource));
    }

    /**
     * 修改备份资源
     */
    @PreAuthorize("@ss.hasPermi('server:resource:edit')")
    @Log(title = "备份资源", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BkAgentResource bkAgentResource)
    {
        return toAjax(bkAgentResourceService.updateBkAgentResource(bkAgentResource));
    }

    /**
     * 删除备份资源
     */
    @PreAuthorize("@ss.hasPermi('server:resource:remove')")
    @Log(title = "备份资源", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(bkAgentResourceService.deleteBkAgentResourceByIds(ids));
    }

    @PreAuthorize("@ss.hasPermi('server:resource:edit')")
    @PostMapping
    public AjaxResult send2Agent(String ip) {
        return toAjax(bkAgentResourceService.send2Agent(ip));
    }
}
