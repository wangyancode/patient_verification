package com.dincher.common.utils.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Bean 工具类
 * 
 *
 */
public class BeanUtils extends org.springframework.beans.BeanUtils
{
    /** Bean方法名中属性名开始的下标 */
    private static final int BEAN_METHOD_PROP_INDEX = 3;

    /** * 匹配getter方法的正则表达式 */
    private static final Pattern GET_PATTERN = Pattern.compile("get(\\p{javaUpperCase}\\w*)");

    /** * 匹配setter方法的正则表达式 */
    private static final Pattern SET_PATTERN = Pattern.compile("set(\\p{javaUpperCase}\\w*)");

    /**
     * 单个拷贝
     *
     * @param o     拷贝对象
     * @param clazz 拷贝之后的类型
     * @return 拷贝之后的对象
     */
    public static <T, O> T copyProperties(O obj, Class<T> clazz) {
        if (Objects.isNull(obj)) {
            return null;
        }
        JSONObject jsonObject = (JSONObject) JSON.toJSON(obj);
        return jsonObject.toJavaObject(clazz);
    }

    /**
     * 获取对象的setter方法。
     * 
     * @param obj 对象
     * @return 对象的setter方法列表
     */
    public static List<Method> getSetterMethods(Object obj)
    {
        // setter方法列表
        List<Method> setterMethods = new ArrayList<Method>();

        // 获取所有方法
        Method[] methods = obj.getClass().getMethods();

        // 查找setter方法

        for (Method method : methods)
        {
            Matcher m = SET_PATTERN.matcher(method.getName());
            if (m.matches() && (method.getParameterTypes().length == 1))
            {
                setterMethods.add(method);
            }
        }
        // 返回setter方法列表
        return setterMethods;
    }

    /**
     * 获取对象的getter方法。
     * 
     * @param obj 对象
     * @return 对象的getter方法列表
     */

    public static List<Method> getGetterMethods(Object obj)
    {
        // getter方法列表
        List<Method> getterMethods = new ArrayList<Method>();
        // 获取所有方法
        Method[] methods = obj.getClass().getMethods();
        // 查找getter方法
        for (Method method : methods)
        {
            Matcher m = GET_PATTERN.matcher(method.getName());
            if (m.matches() && (method.getParameterTypes().length == 0))
            {
                getterMethods.add(method);
            }
        }
        // 返回getter方法列表
        return getterMethods;
    }

    /**
     * 检查Bean方法名中的属性名是否相等。<br>
     * 如getName()和setName()属性名一样，getName()和setAge()属性名不一样。
     * 
     * @param m1 方法名1
     * @param m2 方法名2
     * @return 属性名一样返回true，否则返回false
     */

    public static boolean isMethodPropEquals(String m1, String m2)
    {
        return m1.substring(BEAN_METHOD_PROP_INDEX).equals(m2.substring(BEAN_METHOD_PROP_INDEX));
    }


    /**
     * List 对象拷贝
     *
     * @param list 源
     * @param <T>  目标
     * @return 目标
     */
    public static <T, E> List copyList(List<T> list, Class<E> clazz) {
        if (CollectionUtils.isEmpty(list)) {
            return new ArrayList();
        }
        return JSON.parseArray(JSON.toJSONString(list), clazz);
    }

    /**
     * MAP拷贝
     *
     * @param map 源
     * @return 目标
     */
    public static Map<String, Object> copyMap(Map map) {
        if (CollectionUtils.isEmpty(map)) {
            return new HashMap<>();
        }
        return JSON.parseObject(JSON.toJSONString(map));
    }

    /**
     * json 转 Map
     *
     * @param json
     * @return
     */
    public static Map<String, Object> jsonToMap(String json) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(json, Map.class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
