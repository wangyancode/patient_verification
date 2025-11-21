package com.dincher.project.scm.domain.DTO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * app版本号管理(its_app_versions)请求参数实体类
 *
 * @author yc
 * @date 2023-12-21 13:32:31
 */
@Data
public class AppVersionsDTO {
    
    @ApiModelProperty(value = "创建时间开始")
    private String createDatetimeStart;
    
    @ApiModelProperty(value = "创建时间结束")
    private String createDatetimeEnd;
    
    @ApiModelProperty(value = "修改时间开始")
    private String updateDatetimeStart;
   
    @ApiModelProperty(value = "修改时间结束")
    private String updateDatetimeEnd;
    
}

