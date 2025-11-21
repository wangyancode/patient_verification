package com.dincher.common.utils.fastDFS;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

/**
 * 基础工具类
 *
 * @Author: zhanglj
 * @Date: 2022-11-08 19:52
 */
public class BaseUtil {

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
     * 获取当前项目的路径地址
     * 用于获取文件地址
     *
     * @return
     */
    public static String getSystemBasePath() {
        String osName = System.getProperties().getProperty("os.name");
        if (osName.equals("Linux")) {
            return System.getProperty("user.dir");
        } else {
            String rootPath = Class.class.getClass().getResource("/").getPath();
            rootPath = rootPath.substring(1);
            rootPath.substring(0, rootPath.lastIndexOf("/"));
            return rootPath;
        }
    }

    /**
     * 得到文件流数组
     *
     * @param url 图片地址
     * @return
     */
    public static byte[] getFileStream(String url) {
        try {
            URL httpUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5 * 1000);
            InputStream inStream = conn.getInputStream();//通过输入流获取图片数据
            byte[] btImg = readInputStream(inStream);//得到图片的二进制数据
            return btImg;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 从输入流中获取数据
     *
     * @param inStream 输入流
     * @return
     * @throws Exception
     */
    public static byte[] readInputStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        inStream.close();
        return outStream.toByteArray();
    }


    /**
     * 获取网络图片流
     *
     * @param url
     * @return
     */
    public static InputStream getImageStream(String url) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setReadTimeout(5000);
            connection.setConnectTimeout(5000);
            connection.setRequestMethod("GET");
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = connection.getInputStream();
                return inputStream;
            }
        } catch (IOException e) {
            System.out.println("获取网络图片出现异常，图片路径为：" + url);
            e.printStackTrace();
        }
        return null;
    }

    public static String GetImageStr(String imgFilePath) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        byte[] data = null;

        // 读取图片字节数组
        try {
            InputStream in = new FileInputStream(imgFilePath);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 对字节数组Base64编码
        //BASE64Encoder encoder = new BASE64Encoder();

        // 改为
        Base64.Encoder encoder = Base64.getMimeEncoder();
        //requestValue = encoder.encodeToString(src);
        //Base64.Decoder decoder = Base64.getMimeDecoder();
        //byte[] base64DValue = decoder.decode(URLValue);

//        return encoder.encode(data);// 返回Base64编码过的字节数组字符串
        return encoder.encodeToString(data);// 返回Base64编码过的字节数组字符串
    }

    public static boolean GenerateImage(String imgStr, String imgFilePath) {// 对字节数组字符串进行Base64解码并生成图片
        if (imgStr == null) // 图像数据为空
            return false;
        if (imgStr.contains(",")) {
            String[] split = imgStr.split(",");
            imgStr = split[1];
        }
        Base64.Decoder decoder = Base64.getMimeDecoder();

//        BASE64Decoder decoder = new BASE64Decoder();
        try {
            // Base64解码
//            byte[] bytes = decoder.decodeBuffer(imgStr);
            byte[] bytes = decoder.decode(imgStr);
            for (int i = 0; i < bytes.length; ++i) {
                if (bytes[i] < 0) {// 调整异常数据
                    bytes[i] += 256;
                }
            }
            // 生成jpeg图片
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(bytes);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
