package com.dincher.project.scm.mapper;

import com.dincher.project.scm.domain.vo.HisUnsynchronizedDeliveryOrderDetailsVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;

/**
 * 配送单明细未同步到his的暂存表(scm_his_unsynchronized_delivery_order_details)数据库访问层
 *
 * @author wangbin
 * @date 2024-05-07 09:15:39
 */
public interface HisUnsynchronizedDeliveryOrderDetailsMapper extends BaseMapper<HisUnsynchronizedDeliveryOrderDetailsVO> {
  
    List<HisUnsynchronizedDeliveryOrderDetailsVO> selectDataList(HisUnsynchronizedDeliveryOrderDetailsVO hisUnsynchronizedDeliveryOrderDetailsVO);

    public int deleteDataById(Integer id);
}

