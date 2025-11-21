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
 * 配送单明细未同步到his的暂存表(scm_his_unsynchronized_delivery_order_details)数据库映射实体类
 *
 * @author wangbin
 * @date 2024-05-07 09:15:39
 */
@Data
public class HisUnsynchronizedDeliveryOrderDetails extends CommonEntity{ 

    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "明细主键id")
    private Integer id;
    
    @TableField("box_no")
    @ApiModelProperty(value = "箱号")
    private String boxNo;
    
    @TableField("order_id")
    @ApiModelProperty(value = "平台订单号")
    private String orderId;
    
    @TableField("orderdetail_id")
    @ApiModelProperty(value = "阳光订单明细ID")
    private String orderdetailId;
    
    @TableField("distributionserial_id")
    @ApiModelProperty(value = "配送企业内部ID")
    private String distributionserialId;
    
    @TableField("invoice_no")
    @ApiModelProperty(value = "发票号")
    private String invoiceNo;
    
    @TableField("platform_drug_code")
    @ApiModelProperty(value = "平台代码")
    private String platformDrugCode;
    
    @TableField("procurecatalog_id")
    @ApiModelProperty(value = "商品编号")
    private String procurecatalogId;
    
    @TableField("province_id")
    @ApiModelProperty(value = "省标代码")
    private String provinceId;
    
    @TableField("drug_name")
    @ApiModelProperty(value = "药品名称")
    private String drugName;
    
    @TableField("drug_type")
    @ApiModelProperty(value = "药品分类")
    private String drugType;
    
    @TableField("drug_specs")
    @ApiModelProperty(value = "药品规格")
    private String drugSpecs;
    
    @TableField("form_name")
    @ApiModelProperty(value = "剂型描述")
    private String formName;
    
    @TableField("production_code")
    @ApiModelProperty(value = "生产企业编码")
    private String productionCode;
    
    @TableField("production_name")
    @ApiModelProperty(value = "生产企业名称")
    private String productionName;
    
    @TableField("license_no")
    @ApiModelProperty(value = "批准文号")
    private String licenseNo;
    
    @TableField("approval_no")
    @ApiModelProperty(value = "批号")
    private String approvalNo;
    
    @TableField("valid_date")
    @ApiModelProperty(value = "效期")
    private Date validDate;
    
    @TableField("order_count")
    @ApiModelProperty(value = "计划数（订单数量）")
    private BigDecimal orderCount;
    
    @TableField("distribute_count")
    @ApiModelProperty(value = "配送数量")
    private BigDecimal distributeCount;
    
    @TableField("unit")
    @ApiModelProperty(value = "单位")
    private String unit;
    
    @TableField("distribute_time")
    @ApiModelProperty(value = "配送时间")
    private Date distributeTime;
    
    @TableField("price")
    @ApiModelProperty(value = "采购价格")
    private BigDecimal price;
    
    @TableField("amount")
    @ApiModelProperty(value = "金额")
    private BigDecimal amount;
    
    @TableField("supplier_code")
    @ApiModelProperty(value = "供应商编码")
    private String supplierCode;
    
    @TableField("supplier_name")
    @ApiModelProperty(value = "供应商名称")
    private String supplierName;
    
    @TableField("supplier_drug_code")
    @ApiModelProperty(value = "供应商药品编码")
    private String supplierDrugCode;
    
    @TableField("is_expensive")
    @ApiModelProperty(value = "是否贵重. 1-是 2-否（供应商自行定义）")
    private String isExpensive;
    
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
    
    @TableField(value = "delete_flag",fill = FieldFill.INSERT)
    @ApiModelProperty(value = "是否删除,0-未删除，1-已删除")
    private Integer deleteFlag;
    
    @TableField("pieces")
    @ApiModelProperty(value = "件数")
    private String pieces;
    
    @TableField("distribute_code")
    @ApiModelProperty(value = "配送企业编码")
    private String distributeCode;
    
    @TableField("ajust_count")
    @ApiModelProperty(value = "转换数量")
    private String ajustCount;
    
}

