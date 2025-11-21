package com.dincher.project.system.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Description 重置卫健委用户密码请求参数实体
 * @Author WangXiao
 * @Date 2023/7/26
 */
@Data
public class ResetPwdDTO {

    @NotBlank(message = "用户账号主键id不能为空")
    @ApiModelProperty(value = "用户账号主键id")
    private Integer userId;

    @NotBlank(message = "密码不能为空")
    @ApiModelProperty(value = "密码")
    private String userPassword;

}
