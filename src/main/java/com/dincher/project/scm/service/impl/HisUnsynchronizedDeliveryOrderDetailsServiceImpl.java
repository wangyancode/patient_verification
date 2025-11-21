package com.dincher.project.scm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dincher.common.utils.bean.BeanUtils;
import com.dincher.project.scm.domain.DTO.HisUnsynchronizedOracleDataDTO;
import com.dincher.project.scm.mapper.HisUnsynchronizedDeliveryOrderDetailsMapper;
import com.dincher.project.scm.domain.vo.HisUnsynchronizedDeliveryOrderDetailsVO;
import com.dincher.project.scm.mapper.SynchronizeHisAfterAcceptanceMapper;
import com.dincher.project.scm.service.HisUnsynchronizedDeliveryOrderDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

/**
 * 配送单明细未同步到his的暂存表(scm_his_unsynchronized_delivery_order_details)业务实现类
 *
 * @author wangbin
 * @date 2024-05-07 09:15:39
 */
@Service
@Slf4j
public class HisUnsynchronizedDeliveryOrderDetailsServiceImpl extends ServiceImpl<HisUnsynchronizedDeliveryOrderDetailsMapper, HisUnsynchronizedDeliveryOrderDetailsVO> implements HisUnsynchronizedDeliveryOrderDetailsService {
    @Resource
    HisUnsynchronizedDeliveryOrderDetailsMapper hisUnsynchronizedDeliveryOrderDetailsMapper;

    @Resource
    private SynchronizeHisAfterAcceptanceMapper synchronizeHisAfterAcceptanceMapper;

     /** 
     * 
     * @description //TODO  查询数据
     * @author wangbin 
     * @date 2024-05-07 09:15:39
     * @param hisUnsynchronizedDeliveryOrderDetailsVO
     * @return List<HisUnsynchronizedDeliveryOrderDetails>
     */
    @Override
    public List<HisUnsynchronizedDeliveryOrderDetailsVO> selectDataList(HisUnsynchronizedDeliveryOrderDetailsVO hisUnsynchronizedDeliveryOrderDetailsVO) {
        return hisUnsynchronizedDeliveryOrderDetailsMapper.selectDataList(hisUnsynchronizedDeliveryOrderDetailsVO);     
    }

    @Override
    public int deleteDataById(Integer id) {
        return hisUnsynchronizedDeliveryOrderDetailsMapper.deleteDataById(id);
    }

    @Override
    public void synchronizedData() {
        List<HisUnsynchronizedDeliveryOrderDetailsVO> list = this.list();
        list.forEach(x ->{
            //查询his数据在不在
            //1在，直接修改
            //2不在，中间表不动，5分钟执行一次上面步骤
            List<HisUnsynchronizedOracleDataDTO> hisUnsynchronizedOracleDataDTOS = synchronizeHisAfterAcceptanceMapper.selectDataByDetailIdAndInvoiceNoOracle(x.getOrderdetailId(), x.getInvoiceNo());
            if(CollectionUtils.isNotEmpty(hisUnsynchronizedOracleDataDTOS)){
                synchronizeHisAfterAcceptanceMapper.updateBatchByDetailIdAndInvoiceNoOracle(x.getOrderdetailId(), x.getInvoiceNo());
                //删除中间表数据
                this.deleteDataById(x.getId());
            }else {
                List<HisUnsynchronizedOracleDataDTO> hisUnsynchronizedOracleDataDTOSByfnode5 = synchronizeHisAfterAcceptanceMapper.selectDataByDetailIdAndInvoiceNoOracleByfnode5(x.getOrderdetailId(), x.getInvoiceNo());
                if(CollectionUtils.isNotEmpty(hisUnsynchronizedOracleDataDTOSByfnode5)){
                    //删除中间表数据
                    this.deleteDataById(x.getId());
                }
            }
        });

    }

}

