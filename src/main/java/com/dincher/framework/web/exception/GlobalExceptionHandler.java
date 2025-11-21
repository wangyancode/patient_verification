package com.dincher.framework.web.exception;

import com.dincher.common.constant.HttpStatus;
import com.dincher.common.exception.BaseException;
import com.dincher.common.exception.CustomException;
import com.dincher.common.exception.DemoModeException;
import com.dincher.common.utils.StringUtils;
import com.dincher.framework.web.domain.AjaxResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.UnexpectedTypeException;
import java.util.List;

/**
 * 全局异常处理器
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 基础异常
     */
    @ExceptionHandler(BaseException.class)
    public AjaxResult baseException(BaseException e) {
        return AjaxResult.error(e.getMessage());
    }

    /**
     * 业务异常
     */
    @ExceptionHandler(CustomException.class)
    public AjaxResult businessException(CustomException e) {
        if (StringUtils.isNull(e.getCode())) {
            return AjaxResult.error(e.getMessage());
        }
        return AjaxResult.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public AjaxResult handlerNoFoundException(Exception e) {
        log.error(e.getMessage(), e);
        return AjaxResult.error(HttpStatus.NOT_FOUND, "路径不存在，请检查路径是否正确");
    }

    @ExceptionHandler(AccessDeniedException.class)
    public AjaxResult handleAuthorizationException(AccessDeniedException e) {
        log.error(e.getMessage());
        return AjaxResult.error(HttpStatus.FORBIDDEN, "没有权限，请联系管理员授权");
    }

    @ExceptionHandler(AccountExpiredException.class)
    public AjaxResult handleAccountExpiredException(AccountExpiredException e) {
        log.error(e.getMessage(), e);
        return AjaxResult.error(e.getMessage());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public AjaxResult handleUsernameNotFoundException(UsernameNotFoundException e) {
        log.error(e.getMessage(), e);
        return AjaxResult.error(e.getMessage());
    }

    // 单个上传文件大小不能大于20MB，捕获异常：MaxUploadSizeExceededException
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public AjaxResult handlerMaxUploadFile(MaxUploadSizeExceededException ex) {
        return AjaxResult.error("单个上传文件大小不能大于20MB！");
    }

    /**
     * 演示模式异常
     */
    @ExceptionHandler(DemoModeException.class)
    public AjaxResult demoModeException(DemoModeException e) {
        return AjaxResult.error("演示模式，不允许操作");
    }

    /**
     * 处理请求对象属性不满足校验规则的异常信息（BindException是@Validation单独使用校验失败时产生的异常）
     *
     * @param exception
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = BindException.class)
    public AjaxResult exception(BindException exception) {
        BindingResult result = exception.getBindingResult();
        final List<FieldError> fieldErrors = result.getFieldErrors();
        StringBuilder builder = new StringBuilder();
        for (FieldError error : fieldErrors) {
            builder.append(error.getField() + error.getDefaultMessage() + ",");
        }
        builder.deleteCharAt(builder.length() - 1);
        log.error("入参出错", exception);
        return AjaxResult.error(builder.toString());
    }

    /**
     * 处理请求对象属性不满足校验规则的异常信息
     * （MethodArgumentNotValidException是@RequestBody和@Validated配合时产生的异常，比如在传参时如果前端的json数据里部分缺失@RequestBody修饰的实体类的属性就会产生这个异常。）
     *
     * @param exception
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public AjaxResult exception(MethodArgumentNotValidException exception) {
        BindingResult result = exception.getBindingResult();
        final List<FieldError> fieldErrors = result.getFieldErrors();
        StringBuilder builder = new StringBuilder();
        for (FieldError error : fieldErrors) {
            builder.append(error.getField() + error.getDefaultMessage() + ",");
        }
        builder.deleteCharAt(builder.length() - 1);
        log.error("入参出错", exception);
        return AjaxResult.error(HttpStatus.BAD_REQUEST, builder.toString());
    }

    /**
     * 处理请求对象属性不满足校验规则的异常信息
     *
     * @param exception
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public AjaxResult exception(MissingServletRequestParameterException exception) {
        String parameterName = exception.getParameterName();
        StringBuilder builder = new StringBuilder();
        builder.append(parameterName + ",不能为空!");
        log.error("入参出错", exception);
        return AjaxResult.error(builder.toString());
    }

    /**
     * 处理请求对象属性不满足校验规则的异常信息
     *
     * @param exception
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = UnexpectedTypeException.class)
    public AjaxResult exception(UnexpectedTypeException exception) {
        String parameterName = exception.getMessage();
        StringBuilder builder = new StringBuilder();
        builder.append(parameterName + ",不能为空!");
        log.error("入参出错", exception);
        return AjaxResult.error(builder.toString());
    }

    /**
     * 处理请求对象属性不满足校验规则的异常信息
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(Exception.class)
    public AjaxResult exception(Exception exception) {
        log.error("服务出错", exception);
        exception.printStackTrace();
        return AjaxResult.error(exception.getMessage());
    }

}
