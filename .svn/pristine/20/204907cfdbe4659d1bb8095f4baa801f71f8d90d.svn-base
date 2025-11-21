package com.dincher.framework.web.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: 权限过滤入参实体类
 * @author: chensaineng
 * @since: 2022-12-08 14:55:06
 */
@Data
public class ParamsEntity implements Serializable {

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
