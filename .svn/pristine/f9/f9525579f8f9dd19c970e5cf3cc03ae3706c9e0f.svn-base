package com.dincher.project.scm.domain.DTO;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 配送单查询返回实体类
 *
 * @author wangbin
 * @date 2023-12-06 11:45:08
 */
@Data
public class DeliveryOrderQueryReturnDTO {

    @TableField("box_no")
    @ApiModelProperty(value = "箱号")
    private String boxNo;

    @TableField("group_no")
    @ApiModelProperty(value = "配送组号")
    private String groupNo;

    @ApiModelProperty(value = "发票号")
    private String invoiceNo;

    @TableField("distribute_time")
    @ApiModelProperty(value = "配送时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date distributeTime;

    @TableField("distribute_code")
    @ApiModelProperty(value = "配送企业编码")
    private String distributeCode;

    @TableField(value = "delete_flag",fill = FieldFill.INSERT)
    @ApiModelProperty(value = "是否删除,0-未删除，1-已删除")
    private Integer deleteFlag;

    @TableField("check_status")
    @ApiModelProperty(value = "验收状态1待验收2验收中3已验收")
    private String checkStatus;

    @TableField("check_datetime")
    @ApiModelProperty(value = "验收时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date checkDatetime;

    @TableField("distribute_name")
    @ApiModelProperty(value = "配送企业名称")
    private String distributeName;
    
}

