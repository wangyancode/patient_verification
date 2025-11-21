package com.dincher.project.system.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.dincher.framework.aspectj.lang.annotation.Excel;
import com.dincher.framework.aspectj.lang.annotation.Excel.ColumnType;
import com.dincher.framework.web.domain.CommonEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 字典数据表 sys_dict_data
 * 
 *
 */
@Data
@TableName("sys_dict_data")
public class DictData extends CommonEntity
{
    private static final long serialVersionUID = 1L;

    /** 字典编码 */
    @Excel(name = "字典编码", cellType = ColumnType.NUMERIC)
    @TableId(value = "dict_code", type = IdType.AUTO)
    private Integer dictCode;

    /** 字典排序 */
    @Excel(name = "字典排序", cellType = ColumnType.NUMERIC)
    @TableField("dict_sort")
    private Integer dictSort;

    /** 字典标签 */
    @Excel(name = "字典标签")
    @NotBlank(message = "字典标签不能为空")
    @Size(min = 0, max = 100, message = "字典标签长度不能超过100个字符")
    @TableField("dict_label")
    private String dictLabel;

    /** 字典键值 */
    @Excel(name = "字典键值")
    @NotBlank(message = "字典键值不能为空")
    @Size(min = 0, max = 100, message = "字典键值长度不能超过100个字符")
    @TableField("dict_value")
    private String dictValue;

    /** 字典类型 */
    @Excel(name = "字典类型")
    @NotBlank(message = "字典类型不能为空")
    @Size(min = 0, max = 100, message = "字典类型长度不能超过100个字符")
    @TableField("dict_type")
    private String dictType;

    /** 样式属性（其他样式扩展） */
    @Size(min = 0, max = 100, message = "样式属性长度不能超过100个字符")
    @TableField("css_class")
    private String cssClass;

    /** 表格字典样式 */
    @TableField("list_class")
    private String listClass;

    /** 是否默认（Y是 N否） */
    @Excel(name = "是否默认", readConverterExp = "Y=是,N=否")
    @TableField("is_default")
    private String isDefault;

    /** 状态（0正常 1停用） */
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    @TableField("status")
    private String status;


    /** 备注 */
    @TableField(value = "remark")
    private String remark;

    /**
     *是否删除,0-未删除，1-已删除
     */
    @TableField(value = "delete_flag",fill = FieldFill.INSERT)
    @ApiModelProperty(value = "是否删除,0-未删除，1-已删除")
    private Integer deleteFlag;

}
