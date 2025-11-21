package com.dincher.project.scm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dincher.project.scm.mapper.DeliveryOrderDetailsMapper;
import com.dincher.project.scm.domain.vo.DeliveryOrderDetailsVO;
import com.dincher.project.scm.service.DeliveryOrderDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

/**
 * 配送单明细(scm_delivery_order_details)业务实现类
 *
 * @author wangbin
 * @date 2023-12-06 15:36:59
 */
@Service
@Slf4j
@Transactional
public class DeliveryOrderDetailsServiceImpl extends ServiceImpl<DeliveryOrderDetailsMapper, DeliveryOrderDetailsVO> implements DeliveryOrderDetailsService {
    @Resource
    DeliveryOrderDetailsMapper deliveryOrderDetailsMapper;
   
     /** 
     * 
     * @description //TODO  查询数据
     * @author wangbin 
     * @date 2023-12-06 15:36:59
     * @param deliveryOrderDetailsVO
     * @return List<DeliveryOrderDetails>
     */
    @Override
    public List<DeliveryOrderDetailsVO> selectDataList(DeliveryOrderDetailsVO deliveryOrderDetailsVO) {
        return deliveryOrderDetailsMapper.selectDataList(deliveryOrderDetailsVO);     
    }
    
}

