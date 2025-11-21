package com.dincher.project.system.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dincher.project.system.domain.entity.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 用户账号信息(sys_user)业务处理实体类
 *
 * @author wangxiao
 * @date 2023-07-25 14:03:28
 */
@Data
@TableName("sys_user")
public class UserVO extends User{

    @TableField(exist = false)
    @ApiModelProperty(value = "角色名称")
    private String roleNames;

    @TableField(exist = false)
    @ApiModelProperty(value = "角色主键")
    private Integer[] roleIds;

    @TableField(exist = false)
    @ApiModelProperty(value = "角色主键")
    private Integer roleId;

    @TableField(exist = false)
    @ApiModelProperty(value = "性别")
    private String userSexName;
    @ApiModelProperty(value = "头像")
    private String avatar;
    @ApiModelProperty(value = "创建人")
    @TableField(exist = false)
    private String createByName;

    @TableField(exist = false)
    @ApiModelProperty(value = "职称名称")
    private String positionalTitleName;

    @TableField(exist = false)
    @ApiModelProperty(value = "所属区域名称")
    private String regionName;

    @TableField(exist = false)
    @ApiModelProperty(value = "所属区域值：对应字典值(科室/病区代码)")
    private List<String> regionValue;

    @TableField(exist = false)
    @ApiModelProperty(value = "所属区域信息")
    private List<Map<String,Object>> regionList;

    @TableField(exist = false)
    @ApiModelProperty(value = "复核人信息")
    private UserVO reChecker;

}

