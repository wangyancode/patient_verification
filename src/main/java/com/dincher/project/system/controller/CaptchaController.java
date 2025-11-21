package com.dincher.project.system.controller;

import com.dincher.common.constant.Constants;
import com.dincher.common.utils.IdUtils;
import com.dincher.common.utils.sign.Base64;
import com.dincher.framework.redis.RedisCache;
import com.dincher.framework.web.domain.ResponseEntity;
import com.dincher.framework.web.util.ResponseUtil;
import com.dincher.project.system.domain.vo.CaptchaVO;
import com.dincher.project.system.service.ConfigService;
import com.google.code.kaptcha.Producer;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 验证码操作处理
 * 
 * @author ruoyi
 */
@RestController
public class CaptchaController
{
    @Resource(name = "captchaProducer")
    private Producer captchaProducer;

    @Resource(name = "captchaProducerMath")
    private Producer captchaProducerMath;

    @Resource
    private RedisCache redisCache;

    @Resource
    ConfigService configService;
    
    // 验证码类型
    @Value("${system.captchaType}")
    private String captchaType;

    /**
     * 生成验证码
     */
    @GetMapping("/captchaImage")
    @ApiOperation(value = "生成验证码")
    public ResponseEntity<CaptchaVO> getCode(HttpServletResponse response) throws IOException
    {
        // 保存验证码信息
        String uuid = IdUtils.simpleUUID();
        String verifyKey = Constants.CAPTCHA_CODE_KEY + uuid;

        String capStr = null, code = null;
        BufferedImage image = null;

        // 生成验证码
        if ("math".equals(captchaType))
        {
            String capText = captchaProducerMath.createText();
            capStr = capText.substring(0, capText.lastIndexOf("@"));
            code = capText.substring(capText.lastIndexOf("@") + 1);
            image = captchaProducerMath.createImage(capStr);
        }
        else if ("char".equals(captchaType))
        {
            capStr = code = captchaProducer.createText();
            image = captchaProducer.createImage(capStr);
        }

        redisCache.setCacheObject(verifyKey, code, Constants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
        // 转换流信息写出
        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
        try
        {
            ImageIO.write(image, "jpg", os);
        }
        catch (IOException e)
        {
            return ResponseUtil.error(e.getMessage());
        }
        System.out.println("code : "+ code+" uuid : " + uuid);

        CaptchaVO captchaVO = new CaptchaVO();
        captchaVO.setUuid(uuid);
        captchaVO.setImg(Base64.encode(os.toByteArray()));

        //查询配置内容是否开启验证码
        String s = configService.selectConfigByKey("authCodeFlag");
        captchaVO.setCaptchaEnabled(s);
        return ResponseUtil.success(captchaVO);
    }
}
