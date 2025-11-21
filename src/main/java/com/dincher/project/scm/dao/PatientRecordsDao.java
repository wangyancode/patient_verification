package com.dincher.project.scm.dao;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;


@Repository
public class PatientRecordsDao {


    public String selectPageList(Page page) {
        String sql = "select * from patient_records where 1=1 ";
        return sql ;
    }

}
