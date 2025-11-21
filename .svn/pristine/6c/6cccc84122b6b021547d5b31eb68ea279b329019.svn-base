package com.dincher.project.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dincher.project.system.mapper.UserRegionMapper;
import com.dincher.project.system.domain.vo.UserRegionVO;
import com.dincher.project.system.service.UserRegionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户和区域(病区或科室)关联信息(sys_user_region)业务实现类
 *
 * @author wangxiao
 * @date 2023-11-08 16:08:02
 */
@Service
@Slf4j
@Transactional
public class UserRegionServiceImpl extends ServiceImpl<UserRegionMapper, UserRegionVO> implements UserRegionService {
    @Resource
    private UserRegionMapper userRegionMapper;
   
     /** 
     * 
     * @description //TODO  查询数据
     * @author wangxiao 
     * @date 2023-11-08 16:08:02
     * @param userRegionVO
     * @return List<UserRegion>
     */
    @Override
    public List<UserRegionVO> selectDataList(UserRegionVO userRegionVO) {
        return userRegionMapper.selectDataList(userRegionVO);     
    }
    
}

