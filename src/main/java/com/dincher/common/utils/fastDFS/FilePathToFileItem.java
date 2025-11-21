package com.dincher.common.utils.fastDFS;

import org.springframework.mock.web.MockMultipartFile;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * 文件转换工具类
 *
 * @Author: zhanglj
 * @Date: 2022-11-08 19:52
 */
@Component
public class FilePathToFileItem implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public static Logger logger = LoggerFactory.getLogger(FilePathToFileItem.class);

    /**
     * 获取本地路径返回 MultipartFile 数据类型
     *
     * @param filePath
     * @return
     * @throws IOException
     */
    public static MultipartFile filePathToFileItem(String filePath) throws IOException {
        File file = new File(filePath);
        FileInputStream fileInputStream = null;
        fileInputStream = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile(file.getName(), file.getName(), ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStream);
        return multipartFile;
    }


    /**
     * 将网络 文件 下载到 系统根目录
     *
     * @param path
     * @return
     * @throws Exception
     */
    public static String uploadtoben(String path) throws Exception {
        logger.info("uploadtoben中网络路径+" + path);
        String substring = path.substring(path.lastIndexOf("."));
        SimpleDateFormat tempDate = new SimpleDateFormat("yyyyMMddHHmmss");
        String suijiNumber = tempDate.format(new Date()) + String.format("%06d", getRandomRange(10000, 1000));
        String name = BaseUtil.getSystemBasePath() + "/uploadfiles/" + suijiNumber + substring;
        URL url = new URL(path);
        //链接url
        URLConnection uc = url.openConnection();
        //获取输入流
        InputStream in = uc.getInputStream();
        byte[] data = readInputStream(in);
        File file = new File(name);
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
            outputStream.write(data);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            outputStream.close();
        }
        logger.info("uploadtoben中下载到本地路径+" + name);
        return name;
    }

    /**
     * 获取范围内int值
     *
     * @param max
     * @param min
     * @return int
     */
    public static int getRandomRange(int max, int min) {
        return (int) (Math.random() * (max - min) + min);
    }

    /**
     * 输入流转字节数组
     *
     * @param inStream
     * @return
     * @throws Exception
     */
    public static byte[] readInputStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        //创建一个Buffer字符串
        byte[] buffer = new byte[1024];
        //每次读取的字符串长度，如果为-1，代表全部读取完毕
        int len = 0;
        //使用一个输入流从buffer里把数据读取出来
        while ((len = inStream.read(buffer)) != -1) {
            //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
            outStream.write(buffer, 0, len);
        }
        //关闭输入流
        inStream.close();
        //把outStream里的数据写入内存
        return outStream.toByteArray();
    }

    /**
     * 二进制文件转换MultipartFile
     *
     * @param bytes
     * @param extName
     * @return
     */
    public static MultipartFile getMultipartFile(byte[] bytes, String extName) {
        logger.info("二进制转换MultipartFile开始");
        String originalFilename = UUID.randomUUID().toString() + "." + extName;
        MockMultipartFile mockMultipartFile = null;
        //java7新特性  不用手动关闭流
        try (InputStream inputStream = new ByteArrayInputStream(bytes)) {
            mockMultipartFile = new MockMultipartFile(ContentType.APPLICATION_OCTET_STREAM.toString(), originalFilename, ContentType.APPLICATION_OCTET_STREAM.toString(), inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mockMultipartFile;
    }

}
