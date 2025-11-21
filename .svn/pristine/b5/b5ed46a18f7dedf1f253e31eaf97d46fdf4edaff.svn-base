package com.dincher.project.scm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dincher.common.exception.CustomException;
import com.dincher.common.utils.SecurityUtils;
import com.dincher.common.utils.ServletUtils;
import com.dincher.framework.security.service.TokenService;
import com.dincher.framework.web.domain.LoginUser;
import com.dincher.project.scm.mapper.DeliveryCompanyMapper;
import com.dincher.project.scm.domain.vo.DeliveryCompanyVO;
import com.dincher.project.scm.service.DeliveryCompanyService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;

/**
 * 配送单位(scm_delivery_company)业务实现类
 *
 * @author wangbin
 * @date 2023-12-06 11:45:07
 */
@Service
@Slf4j
@Transactional
public class DeliveryCompanyServiceImpl extends ServiceImpl<DeliveryCompanyMapper, DeliveryCompanyVO> implements DeliveryCompanyService {
    @Resource
    DeliveryCompanyMapper deliveryCompanyMapper;

    @Resource
    TokenService tokenService;

    /**
     * @param deliveryCompanyVO
     * @return List<DeliveryCompany>
     * @description //TODO  查询数据
     * @author wangbin
     * @date 2023-12-06 11:45:07
     */
    @Override
    public List<DeliveryCompanyVO> selectDataList(DeliveryCompanyVO deliveryCompanyVO) {
        return deliveryCompanyMapper.selectDataList(deliveryCompanyVO);
    }

    @Override
    public DeliveryCompanyVO saveDeliveryCompanyVO(DeliveryCompanyVO deliveryCompanyVO) {
        String token = UUID.randomUUID().toString().trim().replaceAll("-", "");
        deliveryCompanyVO.setToken(token);
        String distributeCode = deliveryCompanyVO.getDistributeCode();
        List<DeliveryCompanyVO> deliveryCompanyVOs = this.list(new QueryWrapper<DeliveryCompanyVO>()
                .eq("distribute_code", distributeCode));
        if(CollectionUtils.isNotEmpty(deliveryCompanyVOs)){
            throw new CustomException("配送企业编码重复");
        }
        this.save(deliveryCompanyVO);
        return deliveryCompanyVO;
    }

    @Override
    public DeliveryCompanyVO generateToken(DeliveryCompanyVO appVO) {
        DeliveryCompanyVO appVOReturn = getById(appVO.getCompanyId());
        String token = UUID.randomUUID().toString().trim().replaceAll("-", "");
        appVOReturn.setToken(token);
        this.updateById(appVOReturn);
        return appVOReturn;
    }

    @Override
    public DeliveryCompanyVO seeToken(DeliveryCompanyVO deliveryCompanyVO) {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        String password = loginUser.getPassword();
        if (!SecurityUtils.matchesPassword(deliveryCompanyVO.getPassword(), password)) {
            throw new CustomException("密码错误");
        }
        DeliveryCompanyVO deliveryCompanyVOReturn = getById(deliveryCompanyVO.getCompanyId());
        return deliveryCompanyVOReturn;
    }

    @Override
    public DeliveryCompanyVO generateTokenAgain(DeliveryCompanyVO appVO) {
        DeliveryCompanyVO appVOReturn = getById(appVO.getCompanyId());
        if (null == appVOReturn.getToken()) {
            throw new CustomException("未生成过token，不能重新生成");
        }
        String token = UUID.randomUUID().toString().trim().replaceAll("-", "");
        appVOReturn.setToken(token);
        this.updateById(appVOReturn);
        return appVOReturn;
    }
}

