package com.dincher.project.scm.domain.DTO;

import com.baomidou.mybatisplus.annotation.TableField;
import com.dincher.project.scm.domain.vo.DeliveryOrderDetailsVO;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * 单独验收实体类
 *
 * @author wangbin
 * @date 2023-12-06 11:45:08
 */
@Data
public class SynchornizeDataRequestDTO {

    @ApiModelProperty(value = "箱号")
    private String boxNo;

    @ApiModelProperty(value = "配送组号")
    private String groupNo;

    @ApiModelProperty(value = "配送时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date distributeTime;

    @ApiModelProperty(value = "配送企业编码")
    private String distributeCode;

    @ApiModelProperty(value = "明细")
    private List<SynchornizeDataDetailsRequestDTO> details;



}

