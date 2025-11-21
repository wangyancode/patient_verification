package com.dincher.common.utils;

import com.dincher.framework.security.service.BASE64DecodedMultipartFileImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;

/**
 * Base64操作图片
 *
 * @Author: zhanglj
 * @Date: 2022-09-01
 */
public class Base64Utils {

    protected static final Logger logger = LoggerFactory.getLogger(Base64Utils.class);

    public static MultipartFile base64ToMultipart(String base64) {
        logger.info("base64转MultipartFile入参：" + base64);
        String[] baseStrs = base64.split(",");
        Base64.Decoder decoder = Base64.getMimeDecoder();
        byte[] b = new byte[0];
        b = decoder.decode(baseStrs[1]);
        for (int i = 0; i < b.length; ++i) {
            if (b[i] < 0) {
                b[i] += 256;
            }
        }
        return new BASE64DecodedMultipartFileImpl(b, baseStrs[0]);
    }

}
