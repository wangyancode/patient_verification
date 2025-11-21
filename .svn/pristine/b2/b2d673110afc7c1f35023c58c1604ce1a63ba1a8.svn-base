package com.dincher.project.scm.domain.vo;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dincher.project.scm.domain.entity.DeliveryCompany;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 配送单位(scm_delivery_company)业务处理实体类
 *
 * @author wangbin
 * @date 2023-12-06 11:45:05
 */
@Data
@TableName("scm_delivery_company")
public class DeliveryCompanyVO extends DeliveryCompany{

    @TableField(exist = false)
    @ApiModelProperty(value = "密码")
    private String password;
}

