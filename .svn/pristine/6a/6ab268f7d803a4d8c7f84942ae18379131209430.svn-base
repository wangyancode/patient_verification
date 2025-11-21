package com.dincher.project.system.mapper;

import com.dincher.project.system.domain.vo.UserRoleVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;

/**
 * 用户和角色关联信息(sys_user_role)数据库访问层
 *
 * @author wangxiao
 * @date 2023-07-25 14:19:21
 */
public interface UserRoleMapper extends BaseMapper<UserRoleVO> {
  
    List<UserRoleVO> selectDataList(UserRoleVO userRoleVO);

    void deleteData(Integer userId);

    void deleteDataBatch(Integer[] userIds);
}

