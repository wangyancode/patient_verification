package com.dincher.project.scm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dincher.common.exception.CustomException;
import com.dincher.common.utils.StringUtils;
import com.dincher.project.scm.domain.DTO.AppVersionsDTO;
import com.dincher.project.scm.domain.vo.AppVersionsVO;
import com.dincher.project.scm.mapper.AppVersionsMapper;
import com.dincher.project.scm.service.AppVersionsService;
import com.dincher.project.system.domain.vo.DocumentVO;
import com.dincher.project.system.service.DocumentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * app版本号管理(its_app_versions)业务实现类
 *
 * @author yc
 * @date 2023-12-21 13:32:31
 */
@Service
@Slf4j
@Transactional
public class AppVersionsServiceImpl extends ServiceImpl<AppVersionsMapper, AppVersionsVO> implements AppVersionsService {
    @Resource
    AppVersionsMapper appVersionsMapper;

    @Autowired
    private DocumentService documentService;
   
     /** 
     * 
     * @description //TODO  查询数据
     * @author yc 
     * @date 2023-12-21 13:32:31
     * @param appVersionsVO
     * @return List<AppVersions>
     */
    @Override
    public List<AppVersionsVO> selectDataList(AppVersionsVO appVersionsVO) {
        return appVersionsMapper.selectDataList(appVersionsVO);     
    }
    
    @Override
    public List<AppVersionsVO> pageByDTO(AppVersionsDTO appVersionsDTO) {
        return appVersionsMapper.pageByDTO(appVersionsDTO);     
    }

    @Override
    public AppVersionsVO getByIdVO(Integer id) {

        AppVersionsVO byId = getById(id);
        DocumentVO documentVO = documentService.getById(byId.getDocumentId());
        byId.setDocumentUrl(documentVO.getDocumentUrl());
        return byId;
    }

    @Override
    public AppVersionsVO checkVersions(String versionsNo) {

        if(StringUtils.isBlank(versionsNo)){
            throw new CustomException("入参为空");
        }
        //只会存在一条有效的数据
        List<AppVersionsVO> list = list();
        if(CollectionUtils.isNotEmpty(list)){
            AppVersionsVO appVersionsVO = list.get(0);
            int i = StringUtils.compareVersions(versionsNo, appVersionsVO.getVersionsNo());
            if(i < 0){
                return getByIdVO(appVersionsVO.getApp_versions_id());
            }
        }

        return null;
    }

    @Override
    public AppVersionsVO getLastVersions() {
        List<AppVersionsVO> list = list();
        if(CollectionUtils.isNotEmpty(list)){
            return list.get(0);
        }
        return null;
    }

    @Override
    public boolean saveVO(AppVersionsVO appVersionsVO) {

        if(appVersionsVO != null){
            String versionsNo = appVersionsVO.getVersionsNo();
            Integer documentId = appVersionsVO.getDocumentId();

            if(StringUtils.isBlank(versionsNo)){
                throw new CustomException("版本号为空");
            }
            if(documentId == null){
                throw new CustomException("请上传文件");
            }

            //删除所有
            remove(new QueryWrapper<>());
        }
        //删除旧数据
        return save(appVersionsVO);
    }

}

