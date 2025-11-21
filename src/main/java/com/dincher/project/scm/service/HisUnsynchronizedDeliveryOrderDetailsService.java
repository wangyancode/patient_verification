package com.dincher.project.scm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dincher.project.scm.domain.vo.HisUnsynchronizedDeliveryOrderDetailsVO;
import java.util.List;

/**
 * 配送单明细未同步到his的暂存表(scm_his_unsynchronized_delivery_order_details)业务接口
 *
 * @author wangbin
 * @date 2024-05-07 09:15:39
 */
public interface HisUnsynchronizedDeliveryOrderDetailsService extends IService<HisUnsynchronizedDeliveryOrderDetailsVO> {

    public List<HisUnsynchronizedDeliveryOrderDetailsVO> selectDataList(HisUnsynchronizedDeliveryOrderDetailsVO hisUnsynchronizedDeliveryOrderDetailsVO);

    public int deleteDataById(Integer id);

    public void synchronizedData();
}

