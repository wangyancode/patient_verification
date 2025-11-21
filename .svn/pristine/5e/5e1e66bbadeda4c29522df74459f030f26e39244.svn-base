package com.dincher.project.scm.domain.DTO;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 配送单查询数量返回实体类
 *
 * @author wangbin
 * @date 2023-12-06 11:45:08
 */
@Data
public class DeliveryOrderQueryCountDTO {



    @ApiModelProperty(value = "验收状态1待验收数量")
    private Integer toBeAcceptedCount = 0;

    @ApiModelProperty(value = "验收状态2验收中数量")
    private Integer duringAcceptanceCount= 0;

    @ApiModelProperty(value = "验收状态3已验收数量")
    private Integer acceptedCount= 0;


    
}

