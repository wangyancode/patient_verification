package com.dincher.common.utils.fastDFS;

import cn.hutool.core.util.StrUtil;
import com.dincher.common.exception.CustomException;
import com.dincher.project.system.domain.entity.Document;
import com.dincher.project.system.domain.vo.DocumentVO;
import com.dincher.project.system.service.DocumentService;
import org.apache.commons.io.FilenameUtils;
import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;


/**
 * fastdfs文件服务工具类
 *
 * @Author: zhanglj
 * @Date: 2022-11-08 19:52
 */
@Component
public class FastDFSUtils {

    public static final Logger logger = LoggerFactory.getLogger(FastDFSUtils.class);

    private static String file_server_path;

    private static String connect_timeout_in_seconds;

    private static String network_timeout_in_seconds;

    private static String charset;

    private static String http_anti_steal_token;

    private static String http_tracker_http_port;

    private static String tracker_servers;

    @Value("${fastDfs.server_path}")
    public void setFile_server_path(String server_path) {
        FastDFSUtils.file_server_path = server_path;
    }

    @Value("${fastDfs.connect_timeout_in_seconds}")
    public void setConnect_timeout_in_seconds(String connect_timeout_in_seconds) {
        FastDFSUtils.connect_timeout_in_seconds = connect_timeout_in_seconds;
    }

    @Value("${fastDfs.network_timeout_in_seconds}")
    public void setNetwork_timeout_in_secondss(String network_timeout_in_seconds) {
        FastDFSUtils.network_timeout_in_seconds = network_timeout_in_seconds;
    }

    @Value("${fastDfs.charset}")
    public void setCharset(String charset) {
        FastDFSUtils.charset = charset;
    }

    @Value("${fastDfs.http_anti_steal_token}")
    public void setHttp_anti_steal_token(String http_anti_steal_token) {
        FastDFSUtils.http_anti_steal_token = http_anti_steal_token;
    }

    @Value("${fastDfs.http_tracker_http_port}")
    public void setHttp_tracker_http_port(String http_tracker_http_port) {
        FastDFSUtils.http_tracker_http_port = http_tracker_http_port;
    }

    @Value("${fastDfs.tracker_servers}")
    public void setTracker_servers(String tracker_servers) {
        FastDFSUtils.tracker_servers = tracker_servers;
    }

    private static DocumentService documentService;

    @Autowired
    public void setDocumentService(DocumentService documentService) {
        FastDFSUtils.documentService = documentService;
    }


