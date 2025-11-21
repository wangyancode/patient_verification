package com.dincher.project.scm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dincher.project.scm.dao.PatientRecordsDao;
import com.dincher.project.scm.domain.PatientRecords;
import org.apache.ibatis.annotations.SelectProvider;

/**
 * 参数配置 数据层
 */
public interface PatientRecordsMapper extends BaseMapper<PatientRecords> {

    @SelectProvider(type = PatientRecordsDao.class, method = "selectPageList")
    Page<PatientRecords> selectPageList(Page page);
}