package com.dincher.project.scm.domain.entity;

import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.dincher.framework.web.domain.CommonEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 药品的操作记录信息(scm_his_detail)数据库映射实体类
 *
 * @author wangbin
 * @date 2023-12-19 14:41:17
 */
@Data
public class HisDetail extends CommonEntity{ 

    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "id")
    private Integer id;

    @TableField(value = "detail_id")
    @ApiModelProperty(value = "detail_id")
    private Integer detailId;
    
    @TableField("box_no")
    @ApiModelProperty(value = "箱号")
    private String boxNo;
    
    @TableField("drug_name")
    @ApiModelProperty(value = "药品名称")
    private String drugName;
    
    @TableField("order_count")
    @ApiModelProperty(value = "计划数（订单数量）")
    private BigDecimal orderCount;
    
    @TableField("distribute_count")
    @ApiModelProperty(value = "配送数量")
    private BigDecimal distributeCount;
    
    @TableField("unit")
    @ApiModelProperty(value = "单位")
    private String unit;
    
    @TableField("drug_specs")
    @ApiModelProperty(value = "药品规格")
    private String drugSpecs;
    
    @TableField("license_no")
    @ApiModelProperty(value = "批准文号")
    private String licenseNo;
    
    @TableField("approval_no")
    @ApiModelProperty(value = "批号")
    private String approvalNo;
    
    @TableField("drug_check_status")
    @ApiModelProperty(value = "验收状态1待验收2已验收")
    private String drugCheckStatus;
    
    @TableField("drug_check_datetime")
    @ApiModelProperty(value = "验收时间")
    private Date drugCheckDatetime;
    
    @TableField("check_count")
    @ApiModelProperty(value = "验收数量")
    private BigDecimal checkCount;
    
    @TableField("check_user_id")
    @ApiModelProperty(value = "验收人")
    private Integer checkUserId;
    
    @TableField("supplier_code")
    @ApiModelProperty(value = "供应商编码")
    private String supplierCode;
    
    @TableField("supplier_name")
    @ApiModelProperty(value = "供应商名称")
    private String supplierName;
    
    @TableField("invoice_no")
    @ApiModelProperty(value = "发票号")
    private String invoiceNo;
    
    @TableField("group_no")
    @ApiModelProperty(value = "配送组号")
    private String groupNo;
    
    @TableField("distribute_code")
    @ApiModelProperty(value = "配送企业编码")
    private String distributeCode;
    
    @TableField(value = "delete_flag",fill = FieldFill.INSERT)
    @ApiModelProperty(value = "是否删除,0-未删除，1-已删除")
    private Integer deleteFlag;
    
}

