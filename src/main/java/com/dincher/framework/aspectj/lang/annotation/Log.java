package com.dincher.framework.aspectj.lang.annotation;

import com.dincher.framework.aspectj.lang.enums.BusinessType;

import java.lang.annotation.*;

/**
 * 自定义操作日志记录注解
 * 
 *
 *
 */
@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log
{
    /**
     * 模块名称
     */
    public String moduleName() default "";

    /**
     * 功能名称
     */
    public String functionName() default "";

    /**
     * 操作类型
     */
    public BusinessType businessType() default BusinessType.OTHER;

    /**
     * 是否保存请求的参数
     */
    public boolean isSaveRequestData() default true;
}
