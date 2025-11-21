package com.dincher.project.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dincher.project.system.domain.vo.UserRoleVO;
import java.util.List;

/**
 * 用户和角色关联信息(sys_user_role)业务接口
 *
 * @author wangxiao
 * @date 2023-07-25 14:03:26
 */
public interface UserRoleService extends IService<UserRoleVO> {

    public List<UserRoleVO> selectDataList(UserRoleVO userRoleVO);

    public void deleteData(Integer userId);

    public void deleteDataBatch(Integer[] userIds);
}

