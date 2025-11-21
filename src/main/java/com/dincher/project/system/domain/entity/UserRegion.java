package com.dincher.project.system.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.dincher.framework.web.domain.CommonEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户和区域(病区或科室)关联信息(sys_user_region)数据库映射实体类
 *
 * @author wangxiao
 * @date 2023-11-08 16:08:02
 */
@Data
public class UserRegion extends CommonEntity{ 

    @TableField("user_id")
    @ApiModelProperty(value = "用户主键id")
    private Integer userId;
    
    @TableField("region_value")
    @ApiModelProperty(value = "区域(科室或病区)字典值")
    private String regionValue;
    
    @TableField(value = "delete_flag",fill = FieldFill.INSERT)
    @ApiModelProperty(value = "是否删除,0-未删除，1-已删除")
    private Integer deleteFlag;
    
}

