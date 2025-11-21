package com.dincher.framework.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.dincher.common.utils.SecurityUtils;
import com.dincher.project.system.domain.entity.User;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Description 插入和更新对公共字段的配置
 * @Author WangXiao
 * @Date 2022/6/20
 */
@Component
public class MybatisPlusHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        try {
            User user = SecurityUtils.getLoginUser().getUser();
            setFieldValByName("createBy",user.getUserId(),metaObject);
            setFieldValByName("updateBy",user.getUserId(),metaObject);
        } catch (Exception e) {
        }

        setFieldValByName("createDatetime",new Date(),metaObject);
        setFieldValByName("deleteFlag",0,metaObject);
        setFieldValByName("updateDatetime",new Date(),metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        try {
            User user = SecurityUtils.getLoginUser().getUser();
            setFieldValByName("updateBy",user.getUserId(),metaObject);
        } catch (Exception e) {
        }

        setFieldValByName("updateDatetime",new Date(),metaObject);
    }

}
