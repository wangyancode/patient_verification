package com.dincher.common.utils;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

/**
 * 项目启动完成后执行的方法
 *
 * @Author: zhanglj
 * @Date: 2022-09-01
 */
@Configuration
public class InitProject implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        String systemBasePath = System.getProperty("user.dir");
        System.out.println("系统项目路径===》 "+systemBasePath);
//        new File(systemBasePath + "/downloadPath").mkdir();
    }
}