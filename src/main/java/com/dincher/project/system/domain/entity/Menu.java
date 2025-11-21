package com.dincher.project.system.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.dincher.framework.web.domain.CommonEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 菜单权限表 sys_menu
 * 
 *
 */
@Data
@TableName("sys_menu")
public class Menu extends CommonEntity
{
    private static final long serialVersionUID = 1L;

    /** 菜单ID */
    @TableId(value = "menu_id", type = IdType.AUTO)
    private Integer menuId;

    /** 菜单名称 */
    @NotBlank(message = "菜单名称不能为空")
    @Size(min = 0, max = 50, message = "菜单名称长度不能超过50个字符")
    @TableField("menu_name")
    private String menuName;

    /** 父菜单名称 */
    @TableField(exist = false)
    private String parentName;

    /** 父菜单ID */
    @TableField("parent_id")
    private Integer parentId;

    /** 显示顺序 */
    @NotBlank(message = "显示顺序不能为空")
    @TableField("order_num")
    private String orderNum;

    /** 路由地址 */
    @Size(min = 0, max = 200, message = "路由地址不能超过200个字符")
    @TableField("path")
    private String path;

    /** 组件路径 */
    @Size(min = 0, max = 200, message = "组件路径不能超过255个字符")
    @TableField("component")
    private String component;

    /** 是否为外链（0是 1否） */
    @TableField("is_frame")
    private String isFrame;

    /** 是否缓存（0缓存 1不缓存） */
    @TableField("is_cache")
    private String isCache;

    /** 类型（M目录 C菜单 F按钮） */
    @NotBlank(message = "菜单类型不能为空")
    @TableField("menu_type")
    private String menuType;

    /** 显示状态（0显示 1隐藏） */
    @TableField("visible")
    private String visible;
    
    /** 菜单状态（0显示 1隐藏） */
    @TableField("status")
    private String status;

    /** 权限字符串 */
    @Size(min = 0, max = 100, message = "权限标识长度不能超过100个字符")
    @TableField("perms")
    private String perms;

    /** 菜单图标 */
    @TableField("icon")
    private String icon;

    /** 子菜单 */
    @TableField(exist = false)
    private List<Menu> children = new ArrayList<Menu>();

    /** 备注 */
    @TableField(value = "remark")
    private String remark;

    /** 请求参数 */
    @TableField(exist = false)
    private Map<String, Object> params;

    public Map<String, Object> getParams()
    {
        if (params == null)
        {
            params = new HashMap<>();
        }
        return params;
    }

    /**
     *是否删除,0-未删除，1-已删除
     */
    @TableField(value = "delete_flag",fill = FieldFill.INSERT)
    @ApiModelProperty(value = "是否删除,0-未删除，1-已删除")
    private Integer deleteFlag;

}
