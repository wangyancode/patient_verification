package com.dincher.project.scm.domain.DTO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 配送单查询实体类
 *
 * @author wangbin
 * @date 2023-12-06 11:45:08
 */
@Data
public class DeliveryOrderDetailGroupQueryDTO {

    @ApiModelProperty(value = "验收状态1待验收2已验收")
    private String drugCheckStatus;

    @ApiModelProperty(value = "箱号")
    private List<String> boxNos;

}

