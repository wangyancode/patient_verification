package com.dincher.project.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dincher.common.wrapper.CommonWrapper;
import com.dincher.project.system.domain.vo.DocumentVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 系统文件(sys_document)业务接口
 *
 * @author yangshuai
 * @date 2022-08-12 10:59:06
 */
public interface DocumentService extends IService<DocumentVO>, CommonWrapper<DocumentVO> {

    /**
     * FastDFS文件上传(全局复用)
     * @param file
     */
    DocumentVO fastDFSUpload(MultipartFile file) throws IOException;

    List<DocumentVO> multiFastDFSUpload(List<Map<String,Object>> fileList) throws IOException;
}

