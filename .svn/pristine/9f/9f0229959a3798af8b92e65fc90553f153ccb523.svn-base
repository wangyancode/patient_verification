package com.dincher.project.system.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Description 修改用户状态请求参数实体
 * @Author WangXiao
 * @Date 2023/7/26
 */
@Data
public class ChangeStatusDTO {

    @NotBlank(message = "用户账号主键id不能为空")
    @ApiModelProperty(value = "用户账号主键id")
    private Integer userId;

    @NotBlank(message = "状态不能为空")
    @ApiModelProperty(value = "启用状态 0 正常 1 停用")
    private String userStatus;

}
