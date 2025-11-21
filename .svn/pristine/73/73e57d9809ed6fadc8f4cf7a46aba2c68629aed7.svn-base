package com.dincher.project.system.controller;

import com.dincher.common.constant.UserConstants;
import com.dincher.common.utils.SecurityUtils;
import com.dincher.common.utils.poi.revise.ReviseExcelUtil;
import com.dincher.framework.web.controller.BaseController;
import com.dincher.framework.web.domain.AjaxResult;
import com.dincher.framework.web.domain.ExcelEntity;
import com.dincher.framework.web.page.TableDataInfo;
import com.dincher.project.system.domain.entity.Config;
import com.dincher.project.system.service.ConfigService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 参数配置 信息操作处理
 * 
 *
 */
@RestController
@RequestMapping("/system/config")
public class ConfigController extends BaseController
{
    @Resource
    private ConfigService configService;

    /**
     * 获取参数配置列表
     */
    @GetMapping("/list")
    public TableDataInfo list(Config config)
    {
        startPage();
        List<Config> list = configService.selectConfigList(config);
        return getDataTable(list);
    }

    @PostMapping("/export")
    public void export(HttpServletResponse response, Config config)
    {
        List<Config> list = configService.selectConfigList(config);
        ReviseExcelUtil<Config> util = new ReviseExcelUtil<Config>(Config.class);
        ExcelEntity excel = new ExcelEntity();
        excel.setFileName("参数数据");
        //工作表名称
        List<String> sheetNames = new ArrayList<String>();
        sheetNames.add("参数数据");
        excel.setSheetNames(sheetNames);
        //需要导出的数据
        List<List<Config>> data = new ArrayList<List<Config>>();
        data.add(list);
        util.exportExcel(data, excel, response);
    }

    /**
     * 根据参数编号获取详细信息
     */
    @GetMapping(value = "/{configId}")
    public AjaxResult getInfo(@PathVariable Integer configId)
    {
        return AjaxResult.success(configService.selectConfigById(configId));
    }

    /**
     * 根据参数键名查询参数值
     */
    @GetMapping(value = "/configKey/{configKey}")
    public AjaxResult getConfigKey(@PathVariable String configKey)
    {
        return AjaxResult.success(configService.selectConfigByKey(configKey));
    }

    /**
     * 新增参数配置
     */
    @PostMapping
    public AjaxResult add(@Validated @RequestBody Config config)
    {
        if (UserConstants.NOT_UNIQUE.equals(configService.checkConfigKeyUnique(config)))
        {
            return AjaxResult.error("新增参数'" + config.getConfigName() + "'失败，参数键名已存在");
        }
        config.setCreateBy(SecurityUtils.getLoginUser().getUser().getUserId());
        return toAjax(configService.insertConfig(config));
    }

    /**
     * 修改参数配置
     */
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody Config config)
    {
        if (UserConstants.NOT_UNIQUE.equals(configService.checkConfigKeyUnique(config)))
        {
            return AjaxResult.error("修改参数'" + config.getConfigName() + "'失败，参数键名已存在");
        }
        config.setUpdateBy(SecurityUtils.getLoginUser().getUser().getUserId());
        return toAjax(configService.updateConfig(config));
    }

    /**
     * 删除参数配置
     */
    @DeleteMapping("/{configIds}")
    public AjaxResult remove(@PathVariable Integer[] configIds)
    {
        return toAjax(configService.deleteConfigByIds(configIds));
    }

    /**
     * 清空缓存
     */
    @DeleteMapping("/clearCache")
    public AjaxResult clearCache()
    {
        configService.clearCache();
        return AjaxResult.success();
    }

    /**
     * 刷新参数缓存
     */
    @DeleteMapping("/refreshCache")
    public AjaxResult refreshCache()
    {
        configService.resetConfigCache();
        return AjaxResult.success();
    }
}
