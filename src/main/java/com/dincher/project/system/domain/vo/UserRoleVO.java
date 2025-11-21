package com.dincher.project.system.domain.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dincher.project.system.domain.entity.UserRole;
import lombok.Data;

/**
 * 用户和角色关联信息(sys_user_role)业务处理实体类
 *
 * @author wangxiao
 * @date 2023-07-25 14:03:26
 */
@Data
@TableName("sys_user_role")
public class UserRoleVO extends UserRole{
    
}

