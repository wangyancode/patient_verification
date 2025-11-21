package com.dincher.project.scm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dincher.framework.web.domain.RequestQueryBody;
import com.dincher.framework.web.domain.ResponseEntity;
import com.dincher.framework.web.page.TableDataInfo;
import com.dincher.project.scm.domain.DTO.*;
import com.dincher.project.scm.domain.vo.DeliveryOrderVO;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 配送单(scm_delivery_order)业务接口
 *
 * @author wangbin
 * @date 2023-12-06 11:45:08
 */
public interface DeliveryOrderService extends IService<DeliveryOrderVO> {

    public List<DeliveryOrderVO> selectDataList(DeliveryOrderVO deliveryOrderVO);

    List<DeliveryOrderQueryReturnDTO> allStatusQueryPage(DeliveryOrderQueryDTO deliveryOrderQueryDTO);

    List<DeliveryOrderQueryResponseDTO> queryDetails(DeliveryOrderDetailRequestDTO deliveryOrderDetailRequestDTO);

    List<DeliveryOrderQueryResponseDTO> queryDetailsAllBox(DeliveryOrderDetailRequestDTO deliveryOrderDetailRequestDTO);

    DeliveryOrderVO checkDrugs(CheckDrugsRequestDTO checkDrugsRequestDTO);

    MergeCheckDrugsRequestDTO mergeCheckDrugs( MergeCheckDrugsRequestDTO mergeCheckDrugsRequestDTO);

    DeliveryOrderVO synchronizeData( SynchornizeDataRequestDTO synchornizeDataRequestDTO, HttpServletRequest request);

    DeliveryOrderQueryCountDTO allStatusQueryCount(DeliveryOrderQueryDTO requestQueryBody);

    //验收后同步his
    void synchronizeHisAfterAcceptance(SynchronizeHisAfterAcceptanceDTO synchronizeHisAfterAcceptanceDTO);
}

