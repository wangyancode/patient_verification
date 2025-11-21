package com.dincher.project.scm.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dincher.framework.aspectj.lang.annotation.DataSource;
import com.dincher.framework.aspectj.lang.enums.DataSourceType;
import com.dincher.project.scm.domain.DTO.AppVersionsDTO;
import com.dincher.project.scm.domain.DTO.HisUnsynchronizedOracleDataDTO;
import com.dincher.project.scm.domain.vo.AppVersionsVO;

import java.util.List;

/**
 * 同步数据
 *
 * @author yc
 * @date 2023-12-21 13:32:31
 */
public interface SynchronizeHisAfterAcceptanceMapper {
  

    @DataSource(value = DataSourceType.SLAVE)
    public Boolean updateBatchByDetailIdAndInvoiceNoOracle(String orderdetailId,String invoiceNo);

    @DataSource(value = DataSourceType.SLAVE)
    public List<HisUnsynchronizedOracleDataDTO> selectDataByDetailIdAndInvoiceNoOracle(String orderdetailId, String invoiceNo);

    @DataSource(value = DataSourceType.SLAVE)
    public List<HisUnsynchronizedOracleDataDTO> selectDataByDetailIdAndInvoiceNoOracleByfnode5(String orderdetailId, String invoiceNo);
}

