package com.dincher.project.system.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dincher.common.utils.fastDFS.FastDFSUtils;
import com.dincher.project.system.domain.entity.Document;
import com.dincher.project.system.domain.vo.DocumentVO;
import com.dincher.project.system.mapper.DocumentMapper;
import com.dincher.project.system.service.DocumentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 系统文件(sys_document)业务实现类
 *
 * @author yangshuai
 * @date 2022-08-12 10:59:06
 */
@Service
@Slf4j
@Transactional
public class DocumentServiceImpl extends ServiceImpl<DocumentMapper, DocumentVO> implements DocumentService {

    /**
     * FastDFS文件上传(全局复用)
     *
     * @param file
     * @return
     */
    @Override
    public DocumentVO fastDFSUpload(MultipartFile file) throws IOException {
        //调用上传fastDfs工具类
        Document document = FastDFSUtils.uploadFastdfs(file);
        DocumentVO documentVO = JSON.parseObject(JSON.toJSONString(document), DocumentVO.class);
        return documentVO;
    }

    /**
     *
     * @description //TODO  批量上传文件
     * @author wangxiao
     * @date 2023/10/13
     * @param fileList
     * @return java.util.List<com.dincher.project.system.domain.vo.DocumentVO>
     */
    @Override
    public List<DocumentVO> multiFastDFSUpload(List<Map<String,Object>> fileList) throws IOException {
        return FastDFSUtils.multiUploadFastdfs(fileList);
    }
}

