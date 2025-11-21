package com.dincher.project.system.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description 登录返回实体
 * @Author WangXiao
 * @Date 2022/6/29
 */
@Data
public class TokenVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("token令牌")
    private String token;
}
