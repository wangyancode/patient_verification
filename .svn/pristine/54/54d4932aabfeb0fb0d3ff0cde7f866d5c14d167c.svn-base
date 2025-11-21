package com.dincher.project.scm.domain.DTO;

import com.baomidou.mybatisplus.annotation.TableField;
import com.dincher.project.scm.domain.vo.DeliveryOrderDetailsVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 单独验收实体类
 *
 * @author wangbin
 * @date 2023-12-06 11:45:08
 */
@Data
public class MergeCheckDrugsRequestDTO {

    @ApiModelProperty(value = "配送组号")
    private String groupNo;

    @ApiModelProperty(value = "明细")
    private List<DeliveryOrderDetailsVO> deliveryOrderDetailsVOs;

    @ApiModelProperty(value = "是否验收完0完1未完")
    private String isChecked;

}

