package com.dincher.framework.web.domain;


import com.dincher.framework.web.enumerate.ResponseEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 返回包装类
 *
 * @param <T>
 * @author yangshuai
 */
@ApiModel(value = "ResponseEntity", description = "返回包装")
@Data
public class ResponseEntity<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 返回码，遵循httpcode或遵循ResponseEnum.java枚举类
     */
    @ApiModelProperty(value = "返回码")
    private Integer code;
    /**
     * 返回内容
     */
    @ApiModelProperty(value = "返回内容")
    private T data;
    /**
     * 返回说明
     */
    @ApiModelProperty(value = "返回说明")
    private String msg;

    public ResponseEntity() {
        this.code = ResponseEnum.SUCCESS.getCode();
    }

    public ResponseEntity(T data) {
        this.code = ResponseEnum.SUCCESS.getCode();
        this.data = data;
    }

    public ResponseEntity(Integer code, String message) {
        this.code = code;
        this.msg = message;
    }

    public ResponseEntity(Integer code, T data, String message) {
        this.code = code;
        this.data = data;
        this.msg = message;
    }

}
