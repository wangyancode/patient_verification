package com.dincher.project.scm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dincher.project.scm.mapper.HisDetailMapper;
import com.dincher.project.scm.domain.vo.HisDetailVO;
import com.dincher.project.scm.service.HisDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

/**
 * 药品的操作记录信息(scm_his_detail)业务实现类
 *
 * @author wangbin
 * @date 2023-12-19 14:41:17
 */
@Service
@Slf4j
@Transactional
public class HisDetailServiceImpl extends ServiceImpl<HisDetailMapper, HisDetailVO> implements HisDetailService {
    @Resource
    HisDetailMapper hisDetailMapper;
   
     /** 
     * 
     * @description //TODO  查询数据
     * @author wangbin 
     * @date 2023-12-19 14:41:17
     * @param hisDetailVO
     * @return List<HisDetail>
     */
    @Override
    public List<HisDetailVO> selectDataList(HisDetailVO hisDetailVO) {
        return hisDetailMapper.selectDataList(hisDetailVO);     
    }
    
}

