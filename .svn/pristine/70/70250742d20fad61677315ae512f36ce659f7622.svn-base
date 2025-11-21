package com.dincher.project.system.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.dincher.framework.aspectj.lang.annotation.Excel;
import com.dincher.framework.aspectj.lang.annotation.Excel.ColumnType;
import com.dincher.framework.web.domain.CommonEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 参数配置表 sys_config
 * 
 *
 */
@Data
@TableName("sys_config")
public class Config extends CommonEntity
{
    private static final long serialVersionUID = 1L;

    /** 参数主键 */
    @Excel(name = "参数主键", cellType = ColumnType.NUMERIC)
    @TableId(value = "config_id", type = IdType.AUTO)
    private Integer configId;

    /** 参数名称 */
    @Excel(name = "参数名称")
    @TableField("config_name")
    private String configName;

    /** 参数键名 */
    @Excel(name = "参数键名")
    @TableField("config_key")
    private String configKey;

    /** 参数键值 */
    @Excel(name = "参数键值")
    @TableField("config_value")
    private String configValue;

    /** 系统内置（Y是 N否） */
    @Excel(name = "系统内置", readConverterExp = "Y=是,N=否")
    @TableField("config_type")
    private String configType;

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
