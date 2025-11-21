package com.dincher.project.scm.mapper;

import com.dincher.project.scm.domain.DTO.*;
import com.dincher.project.scm.domain.vo.DeliveryOrderDetailsVO;
import com.dincher.project.scm.domain.vo.DeliveryOrderVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;

/**
 * 配送单(scm_delivery_order)数据库访问层
 *
 * @author wangbin
 * @date 2023-12-06 11:45:08
 */
public interface DeliveryOrderMapper extends BaseMapper<DeliveryOrderVO> {
  
    List<DeliveryOrderVO> selectDataList(DeliveryOrderVO deliveryOrderVO);

    //分页查询
    List<DeliveryOrderQueryReturnDTO> allStatusQueryPage(DeliveryOrderQueryDTO deliveryOrderQueryDTO);

    //查询箱子具体的信息
    DeliveryOrderQueryResponseDTO queryOrderAndInvoiceNo(DeliveryOrderDetailRequestDTO deliveryOrderDetailRequestDTO);

    //查询同组下所有的箱子
    List<DeliveryOrderQueryResponseDTO> queryOrderAndInvoiceNoAll(DeliveryOrderDetailRequestDTO deliveryOrderDetailRequestDTO);

    //反查具体的药品明细
    List<DeliveryOrderDetailsVO> reverseCheckSpecificDrugDetails(DeliveryOrderDetailsVO deliveryOrderDetailsVO);

    //查询数量
    DeliveryOrderQueryCountDTO allStatusQueryCount(DeliveryOrderQueryDTO requestQueryBody);
}

