package com.dincher.project.system.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.dincher.framework.aspectj.lang.annotation.Excel;
import com.dincher.framework.aspectj.lang.annotation.Excel.ColumnType;
import com.dincher.framework.web.domain.CommonEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 角色表 sys_role
 * 
 *
 */
@Data
@TableName("sys_role")
public class Role extends CommonEntity
{


    private static final long serialVersionUID = 1L;

    /** 角色ID */
    @Excel(name = "角色序号", cellType = ColumnType.NUMERIC)
    @TableId(value = "role_id", type = IdType.AUTO)
    private Integer roleId;

    /** 角色名称 */
    @Excel(name = "角色名称")
    @NotBlank(message = "角色名称不能为空")
    @Size(min = 0, max = 30, message = "角色名称长度不能超过30个字符")
    @TableField("role_name")
    private String roleName;

    /** 角色权限 */
    @Excel(name = "角色权限")
    @NotBlank(message = "权限字符不能为空")
    @Size(min = 0, max = 100, message = "权限字符长度不能超过100个字符")
    @TableField("role_key")
    private String roleKey;

    /** 角色排序 */
    @Excel(name = "角色排序")
    @NotBlank(message = "显示顺序不能为空")
    @TableField("role_sort")
    private String roleSort;

    /** 数据范围（1：所有数据权限；2：自定义数据权限；3：本部门数据权限；4：本部门及以下数据权限） */
    @Excel(name = "数据范围", readConverterExp = "1=所有数据权限,2=自定义数据权限,3=本部门数据权限,4=本部门及以下数据权限")
    @TableField("data_scope")
    private String dataScope;

    /** 菜单树选择项是否关联显示（ 0：父子不互相关联显示 1：父子互相关联显示） */
    @TableField("menu_check_strictly")
    private boolean menuCheckStrictly;

    /** 部门树选择项是否关联显示（0：父子不互相关联显示 1：父子互相关联显示 ） */
    @TableField("dept_check_strictly")
    private boolean deptCheckStrictly;

    /** 角色状态（0正常 1停用） */
    @Excel(name = "角色状态", readConverterExp = "0=正常,1=停用")
    @TableField("status")
    private String status;

    /**
     *是否删除,0-未删除，1-已删除
     */
    @TableField(value = "delete_flag",fill = FieldFill.INSERT)
    @ApiModelProperty(value = "是否删除,0-未删除，1-已删除")
    private Integer deleteFlag;

    /** 用户是否存在此角色标识 默认不存在 */
    @TableField(exist = false)
    private boolean flag = false;

    /** 菜单组 */
    @TableField(exist = false)
    private Integer[] menuIds;

    /** 部门组（数据权限） */
    @TableField(exist = false)
    private Integer[] deptIds;

    /** 备注 */
    @TableField(value = "remark")
    private String remark;

    @TableField(exist = false)
    private String createByName;

    public Role()
    {

    }

    public Role(Integer roleId)
    {
        this.roleId = roleId;
    }

    public boolean isAdmin()
    {
        return isAdmin(this.roleId);
    }

    public static boolean isAdmin(Integer roleId)
    {
        return roleId != null && 1L == roleId;
    }


}
