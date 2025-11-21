package com.dincher.project.scm.domain.vo;

import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dincher.project.scm.domain.entity.DeliveryOrderDetails;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 配送单明细(scm_delivery_order_details)业务处理实体类
 *
 * @author wangbin
 * @date 2023-12-06 15:36:57
 */
@Data
@TableName("scm_delivery_order_details")
public class DeliveryOrderDetailsVO extends DeliveryOrderDetails{

    @TableField(exist = false)
    @ApiModelProperty(value = "箱数")
    private Integer boxCount;

    @TableField(exist = false)
    @ApiModelProperty(value = "配送组号")
    private String groupNo;

    @TableField(exist = false)
    @ApiModelProperty(value = "总数量")
    private Integer allDistributeCount;

    @TableField(exist = false)
    @ApiModelProperty(value = "是否在效期内 0是1否")
    private Integer isInValidity;

}

