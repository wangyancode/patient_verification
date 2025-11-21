package com.dincher.project.scm.domain.DTO;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * 配送单查询实体类
 *
 * @author wangbin
 * @date 2023-12-06 11:45:08
 */
@Data
public class DeliveryOrderQueryDTO {

    @ApiModelProperty(value = "查询条件")
    private String queryCriteria;

    @ApiModelProperty(value = "验收状态1待验收2验收中3已验收")
    private String checkStatus;

    @ApiModelProperty(value = "配送企业编码")
    private List<String> distributeCodes;

    @ApiModelProperty(value = "配送开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date distributeTimeStart;

    @ApiModelProperty(value = "配送结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date distributeTimeEnd;
}

