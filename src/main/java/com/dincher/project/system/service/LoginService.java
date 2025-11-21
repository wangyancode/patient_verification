package com.dincher.project.system.service;

import com.dincher.project.system.domain.dto.LoginDTO;

/**
 * @Description 登录接口
 * @Author WangXiao
 * @Date 2022/11/9
 */
public interface LoginService {

    public String login(LoginDTO loginDTO);

}
