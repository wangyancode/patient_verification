package com.dincher.project.scm.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.dincher.project.scm.domain.DTO.AppVersionsDTO;
import com.dincher.project.scm.domain.vo.AppVersionsVO;

import java.util.List;

/**
 * app版本号管理(its_app_versions)业务接口
 *
 * @author yc
 * @date 2023-12-21 13:32:31
 */
public interface AppVersionsService extends IService<AppVersionsVO> {

    public List<AppVersionsVO> selectDataList(AppVersionsVO appVersionsVO);
    
    public List<AppVersionsVO> pageByDTO(AppVersionsDTO appVersionsDTO);

    AppVersionsVO getByIdVO(Integer id);

    AppVersionsVO checkVersions(String versionsNo);

    AppVersionsVO getLastVersions();

    boolean saveVO(AppVersionsVO appVersionsVO);
}

