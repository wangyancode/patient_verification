package com.dincher.project.scm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dincher.project.scm.domain.vo.DeliveryCompanyVO;
import java.util.List;

/**
 * 配送单位(scm_delivery_company)业务接口
 *
 * @author wangbin
 * @date 2023-12-06 11:45:06
 */
public interface DeliveryCompanyService extends IService<DeliveryCompanyVO> {

    public List<DeliveryCompanyVO> selectDataList(DeliveryCompanyVO deliveryCompanyVO);

    public DeliveryCompanyVO saveDeliveryCompanyVO(DeliveryCompanyVO deliveryCompanyVO);

    public DeliveryCompanyVO generateToken(DeliveryCompanyVO appVO);

    public DeliveryCompanyVO seeToken(DeliveryCompanyVO appVO);

    public DeliveryCompanyVO generateTokenAgain(DeliveryCompanyVO appVO);
}

