package com.dincher.framework.security.handle;

import com.alibaba.fastjson.JSON;
import com.dincher.common.constant.HttpStatus;
import com.dincher.common.utils.ServletUtils;
import com.dincher.common.utils.StringUtils;
import com.dincher.framework.web.domain.AjaxResult;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

/**
 * 认证失败处理类 返回未授权
 * 
 *
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint, Serializable
{
    private static final long serialVersionUID = -8970718410437077606L;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e)
            throws IOException
    {
        int code = HttpStatus.UNAUTHORIZED;
        String msg = StringUtils.format("请求访问：{}，认证失败，无法访问系统资源", request.getRequestURI());
        Object errorCode = request.getAttribute("errorCode");
        if(StringUtils.isNotNull(errorCode)){
            if("402".equals(errorCode.toString())){
                code=402;
                msg="对不起，您的账号已停用";
            }
        }
        ServletUtils.renderString(response, JSON.toJSONString(AjaxResult.error(code, msg)));

    }
}
