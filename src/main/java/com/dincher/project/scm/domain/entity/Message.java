package com.dincher.project.scm.domain.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.dincher.framework.web.domain.CommonEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 消息(scm_message)数据库映射实体类
 *
 * @author wangbin
 * @date 2023-12-06 11:45:08
 */
@Data
public class Message extends CommonEntity{

    @TableId(value = "message_id", type = IdType.AUTO)
    @ApiModelProperty(value = "消息主键id")
    private Integer messageId;

    @TableField("message_content")
    @ApiModelProperty(value = "消息内容 ")
    private String messageContent;

    @TableField("message_code")
    @ApiModelProperty(value = "消息编号")
    private String messageCode;

    @TableField("send_time")
    @ApiModelProperty(value = "消息发送时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date sendTime;

    @TableField("box_no")
    @ApiModelProperty(value = "箱号")
    private String boxNo;

    @TableField(value = "delete_flag",fill = FieldFill.INSERT)
    @ApiModelProperty(value = "是否删除,0-未删除，1-已删除")
    private Integer deleteFlag;

}

