package com.dincher.project.system.domain.enums;

/**
 * @Description 用户类型枚举
 * @Author WangXiao
 * @Date 2023/7/25
 */
public enum UserTypeEnum {
    HEAL("1","卫健委"), APPLY_COMPANY("2","申报单位"), EXPERT("3","专家"),APPLY_DEPT("4","申报科室");

    private final String code;
    private final String info;

    UserTypeEnum(String code, String info)
    {
        this.code = code;
        this.info = info;
    }

    public String getCode()
    {
        return code;
    }

    public String getInfo()
    {
        return info;
    }
}
