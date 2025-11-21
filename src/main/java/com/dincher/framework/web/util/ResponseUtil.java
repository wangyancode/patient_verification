package com.dincher.framework.web.util;

import com.dincher.framework.web.domain.ResponseEntity;
import com.dincher.framework.web.enumerate.ResponseEnum;

/**
 * 接口返回包装工具类
 *
 * @author yangshuai
 */
public final class ResponseUtil {

    private ResponseUtil() {
    }

    public static <T> ResponseEntity<T> success() {
        return response(ResponseEnum.SUCCESS.getCode(), null, ResponseEnum.SUCCESS.getMsg());
    }

    public static <T> ResponseEntity<T> success(final T t) {
        return response(ResponseEnum.SUCCESS.getCode(), t, ResponseEnum.SUCCESS.getMsg());
    }

    public static <T> ResponseEntity<T> error() {
        return response(ResponseEnum.FAILURE.getCode(), null, ResponseEnum.FAILURE.getMsg());
    }

    public static <T> ResponseEntity<T> error(String msg) {
        return response(ResponseEnum.FAILURE.getCode(), null, msg);
    }

    public static <T> ResponseEntity<T> response(final ResponseEnum e) {
        return response(e.getCode(), null, e.getMsg());
    }

    public static <T> ResponseEntity<T> response(final T data,
                                                 final ResponseEnum e) {
        return response(e.getCode(), data, e.getMsg());
    }

    public static <T> ResponseEntity<T> response(final int code,
                                                 final String msg) {
        return response(code, null, msg);
    }

    public static <T> ResponseEntity<T> response(final int code,
                                                 final T data,
                                                 final String msg) {
        return new ResponseEntity<T>(code, data, msg);
    }
}
