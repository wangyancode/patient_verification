package com.dincher.project.system.controller;

import com.dincher.common.utils.Base64Utils;
import com.dincher.common.utils.fastDFS.FastDFSUtils;
import com.dincher.framework.web.controller.BaseController;
import com.dincher.framework.web.domain.AjaxResult;
import com.dincher.project.system.domain.vo.DocumentVO;
import com.dincher.project.system.service.DocumentService;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.csource.common.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * 系统文件(sys_document)控制层
 *
 * @Author: zhanglj
 * @Date: 2022-11-08 19:02
 */
@RestController
@Api(tags = "文件服务(sys_document)API")
@RequestMapping("/system/document")
@ApiSupport(order = 1)
public class DocumentController extends BaseController {

    @Autowired
    private DocumentService documentService;

    /**
     * 上传文件到FastDFS服务器
     * FastDFS文件上传(全局复用)
     *
     * @param file
     */
    @ApiOperation("FastDFS文件上传(全局复用)")
    @ApiImplicitParams({@ApiImplicitParam(name = "file", paramType = "form", value = "文件（支持任意类型文件上传）", dataType = "file", required = true)})
    @RequestMapping(value = "/fastDFSUpload", method = RequestMethod.POST)
    public AjaxResult fastDFSUpload(@RequestPart("file") MultipartFile file) throws IOException {
        DocumentVO documentVO = documentService.fastDFSUpload(file);
        return AjaxResult.success(documentVO);
    }

    /**
     * base64转MultipartFile上传到FastDFS服务器
     *
     * @param base64
     */
    @ApiOperation("base64转MultipartFile上传到FastDFS服务器")
    @RequestMapping(value = "/base64FastDFSUpload", method = RequestMethod.POST)
    @ApiImplicitParam(name = "base64", value = "base64", required = true, dataType = "String")
    public AjaxResult base64FastDFSUpload(String base64) throws IOException {
        MultipartFile multipartFile = Base64Utils.base64ToMultipart(base64);
        DocumentVO documentVO = documentService.fastDFSUpload(multipartFile);
        return AjaxResult.success(documentVO);
    }

    /**
     * 上传文件到FastDFS服务器  此方法不允许改
     * 单个文件上传(文件流)
     *
     * @param file
     */
    @RequestMapping(value = "/fastDFSUploadFragment", method = RequestMethod.POST)
    @ApiOperation("FastDFS分片上传")
    @ApiImplicitParams({@ApiImplicitParam(name = "file", paramType = "form", value = "文件（支持任意类型文件上传）", dataType = "file", required = true)})
    public AjaxResult fastDFSUploadFragment(@RequestPart("file") MultipartFile file) {
        String path = FastDFSUtils.uploadBreakpointFile("group1", file.getOriginalFilename(), null);
        return AjaxResult.success(path);
    }

    /**
     * fastdfs删除文件
     *
     * @param filePath
     */
    @RequestMapping(value = "/deleteFastDfsFile", method = RequestMethod.POST)
    @ApiOperation("FastDFS删除文件")
    public AjaxResult fastDFSUploadFragment(String filePath) throws MyException, IOException {
        Boolean aBoolean = FastDFSUtils.deleteFile(filePath);
        return AjaxResult.success(aBoolean);
    }

    /**
     * fastdfs下载文件
     *
     * @param id 文件
     * @return
     */
    @GetMapping("/fastdfs/download/{id}")
    public void fastdfsDownLoad(@PathVariable Integer id, HttpServletResponse response, HttpServletRequest request) {
        DocumentVO documentVO = documentService.getById(id);
        if(null==documentVO){
            return;
        }
        try {
            FastDFSUtils.fastdfsDownLoad(documentVO,response);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

