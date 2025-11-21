package com.dincher.framework.web.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.dincher.framework.aspectj.lang.annotation.Excel;
import com.dincher.framework.aspectj.lang.annotation.Excel.Type;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description 自定义基类，抽取实体类公共属性，不使用BaseEntity
 * @Author WangXiao
 * @Date 2022/6/22
 */
@Data
public class CommonEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *创建人
     */
    @TableField(value ="create_by",fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建人")
    private Integer createBy;

    /**
     *创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value ="create_datetime",fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    @Excel(name = "创建时间",width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss", type = Type.ALL)
    private Date createDatetime;

    /**
     *最后修改人
     */
    @TableField(value ="update_by",fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "最后修改人")
    private Integer updateBy;

    /**
     *最后修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value ="update_datetime",fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "最后修改时间")
    @Excel(name = "最后修改时间",width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss", type = Type.ALL)
    private Date updateDatetime;

    @TableField(exist = false)
    @JsonIgnore
    @ApiModelProperty(value = "查询权限过滤参数")
    private Map<String, Object> params;

    public Map<String, Object> getParams()
    {
        if (params == null)
        {
            params = new HashMap<>();
        }
        return params;
    }
}
