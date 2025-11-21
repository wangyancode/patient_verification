package com.dincher.project.system.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.dincher.framework.web.domain.CommonEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户和角色关联信息(sys_user_role)数据库映射实体类
 *
 * @author wangxiao
 * @date 2023-07-25 14:03:25
 */
@Data
public class UserRole extends CommonEntity{ 

    @TableField("user_id")
    @ApiModelProperty(value = "用户账号信息主键id")
    private Integer userId;
    
    @TableField("role_id")
    @ApiModelProperty(value = "角色ID")
    private Integer roleId;
    
    @TableField(value = "delete_flag",fill = FieldFill.INSERT)
    @ApiModelProperty(value = "是否删除,0-未删除，1-已删除")
    private Integer deleteFlag;
    
}

