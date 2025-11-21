package com.dincher.project.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dincher.project.system.domain.vo.UserRoleVO;
import com.dincher.project.system.mapper.UserRoleMapper;
import com.dincher.project.system.service.UserRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户和角色关联信息(sys_user_role)业务实现类
 *
 * @author wangxiao
 * @date 2023-07-25 14:03:26
 */
@Service
@Slf4j
@Transactional
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRoleVO> implements UserRoleService {
    @Resource
    private UserRoleMapper userRoleMapper;
   
     /** 
     * 
     * @description //TODO  查询数据
     * @author wangxiao 
     * @date 2023-07-25 14:03:26
     * @param userRoleVO
     * @return List<UserRole>
     */
    @Override
    public List<UserRoleVO> selectDataList(UserRoleVO userRoleVO) {
        return userRoleMapper.selectDataList(userRoleVO);     
    }

    @Override
    public void deleteData(Integer userId) {
        userRoleMapper.deleteData(userId);
    }

    @Override
    public void deleteDataBatch(Integer[] userIds) {
        userRoleMapper.deleteDataBatch(userIds);
    }
}

