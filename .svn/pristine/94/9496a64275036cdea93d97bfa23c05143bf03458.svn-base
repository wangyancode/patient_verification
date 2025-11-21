package com.dincher.project.scm.mapper;

import com.dincher.project.scm.domain.DTO.DeliveryOrderDetailGroupQueryDTO;
import com.dincher.project.scm.domain.vo.DeliveryOrderDetailsVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;

/**
 * 配送单明细(scm_delivery_order_details)数据库访问层
 *
 * @author wangbin
 * @date 2023-12-06 15:37:00
 */
public interface DeliveryOrderDetailsMapper extends BaseMapper<DeliveryOrderDetailsVO> {
  
    List<DeliveryOrderDetailsVO> selectDataList(DeliveryOrderDetailsVO deliveryOrderDetailsVO);

    List<DeliveryOrderDetailsVO> selectDataGroupByBoxNoAndApprovalNo(DeliveryOrderDetailGroupQueryDTO deliveryOrderDetailGroupQueryDTO);

    List<DeliveryOrderDetailsVO> selectDataGroupByBoxNoAndApprovalNoNotGroup(DeliveryOrderDetailGroupQueryDTO deliveryOrderDetailGroupQueryDTO);


}

