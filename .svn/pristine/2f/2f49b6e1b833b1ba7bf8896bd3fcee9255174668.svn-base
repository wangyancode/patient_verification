package com.dincher.project.scm.domain.vo;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dincher.project.scm.domain.entity.Message;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 消息(scm_message)业务处理实体类
 *
 * @author wangbin
 * @date 2023-12-06 11:45:08
 */
@Data
@TableName("scm_message")
public class MessageVO extends Message{


    @TableField(exist = false)
    @ApiModelProperty(value = "箱号")
    private String boxNo;

    @TableField(exist = false)
    @ApiModelProperty(value = "配送组号")
    private String groupNo;

    @TableField(exist = false)
    @ApiModelProperty(value = "发票号")
    private String invoiceNo;

    @TableField(exist = false)
    @ApiModelProperty(value = "配送时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date distributeTime;

    @TableField(exist = false)
    @ApiModelProperty(value = "配送企业名称")
    private String distributeName;
}

