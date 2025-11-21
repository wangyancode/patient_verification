package com.dincher.project.system.domain.entity;

import lombok.Data;

/**
 * 角色和菜单关联 sys_role_menu
 * 
 *
 */
@Data
public class RoleMenu
{
    /** 角色ID */
    private Integer roleId;

    /** 菜单ID */
    private Integer menuId;

}
