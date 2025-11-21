package com.dincher.project.system.service.impl;

import com.dincher.common.constant.Constants;
import com.dincher.common.exception.BaseException;
import com.dincher.common.exception.CustomException;
import com.dincher.common.exception.user.CaptchaException;
import com.dincher.common.exception.user.CaptchaExpireException;
import com.dincher.common.exception.user.UserPasswordNotMatchException;
import com.dincher.common.utils.SecurityUtils;
import com.dincher.common.utils.StringUtils;
import com.dincher.framework.redis.RedisCache;
import com.dincher.framework.security.service.TokenService;
import com.dincher.framework.web.domain.LoginUser;
import com.dincher.project.system.domain.dto.LoginDTO;
import com.dincher.project.system.domain.vo.UserVO;
import com.dincher.project.system.service.ConfigService;
import com.dincher.project.system.service.LoginService;
import com.dincher.project.system.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 登录校验方法
 */
@Service
@Slf4j
public class LoginServiceImpl implements LoginService {

    @Resource
    private TokenService tokenService;

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private RedisCache redisCache;

    @Resource
    private ConfigService configService;

    @Resource
    private UserService userService;

    /**
     * @param loginDTO
     * @return java.lang.String
     * @description //TODO  登录验证
     * @author wangxiao
     * @date 2022/11/9
     */
    @Override
    public String login(LoginDTO loginDTO) {
        //如果配置中验证码开启，则验证码进行验证
        String authCodeFlag = configService.selectConfigByKey("authCodeFlag");
        if (Constants.YES.equals(authCodeFlag)) {
            String verifyKey = Constants.CAPTCHA_CODE_KEY + loginDTO.getUuid();
            String captcha = redisCache.getCacheObject(verifyKey) == null ? null : redisCache.getCacheObject(verifyKey).toString();
            redisCache.deleteObject(verifyKey);
            if (captcha == null) {
                throw new CaptchaExpireException();
            }
            if (!loginDTO.getCode().equalsIgnoreCase(captcha)) {
                throw new CaptchaException();
            }
        }
        String account = loginDTO.getUserAccount();
        // 用户验证
        Authentication authentication = null;
        try {
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(account, loginDTO.getPassword()));
        } catch (Exception e) {
            if (e instanceof BadCredentialsException) {
                throw new UserPasswordNotMatchException();
            } else {
                throw new CustomException(e.getMessage());
            }
        }
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();

        String platform = loginDTO.getPlatform();
        String department = loginDTO.getDepartment();
        Boolean is_department = true;
        if (null != platform && "app".equals(platform) && loginUser.getUser().getRegionList() != null && loginUser.getUser().getRegionList().size() != 0) {
            for (Map<String, Object> map : loginUser.getUser().getRegionList()) {
                if (map != null) {
                    for (Map.Entry<String, Object> entry : map.entrySet()) {
                        String key = entry.getKey();
                        Object value = entry.getValue();
                        if(value.equals(department)) {
                            is_department = false;
                        }
                        System.err.println(key + " : " + value);
                    }
                } else {
                    log.info("登录账号：{} 未配置科室.", loginUser.getUser().getUserAccount());
                    throw new BaseException("对不起，您的账号：" + loginUser.getUser().getUserAccount() + " 未配置科室");
                }

            }
            if(is_department) {
                log.info("登录账号：{} 无此科室权限.", loginUser.getUser().getUserAccount());
                throw new BaseException("对不起，您的账号：" + loginUser.getUser().getUserAccount() + " 无此科室权限");
            }
        }

        if(StringUtils.isNotEmpty(loginDTO.getReCheckerAccount())){
            UserVO reChecker = userService.getUserByUserAccount(loginDTO.getReCheckerAccount());
            if(StringUtils.isNull(reChecker)){
                throw new CustomException("复核人账号不存在");
            }
            if(reChecker.getUserPassword().equals(SecurityUtils.encryptPassword(loginDTO.getReCheckerPassword()))){
                throw new CustomException("复核人登录密码不正确");
            }
            loginUser.getUser().setReChecker(reChecker);
        }
        // 生成token
        return tokenService.createToken(loginUser);
    }
}
