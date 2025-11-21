package com.dincher.project.system.domain.vo;

import com.dincher.project.system.domain.entity.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * @Description 登录后需要的信息返回实体
 * @Author WangXiao
 * @Date 2022/6/29
 */
@Data
public class LoginInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户信息")
    private User user;

    @ApiModelProperty("角色权限标识")
    private Set<String> rolePermissions;

    @ApiModelProperty("菜单权限标识")
    private Set<String> menuPermissions;
}
