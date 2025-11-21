package com.dincher.project.scm.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dincher.project.scm.domain.entity.AppVersions;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * app版本号管理(its_app_versions)业务处理实体类
 *
 * @author yc
 * @date 2023-12-21 13:32:31
 */
@Data
@TableName("scm_app_versions")
public class AppVersionsVO extends AppVersions {

    @TableField(exist = false)
    @ApiModelProperty(value = "url")
    private String documentUrl;

}