    /**
     * 初始化fastdfs配置
     *
     * @return
     */
    public static StorageClient initFastDfs() {
        StorageClient storageClient = null;
        TrackerServer trackerServer = null;
        StorageServer storageServer = null;
        try {
            // 初始化文件资源
            Properties properties = new Properties();
            //属性设置
            properties.put("fastdfs.connect_timeout_in_seconds", connect_timeout_in_seconds);
            properties.put("fastdfs.network_timeout_in_seconds", network_timeout_in_seconds);
            properties.put("fastdfs.charset", charset);
            properties.put("fastdfs.http_anti_steal_token", http_anti_steal_token);
            properties.put("fastdfs.http_tracker_http_port", http_tracker_http_port);
            properties.put("fastdfs.tracker_servers", tracker_servers);
            ClientGlobal.initByProperties(properties);
            // ClientGlobal.initByProperties("fastDFS.properties");
            // 链接FastDFS服务器，创建tracker和Stroage
            TrackerClient trackerClient = new TrackerClient();
            trackerServer = trackerClient.getConnection();
            String storageServerIp = getStorageServerIp(trackerClient, trackerServer);
            storageServer = getStorageServer(storageServerIp);
            storageClient = new StorageClient(trackerServer, storageServer);
        } catch (Exception e) {
            e.printStackTrace();
            if (trackerServer != null) {
                try {
                    trackerServer.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
            if (storageServer != null) {
                try {
                    storageServer.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }
        return storageClient;
    }

    /**
     * 将 MultipartFile 转换成 二进制数组 调用上传文件服务器工具类
     * 返回 Annex
     *
     * @param files
     * @return
     */
    public static Document uploadFastdfs(MultipartFile files) throws IOException {
        DocumentVO document = new DocumentVO();
        //验证文件类型
        Boolean mimeType = FileTypeUtils.verifyFile(files.getInputStream(), files.getOriginalFilename());
        if (!mimeType) {
            throw new CustomException("文件格式有误!");
        }
        String fileName = files.getOriginalFilename();
        document.setDocumentName(fileName);//文件名
        document.setDocumentType(fileName.substring(fileName.lastIndexOf(".") + 1));//文件类型(文件后缀名)
        byte[] bytes = null;
        bytes = files.getBytes();
        if (files.getSize() > 0) {
            double v = (double) files.getSize() / 1024 ;
            document.setDocumentSize(String.format("%.2f",v));//文件大小
        }
        String filePath = uploadFile(bytes, document.getDocumentType());
        // 赋值上传服务器返回路径
        document.setDocumentUrl(filePath);//文件路径
        document.setDeleteFlag(0);
        documentService.save(document);
        return document;
    }


    /**
     *
     * @description //TODO  批量上传文件
     * @author wangxiao
     * @date 2023/10/13
     * @param files map中一个key为uniqueValue，代表文件关联的记录主键;一个key为file,代表上传的文件
     * @return java.util.List<com.dincher.project.system.domain.vo.DocumentVO>
     */
    public static List<DocumentVO> multiUploadFastdfs(List<Map<String,Object>> files) throws IOException {
        List<DocumentVO> documentVOList = new ArrayList<>();
        for (Map<String,Object> map : files) {
            MultipartFile file = (MultipartFile)map.get("file");
            String uniqueValue = map.get("uniqueValue").toString();
            DocumentVO document = new DocumentVO();
            //验证文件类型
            Boolean mimeType = FileTypeUtils.verifyFile(file.getInputStream(), file.getOriginalFilename());
            if (!mimeType) {
                throw new CustomException("文件格式有误!");
            }
            String fileName = file.getOriginalFilename();
            document.setDocumentName(fileName);//文件名
            document.setDocumentType(fileName.substring(fileName.lastIndexOf(".") + 1));//文件类型(文件后缀名)
            byte[] bytes = null;
            bytes = file.getBytes();
            if (file.getSize() > 0) {
                double v = (double) file.getSize() / 1024 ;
                document.setDocumentSize(String.format("%.2f",v));//文件大小
            }
            String filePath = uploadFile(bytes, document.getDocumentType());
            // 赋值上传服务器返回路径
            document.setDocumentUrl(filePath);//文件路径
            document.setUniqueValue(uniqueValue);
            documentVOList.add(document);
        }
        documentService.saveBatch(documentVOList);
        return documentVOList;
    }

    /**
     * 将二进制数组上传到FastDFS服务器
     *
     * @param byteFile
     * @param ext_file
     * @return
     */
    public static String uploadFile(byte[] byteFile, String ext_file) {
        // 拼接服务区的文件路径
        StorageClient storageClient = initFastDfs();
        StringBuffer sbPath = new StringBuffer();
        try {

            //利用字节流上传文件
            String[] strings = storageClient.upload_file(byteFile, ext_file, null);
            sbPath.append(file_server_path);
            sbPath.append(StrUtil.join("/", strings));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sbPath.toString();
    }

    /**
     * 分片上传
     *
     * @param group_name
     * @param fileName
     * @param meta
     * @return
     */
    public static String uploadBreakpointFile(String group_name, String fileName, NameValuePair[] meta) {
        String[] results = null;
        File file = new File(fileName);
        long originalFileSize = file.length();
        long defaultSize = 1024 * 1024;
        byte[] file_buff;
        // 拼接服务区的文件路径
        StringBuffer sbPath = new StringBuffer();
        // 初始化文件资源
        StorageClient storageClient = initFastDfs();
        //NameValuePair[] vars = new NameValuePair[]{new NameValuePair("fileName", fileName), new NameValuePair("fileSize", String.valueOf(originalFileSize))};
        int number = (int) (originalFileSize / defaultSize), leftLength;
        number = originalFileSize % defaultSize == 0 ? number : number + 1;
        byte[] bytes;
        try {
            InputStream input = new FileInputStream(file);
            file_buff = new byte[input.available()];
            input.read(file_buff);
            if (originalFileSize > defaultSize) {
                // 如果文件块大，则实现分块上传，需要准备一个空的文件
                for (int i = 0; i < number; i++) {
                    if (originalFileSize - (i) * defaultSize < defaultSize) {
                        leftLength = (int) (originalFileSize - (i) * defaultSize);
                        leftLength = leftLength < 0 ? (int) originalFileSize : leftLength;
                        bytes = new byte[leftLength];
                        if (i == 0) {
                            results = storageClient.upload_appender_file(group_name, bytes, 0, leftLength, FilenameUtils.getExtension(fileName), meta);
                        } else {
                            /*采用追加的方式*/
                            storageClient.append_file(results[0], results[1], bytes, 0, leftLength);
                        }
                    } else {
                        bytes = new byte[(int) defaultSize];
                        leftLength = (int) defaultSize;
                        if (i == 0) {
                            results = storageClient.upload_appender_file(group_name, bytes, 0, leftLength, FilenameUtils.getExtension(fileName), meta);
                        } else {
                            /*采用追加的方式*/
                            storageClient.append_file(results[0], results[1], bytes, 0, leftLength);
                        }
                    }
                }
                //写入内容
                storageClient.modify_file(results[0], results[1], 0, file_buff, 0, file_buff.length);
            } else {
                //如果文件比默认的文件要小，则直接上传
                results = storageClient.upload_file(group_name, file_buff, FilenameUtils.getExtension(fileName), meta);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        sbPath.append(file_server_path);
        sbPath.append(StrUtil.join("/", results));
        return sbPath.toString();

    }
/*
            String url = new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());
            File temp = null;
            temp = new File(ConstantField.SERVER_PATH_FILE + fileName + ".mp4");
            if (temp.exists()) {
                temp.delete();
            }
            temp.createNewFile();
            files.transferTo(temp);
            temp.deleteOnExit();
            String filePath = uploadBreakpointFile("group1", temp.getAbsolutePath(), null);
*/

    /**
     * @param storagePath 文件的全部路径 如：group1/M00/00/00/wKgRsVjtwpSAXGwkAAAweEAzRjw471.jpg
     * @return -1失败,0成功
     * @throws IOException
     * @throws Exception
     */
    public static Boolean deleteFile(String storagePath) throws MyException, IOException {
        // 拼接服务区的文件路径
        StorageClient storageClient = initFastDfs();
        int result = -1;
        result = storageClient.delete_file("group1", storagePath);
        logger.info("删除fastdfs文件服务器中的文件：" + "group1/" + storagePath);
        if (result == -1) {
            return false;
        }
        return true;
    }

    /**
     * 得到Storage服务
     *
     * @param storageIp
     * @return 返回Storage服务
     */
    public static StorageServer getStorageServer(String storageIp) {
        StorageServer storageServer = null;
        if (storageIp != null && !("").equals(storageIp)) {
            try {
                // ip port store_path下标
                storageServer = new StorageServer(storageIp, 23000, 0);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    storageServer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return storageServer;
    }

    /**
     * 获得可用的storage IP
     *
     * @param trackerClient
     * @param trackerServer
     * @return 返回storage IP
     */
    public static String getStorageServerIp(TrackerClient trackerClient, TrackerServer trackerServer) {
        String storageIp = null;
        StorageServer storageServer = null;
        if (trackerClient != null && trackerServer != null) {
            try {
                storageServer = trackerClient.getStoreStorage(trackerServer, "group1");
                storageIp = storageServer.getSocket().getInetAddress().getHostAddress();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    storageServer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return storageIp;
    }


    /**
     * @param  documentVO
     * @return -1失败,0成功
     * @throws IOException
     * @throws Exception
     */
    public static void fastdfsDownLoad( DocumentVO documentVO, HttpServletResponse response) throws MyException, IOException {
        OutputStream outputStream =null;
        try {
            String documentName = documentVO.getDocumentName();
            documentName=documentName.substring(0,documentName.indexOf("."));
            StorageClient storageClient = initFastDfs();
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(documentName, "UTF-8") + "."+documentVO.getDocumentType());
            String storagePath = documentVO.getDocumentUrl();
            storagePath=storagePath.substring(storagePath.indexOf("group1/")+7);

            byte[] bytes = storageClient.download_file("group1", storagePath);
            outputStream = response.getOutputStream();
            outputStream.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                outputStream.flush();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}

