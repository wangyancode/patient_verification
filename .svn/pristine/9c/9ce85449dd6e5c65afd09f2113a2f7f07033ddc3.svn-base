package com.dincher.project.scm.domain.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.dincher.framework.web.domain.CommonEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 配送单位(scm_delivery_company)数据库映射实体类
 *
 * @author wangbin
 * @date 2023-12-06 11:45:04
 */
@Data
public class DeliveryCompany extends CommonEntity{ 

    @TableId(value = "company_id", type = IdType.AUTO)
    @ApiModelProperty(value = "单位id")
    private Integer companyId;

    @TableField("token")
    @ApiModelProperty(value = "token")
    private String token;
    
    @TableField("distribute_name")
    @ApiModelProperty(value = "配送企业名称")
    private String distributeName;
    
    @TableField("distribute_code")
    @ApiModelProperty(value = "配送企业编码")
    private String distributeCode;

    @TableField("status")
    @ApiModelProperty(value = "状态（0正常 1停用）")
    private String status;
    
    @TableField(value = "delete_flag",fill = FieldFill.INSERT)
    @ApiModelProperty(value = "是否删除,0-未删除，1-已删除")
    private Integer deleteFlag;
    
}

