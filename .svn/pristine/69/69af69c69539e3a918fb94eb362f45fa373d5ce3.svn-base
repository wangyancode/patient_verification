package com.dincher.framework.security.service;

import com.dincher.common.enums.UserStatus;
import com.dincher.common.exception.BaseException;
import com.dincher.common.utils.StringUtils;
import com.dincher.framework.web.domain.LoginUser;
import com.dincher.project.system.domain.vo.UserVO;
import com.dincher.project.system.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 用户验证处理
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    private UserService userService;

    @Autowired
    private SysPermissionService permissionService;

    @Override
    public UserDetails loadUserByUsername(String userAccount) throws UsernameNotFoundException {
        UserVO user = userService.getUserByUserAccount(userAccount);
//        UserVO user = userService.getOne(new QueryWrapper<UserVO>().eq("user_account",userAccount));
        if (StringUtils.isNull(user)) {
            log.info("登录账号：{} 不存在.", userAccount);
            throw new UsernameNotFoundException("登录账号：" + userAccount + " 不存在");
        }
        else if (UserStatus.DELETED.getCode().equals(user.getDeleteFlag())) {
            log.info("登录账号：{} 已被删除.",  userAccount);
            throw new BaseException("对不起，您的账号：" +  userAccount + " 已被删除");
        }
        else if (UserStatus.DISABLE.getCode().equals(user.getUserStatus())) {
            log.info("登录账号：{} 已被停用.", userAccount);
            throw new BaseException("对不起，您的账号：" + userAccount + " 已停用");
        }
        return createLoginUser(user);
    }

    public UserDetails createLoginUser(UserVO user) {
        return new LoginUser(user, permissionService.getMenuPermission(user));
    }
}
