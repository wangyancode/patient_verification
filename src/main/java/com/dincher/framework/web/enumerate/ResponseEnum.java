package com.dincher.framework.web.enumerate;

/**
 * 枚举
 *
 * @author yangshuai
 */
public enum ResponseEnum {

    /**
     * 操作成功
     */
    SUCCESS(200, "操作成功"),

    /**
     * 操作失败
     */
    FAILURE(500, "操作失败"),

    /**
     * 无法找到相关的数据
     */
    NOT_FOUND(404, "无法获取数据"),

    /**
     * 参数丢失
     */
    PARAMS_MISS(302, "参数丢失");


    private Integer code;
    private String msg;

    ResponseEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
