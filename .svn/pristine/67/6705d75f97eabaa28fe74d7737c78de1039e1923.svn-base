package com.dincher.project.system.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.dincher.framework.web.domain.CommonEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 系统文件(sys_document)数据库映射实体类
 *
 * @author yangshuai
 * @date 2022-08-12 10:58:57
 */
@Data
public class Document  {

    @TableId(value = "document_id", type = IdType.AUTO)
    @ApiModelProperty(value = "主键id")
    private Integer documentId;

    @TableField("document_name")
    @ApiModelProperty(value = "文件名称")
    private String documentName;

    @TableField("document_type")
    @ApiModelProperty(value = "文件类型(文件后缀名)")
    private String documentType;

    @TableField("document_size")
    @ApiModelProperty(value = "文件大小（MB）")
    private String documentSize;

    @TableField("document_url")
    @ApiModelProperty(value = "文件地址")
    private String documentUrl;

    @TableField(value = "delete_flag")
    @ApiModelProperty(value = "是否删除,0-未删除，1-已删除")
    private Integer deleteFlag;

}

