package com.dincher.framework.security.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * base64转MultipartFile文件file
 *
 * @author zhanglj
 * @date 2022-11-09 10:05:23
 */

public class BASE64DecodedMultipartFileImpl implements MultipartFile {

    private byte[] imgContent;
    private String header;

    public BASE64DecodedMultipartFileImpl(byte[] imgContent, String header) {
        this.imgContent = imgContent;
        this.header = header.split(";")[0];
    }

    @Override
    public String getName() {
        // TODO - implementation depends on your requirements
        return System.currentTimeMillis() + Math.random() + "." + header.split("/")[1];
    }


    @Override
    public String getOriginalFilename() {
        // TODO - implementation depends on your requirements
        return System.currentTimeMillis() + (int) Math.random() * 10000 + "." + header.split("/")[1];
    }

    @Override
    public String getContentType() {
        // TODO - implementation depends on your requirements
        return header.split(":")[1];
    }

    @Override
    public boolean isEmpty() {
        return imgContent == null || imgContent.length == 0;
    }

    @Override
    public long getSize() {
        return imgContent.length;
    }

    @Override
    public byte[] getBytes() throws IOException {
        return imgContent;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(imgContent);
    }

    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {
        FileOutputStream aa = new FileOutputStream(dest);
        aa.write(imgContent);
        aa.close();
    }

}