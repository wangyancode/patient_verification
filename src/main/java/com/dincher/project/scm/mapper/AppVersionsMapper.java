package com.dincher.project.scm.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dincher.project.scm.domain.DTO.AppVersionsDTO;
import com.dincher.project.scm.domain.vo.AppVersionsVO;

import java.util.List;

/**
 * app版本号管理(its_app_versions)数据库访问层
 *
 * @author yc
 * @date 2023-12-21 13:32:31
 */
public interface AppVersionsMapper extends BaseMapper<AppVersionsVO> {
  
    List<AppVersionsVO> selectDataList(AppVersionsVO appVersionsVO); 

    List<AppVersionsVO> pageByDTO(AppVersionsDTO appVersionsDTO);
}

