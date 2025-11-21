package com.dincher.project.system.mapper;

import com.dincher.project.system.domain.vo.UserRegionVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;

/**
 * 用户和区域(病区或科室)关联信息(sys_user_region)数据库访问层
 *
 * @author wangxiao
 * @date 2023-11-08 16:08:02
 */
public interface UserRegionMapper extends BaseMapper<UserRegionVO> {
  
    List<UserRegionVO> selectDataList(UserRegionVO userRegionVO); 

}

