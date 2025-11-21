package com.dincher.project.system.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dincher.project.system.domain.entity.Document;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 系统文件(sys_document)业务处理实体类
 *
 * @author yangshuai
 * @date 2022-08-12 10:59:06
 */
@Data
@TableName("sys_document")
@ApiModel(value = "DocumentVO", description = "sys_document业务处理实体类")
public class DocumentVO extends Document {

    @TableField(exist = false)
    @ApiModelProperty(value = "文件对应的其他数据记录唯一值",required = false)
    //示例：药箱二维码文件，这里就是药箱记录的唯一值(可以是药箱记录的主键，也可以是药箱的编号)
    private String uniqueValue;

}

