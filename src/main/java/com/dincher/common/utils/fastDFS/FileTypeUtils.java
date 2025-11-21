package com.dincher.common.utils.fastDFS;

import org.apache.tika.parser.ParseContext;
import org.apache.commons.lang3.StringUtils;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.HttpHeaders;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.metadata.TikaMetadataKeys;
import org.apache.tika.mime.MediaType;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.Parser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 文件类型判断工具类
 *
 * @Author: zhanglj
 * @Date: 2022-11-08 19:52
 */
public class FileTypeUtils {

    public static final Logger logger = LoggerFactory.getLogger(FileTypeUtils.class);

    /**
     * 获取文件getMimeType
     *
     * @param file
     * @return
     */
    public static String getMimeType(File file) {
        if (file.isDirectory()) {
            return "the target is a directory";
        }
        AutoDetectParser parser = new AutoDetectParser();
        parser.setParsers(new HashMap<MediaType, Parser>());
        Metadata metadata = new Metadata();
        metadata.add(TikaMetadataKeys.RESOURCE_NAME_KEY, file.getName());
        InputStream stream;
        try {
            stream = new FileInputStream(file);
            parser.parse(stream, new DefaultHandler(), metadata, new ParseContext());
            stream.close();
        } catch (TikaException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return metadata.get(HttpHeaders.CONTENT_TYPE);
    }

    /**
     * 获取文件类型
     *
     * @param inputStream
     * @param name
     * @return
     */
    public static String getMimeType(InputStream inputStream, String name) {
        Tika tika = new Tika();
        try {
            String fileType = tika.detect(inputStream, name);
            return fileType;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 验证文件类型是否合格
     *
     * @param inputStream
     * @param name
     * @return
     */
    public static Boolean verifyFile(InputStream inputStream, String name) {
        String mimeType = getMimeType(inputStream, name);
        logger.info("上传文件类型：{}", mimeType);
        if (StringUtils.isNotBlank(mimeType)) {
            for (FileTypeEnum value : FileTypeEnum.values()) {
//                if (value.getName().matches(mimeType)) {
//                    return true;
//                }
                Pattern pattern = Pattern.compile(value.getName());
                Matcher matcher = pattern.matcher(mimeType);
                if (matcher.matches()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 类型枚举
     */
    public enum FileTypeEnum {
        //dcm
        DCM("application/dicom"),
        ZIP("application/zip"),//zip
        RAR("application/x-rar-compressed"),//RAR
        IMAGE("image/.*"),//图片
        VIDEO("video/.*"),//视频
        WORDDOC("application/msword"), //word(.docx)
        WORDDOCX("application/vnd.openxmlformats-officedocument.wordprocessingml.document"), //word(.docx)
        PDF("application/pdf"),//PDF
        HTML("application/xhtml+xml"),//HTML
        TEXTHTML("text/html"),//TEXT/HTML
        EXE("application/xhtml+xml"),//EXE
        DOSEXEC("application/x-dosexec"),//EXE
        WORDDOCWPS("application/x-tika-msoffice"),
        WORDDOCOFFICE("application/x-tika-ooxml"),
        MSWORD5("application/msword5"),
        MSDOWNLOAD("application/x-msdownload"),
        TXT("text/plain"),
        EXCEL("application/vnd.ms-excel"),
        XLSX("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"),
        XML("application/xml"),
        SH("application/x-sh"),
        APK("application/vnd.android.package-archive"),
        WEBM("application/x-matroska");

        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        FileTypeEnum(String name) {
            this.name = name;
        }
    }

}
