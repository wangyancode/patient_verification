package com.dincher.common.utils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

/**
 * @author yangshuai
 * @date 2023-5-11 0011 16:57
 */
public class SortUtil<T> {

    /**
     * 根据对象中字符串数字字段排序
     *
     * @param list        待排序列表
     * @param sortField   排序字段
     * @param isAscending 是否升序
     */
    public void sortByStringNumberField(List<T> list, String sortField, boolean isAscending) {
        Comparator<T> comparator = Comparator.comparing(obj -> new BigDecimal(getFieldValue(obj, sortField)));
        if (!isAscending) {
            comparator = comparator.reversed();
        }
        list.sort(comparator);
    }

    /**
     * 获取对象中指定字段的值
     *
     * @param obj       对象
     * @param fieldName 字段名
     * @return 字段值
     */
    private static <T> String getFieldValue(T obj, String fieldName) {
        try {
            Field field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            Object value = field.get(obj);
            return value != null ? value.toString() : "";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
