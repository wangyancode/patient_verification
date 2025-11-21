package com.dincher.common.utils;

/**
 * 自定义编码工具类
 *
 * @author zhanglj
 * @date 2022-11-09 10:05:23
 */
public class CodeUtils {

    /**
     * 生成自增自定义编码
     *
     * @param prefix 前缀
     * @param nowNum 去前缀后的当前num数
     * @return String
     */
    public static String generateCode(String prefix, String nowNum) {
        // %nd 输出的整型宽度至少为n位，右对齐，%4d即宽度至少为4位，位数大于4则输出实际位数，0表示用0补齐
        return prefix + String.format("%04d", Integer.parseInt(nowNum) + 1);
    }

    /**
     * 生成自增自定义编码
     *
     * @param prefix 前缀
     * @param nowNum 去前缀后的当前num数
     * @return String
     */
    public static String generateThreeCode(String prefix, String nowNum) {
        // %nd 输出的整型宽度至少为n位，右对齐，%3d即宽度至少为3位，位数大于3则输出实际位数，0表示用0补齐
        return prefix + String.format("%03d", Integer.parseInt(nowNum) + 1);
    }

}
