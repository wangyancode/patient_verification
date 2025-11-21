package com.dincher.project.scm.mapper;

import com.dincher.project.scm.domain.vo.HisDetailVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;

/**
 * 药品的操作记录信息(scm_his_detail)数据库访问层
 *
 * @author wangbin
 * @date 2023-12-19 14:41:17
 */
public interface HisDetailMapper extends BaseMapper<HisDetailVO> {
  
    List<HisDetailVO> selectDataList(HisDetailVO hisDetailVO); 

}

