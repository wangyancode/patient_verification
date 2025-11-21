package com.dincher.project.scm.domain.DTO;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 单独验收实体类
 *
 * @author wangbin
 * @date 2023-12-06 11:45:08
 */
@Data
public class SynchornizeDataDetailsRequestDTO {

    @ApiModelProperty(value = "明细主键id")
    private String distributionserialId;

    @ApiModelProperty(value = "平台订单号")
    private String orderId;

    @ApiModelProperty(value = "阳光订单明细ID")
    private String orderdetailId;

    @ApiModelProperty(value = "发票号")
    private String invoiceNo;

    @ApiModelProperty(value = "平台代码")
    private String platformDrugCode;

    @ApiModelProperty(value = "商品编号")
    private String procurecatalogId;

    @ApiModelProperty(value = "省标代码")
    private String provinceId;

    @ApiModelProperty(value = "药品名称")
    private String drugName;

    @ApiModelProperty(value = "药品分类")
    private String drugType;

    @ApiModelProperty(value = "药品规格")
    private String drugSpecs;

    @ApiModelProperty(value = "剂型描述")
    private String formName;

    @ApiModelProperty(value = "生产企业编码")
    private String productionCode;

    @ApiModelProperty(value = "生产企业名称")
    private String productionName;

    @ApiModelProperty(value = "批准文号")
    private String licenseNo;

    @ApiModelProperty(value = "批号")
    private String approvalNo;

    @ApiModelProperty(value = "效期")
    private Date validDate;

    @ApiModelProperty(value = "计划数（订单数量）")
    private BigDecimal orderCount;

    @ApiModelProperty(value = "配送数量")
    private BigDecimal distributeCount;

    @ApiModelProperty(value = "单位")
    private String unit;

    @ApiModelProperty(value = "配送时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date distributeTime;

    @ApiModelProperty(value = "采购价格")
    private BigDecimal price;

    @ApiModelProperty(value = "金额")
    private BigDecimal amount;

    @ApiModelProperty(value = "供应商编码")
    private String supplierCode;

    @ApiModelProperty(value = "供应商名称")
    private String supplierName;

    @ApiModelProperty(value = "供应商药品编码")
    private String supplierDrugCode;

    @ApiModelProperty(value = "是否贵重. 1-是 2-否（供应商自行定义）")
    private String isExpensive;





}

