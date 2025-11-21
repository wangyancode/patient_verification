package com.dincher.project.scm.domain.DTO;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * 同步his
 *
 * @author wangbin
 * @date 2023-12-06 11:45:08
 */
@Data
public class SynchronizeHisAfterAcceptanceDTO {

    @TableField("box_no")
    @ApiModelProperty(value = "箱号")
    private String boxNo;

    @TableField("group_no")
    @ApiModelProperty(value = "配送组号")
    private String groupNo;

}

