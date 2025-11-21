package com.dincher.project.scm.domain.DTO;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dincher.project.scm.domain.entity.HisUnsynchronizedDeliveryOrderDetails;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * his的oracle数据
 *
 * @author wangbin
 * @date 2024-05-07 09:15:39
 */
@Data
public class HisUnsynchronizedOracleDataDTO{

    @ApiModelProperty(value = "配送明细ID，distributeId")
    private String DISTRIBUTIONSERIALID;
}

