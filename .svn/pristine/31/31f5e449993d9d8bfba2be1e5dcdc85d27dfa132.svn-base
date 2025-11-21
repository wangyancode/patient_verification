package com.dincher.project.scm.task;

import com.alibaba.fastjson.JSON;
import com.dincher.project.scm.domain.DTO.SynchronizeHisAfterAcceptanceDTO;
import com.dincher.project.scm.service.DeliveryOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;

@Configuration
@Slf4j
@Lazy
public class HisDetailSaveTask {

    @Autowired
    DeliveryOrderService deliveryOrderService;


    @Async("threadPoolTaskExecutor")
    public void updateBatchByDetailIdAndInvoiceNoOracle(SynchronizeHisAfterAcceptanceDTO synchronizeHisAfterAcceptanceDTO) {

        log.info(" 》》》》》》》》》HisDetailSaveTask 入参 updateBatchByDetailIdAndInvoiceNoOracle-----"
                +JSON.toJSONString(synchronizeHisAfterAcceptanceDTO));
        try {

            deliveryOrderService.synchronizeHisAfterAcceptance(synchronizeHisAfterAcceptanceDTO);

        } catch (Exception e) {
            log.error("验收后同步his记录异常 ", e);
        }

    }
}
