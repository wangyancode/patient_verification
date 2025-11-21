package com.dincher.project.scm.domain.vo;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dincher.project.scm.domain.entity.DeliveryOrder;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 配送单(scm_delivery_order)业务处理实体类
 *
 * @author wangbin
 * @date 2023-12-06 11:45:08
 */
@Data
@TableName("scm_delivery_order")
public class DeliveryOrderVO extends DeliveryOrder{

    @TableField(exist = false)
    @ApiModelProperty(value = "是否验收完0完1未完")
    private String isChecked;
}

