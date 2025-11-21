package com.dincher.framework.security.filter;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dincher.common.enums.UserStatus;
import com.dincher.common.utils.SecurityUtils;
import com.dincher.common.utils.StringUtils;
import com.dincher.framework.security.service.TokenService;
import com.dincher.framework.web.domain.LoginUser;
import com.dincher.project.system.domain.vo.UserVO;
import com.dincher.project.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * token过滤器 验证token有效性
 *
 *
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter
{
    @Autowired
    private TokenService tokenService;

    @Resource
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException
    {
        LoginUser loginUser = tokenService.getLoginUser(request);
        if (StringUtils.isNotNull(loginUser) && StringUtils.isNull(SecurityUtils.getAuthentication()))
        {
            if (StringUtils.isNotNull(loginUser.getUser())){
                QueryWrapper<UserVO> queryWrapper = new QueryWrapper<UserVO>();
                queryWrapper.eq("user_account",loginUser.getUser().getUserAccount());
                UserVO user = userService.getOne(queryWrapper);
                if(StringUtils.isNotNull(user) && UserStatus.DISABLE.getCode().equals(user.getUserStatus())){
                    request.setAttribute("errorCode",402);
                    throw new ServletException();
                }
            }
            tokenService.verifyToken(loginUser);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        chain.doFilter(request, response);
    }
}
