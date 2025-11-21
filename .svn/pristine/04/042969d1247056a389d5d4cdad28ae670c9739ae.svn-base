package com.dincher.project.system.controller;

import com.dincher.common.utils.ServletUtils;
import com.dincher.framework.security.service.SysPermissionService;
import com.dincher.framework.security.service.TokenService;
import com.dincher.framework.web.domain.LoginUser;
import com.dincher.framework.web.domain.ResponseEntity;
import com.dincher.framework.web.util.ResponseUtil;
import com.dincher.project.system.domain.dto.LoginDTO;
import com.dincher.project.system.domain.entity.Menu;
import com.dincher.project.system.domain.entity.User;
import com.dincher.project.system.domain.vo.LoginInfoVO;
import com.dincher.project.system.domain.vo.RouterVo;
import com.dincher.project.system.domain.vo.TokenVO;
import com.dincher.project.system.service.LoginService;
import com.dincher.project.system.service.MenuService;
import io.swagger.annotations.Api;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

@RestController
@Api(tags = "登录APi")
public class LoginController {
    @Resource
    private LoginService loginService;

    @Resource
    private MenuService menuService;

    @Resource
    private SysPermissionService permissionService;

    @Resource
    private TokenService tokenService;


    /**
     * 登录方法
     *
     * @param loginDTO 登录信息
     * @return 结果
     */
    @PostMapping("/login")
    public ResponseEntity<TokenVO> login(@RequestBody LoginDTO loginDTO)
    {

        //强密码验证
        if (null != loginDTO && null != loginDTO.getPassword()) {
            boolean checkPass = this.validateStrongPassword(loginDTO.getPassword());
            if (!checkPass) {
                throw new UsernameNotFoundException("密码规则说明：小写字母+大写字母+数字+特殊符号+长度为8-16位！");
            }
        }

        // 生成令牌
        String token = loginService.login(loginDTO);
        TokenVO tokenVO = new TokenVO();
        tokenVO.setToken(token);
        return ResponseUtil.success(tokenVO);
    }

    // 正则表达式用于验证密码强度
    private static final String PASSWORD_REGEX =
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,16}$";

    /**
     * 验证密码是否符合强密码规则
     *
     * @param password 待验证的密码
     * @return 如果密码符合规则返回true，否则返回false
     */
    public static boolean validateStrongPassword(String password) {
        return password.matches(PASSWORD_REGEX);
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @GetMapping("getInfo")
    public ResponseEntity<LoginInfoVO> getInfo()
    {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        User user = loginUser.getUser();
        // 角色集合
        Set<String> roles = permissionService.getRolePermission(user);
        // 权限集合
        Set<String> permissions = permissionService.getMenuPermission(user);
        LoginInfoVO loginInfoVO = new LoginInfoVO();
        loginInfoVO.setUser(user);
        loginInfoVO.setRolePermissions(roles);
        loginInfoVO.setMenuPermissions(permissions);
        return ResponseUtil.success(loginInfoVO);
    }

    /**
     * 获取路由信息
     *
     * @return 路由信息
     */
    @GetMapping("getRouters")
    public ResponseEntity<List<RouterVo>> getRouters()
    {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        // 用户信息
        User user = loginUser.getUser();
        List<Menu> menus = menuService.selectMenuTreeByUserId(user.getUserId());
        return ResponseUtil.success(menuService.buildMenus(menus));
    }
}
