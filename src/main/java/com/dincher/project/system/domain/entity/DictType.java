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
 * 字典类型表 sys_dict_type
 * 
 *
 */
@Data
@TableName("sys_dict_type")
public class DictType extends CommonEntity
{
    private static final long serialVersionUID = 1L;

    /** 字典主键 */
    @Excel(name = "字典主键", cellType = ColumnType.NUMERIC)
    @TableId(value = "dict_id", type = IdType.AUTO)
    private Integer dictId;

    /** 字典名称 */
    @Excel(name = "字典名称")
    @NotBlank(message = "字典名称不能为空")
    @Size(min = 0, max = 100, message = "字典类型名称长度不能超过100个字符")
    @TableField("dict_name")
    private String dictName;

    /** 字典类型 */
    @Excel(name = "字典类型")
    @NotBlank(message = "字典类型不能为空")
    @Size(min = 0, max = 100, message = "字典类型类型长度不能超过100个字符")
    @TableField("dict_type")
    private String dictType;

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
