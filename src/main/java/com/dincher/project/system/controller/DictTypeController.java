package com.dincher.project.system.controller;

import com.dincher.common.constant.UserConstants;
import com.dincher.common.utils.SecurityUtils;
import com.dincher.common.utils.poi.revise.ReviseExcelUtil;
import com.dincher.framework.web.controller.BaseController;
import com.dincher.framework.web.domain.AjaxResult;
import com.dincher.framework.web.domain.ExcelEntity;
import com.dincher.framework.web.page.TableDataInfo;
import com.dincher.project.system.domain.entity.DictType;
import com.dincher.project.system.service.DictTypeService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据字典信息
 *
 *
 */
@RestController
@RequestMapping("/system/dict/type")
public class DictTypeController extends BaseController
{
    @Resource
    private DictTypeService dictTypeService;

    @GetMapping("/list")
    public TableDataInfo list(DictType dictType)
    {
        startPage();
        List<DictType> list = dictTypeService.selectDictTypeList(dictType);
        return getDataTable(list);
    }

    @PostMapping("/export")
    public void export(HttpServletResponse response, DictType dictType)
    {
        List<DictType> list = dictTypeService.selectDictTypeList(dictType);
        ReviseExcelUtil<DictType> util = new ReviseExcelUtil<DictType>(DictType.class);
        ExcelEntity excel = new ExcelEntity();
        excel.setFileName("字典类型");
        //工作表名称
        List<String> sheetNames = new ArrayList<String>();
        sheetNames.add("字典类型");
        excel.setSheetNames(sheetNames);
        //需要导出的数据
        List<List<DictType>> data = new ArrayList<List<DictType>>();
        data.add(list);
        util.exportExcel(data, excel, response);
    }

    /**
     * 查询字典类型详细
     */
    @GetMapping(value = "/{dictId}")
    public AjaxResult getInfo(@PathVariable Integer dictId)
    {
        return AjaxResult.success(dictTypeService.selectDictTypeById(dictId));
    }

    /**
     * 新增字典类型
     */
    @PostMapping
    public AjaxResult add(@Validated @RequestBody DictType dict)
    {
        if (UserConstants.NOT_UNIQUE.equals(dictTypeService.checkDictTypeUnique(dict)))
        {
            return AjaxResult.error("新增字典'" + dict.getDictName() + "'失败，字典类型已存在");
        }
        dict.setCreateBy(SecurityUtils.getLoginUser().getUser().getUserId());
        return toAjax(dictTypeService.insertDictType(dict));
    }

    /**
     * 修改字典类型
     */
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody DictType dict)
    {
        if (UserConstants.NOT_UNIQUE.equals(dictTypeService.checkDictTypeUnique(dict)))
        {
            return AjaxResult.error("修改字典'" + dict.getDictName() + "'失败，字典类型已存在");
        }
        dict.setUpdateBy(SecurityUtils.getLoginUser().getUser().getUserId());
        return toAjax(dictTypeService.updateDictType(dict));
    }

    /**
     * 删除字典类型
     */
    @DeleteMapping("/{dictIds}")
    public AjaxResult remove(@PathVariable Integer[] dictIds)
    {
        return toAjax(dictTypeService.deleteDictTypeByIds(dictIds));
    }

    /**
     * 刷新字典缓存
     */
    @DeleteMapping("/refreshCache")
    public AjaxResult refreshCache()
    {
        dictTypeService.resetDictCache();
        return AjaxResult.success();
    }

    /**
     * 获取字典选择框列表
     */
    @GetMapping("/optionselect")
    public AjaxResult optionselect()
    {
        List<DictType> dictTypes = dictTypeService.selectDictTypeAll();
        return AjaxResult.success(dictTypes);
    }
}
