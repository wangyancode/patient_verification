package com.dincher.framework.web.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @Description 请求实体类
 * @Author WangXiao
 * @Date 2022/6/22
 */
@Data
public class RequestQueryBody<T> {

    @ApiModelProperty(value = "当前页数")
    private Integer pageNum;
    @ApiModelProperty(value = "每页显示记录数")
    private Integer pageSize;
    @Valid
    @ApiModelProperty(value = "请求参数")
    private T param;
}
