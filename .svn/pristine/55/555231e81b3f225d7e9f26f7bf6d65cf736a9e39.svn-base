package com.dincher.common.wrapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dincher.common.constant.HttpStatus;
import com.dincher.common.wrapper.string.StringUtil;
import com.dincher.framework.web.page.TableDataInfo;
import com.github.pagehelper.PageInfo;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @author wangbin
 * @version V2.0
 * @interfaceName CommonWrapper
 * @description
 * @date 2022/06/23 16:49
 **/
public interface CommonWrapper<T> {

    default Wrapper<T> getListWrapper(Map<String, Object> param){
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
//        if (param == null || param.isEmpty()) {
//            return null;
//        }
        param.forEach(
                (key, value) -> {
                    if (StringUtil.isNull(value)) {
                        return;
                    }
                    if ("orderBy".equals(key) || "limit".equals(key)) {
                        return;
                    }
                    boolean isNot = key.startsWith("!");
                    if (isNot) {
                        key = key.replace("!", "");
                    }
                    key = StringUtil.camel2Underline(key);

                    if (StringUtil.isNotNull(value) && value instanceof String) {
                        String valueStr = StringUtil.valueOf(value);
//                        if ("id".equalsIgnoreCase(key)) {
//                            valueStr = valueStr.replaceAll("\\*", "");
//                        }

                        if (valueStr.startsWith("!=")) {
                            valueStr = valueStr.substring(2);
                            queryWrapper.ne(key,valueStr);
                        } else if (valueStr.startsWith(">=")) {
                            valueStr = valueStr.substring(2);
                            queryWrapper.ge(key,valueStr);
                        } else if (valueStr.startsWith(">")) {
                            valueStr = valueStr.substring(1);
                            queryWrapper.gt(key,valueStr);
                        } else if (valueStr.startsWith("<=")) {
                            valueStr = valueStr.substring(2);
                            queryWrapper.le(key,valueStr);
                        } else if (valueStr.startsWith("<")) {
                            valueStr = valueStr.substring(1);
                            queryWrapper.lt(key,valueStr);
                        } else if (valueStr.indexOf("~") >= 0) {
                            String[] valueArray = valueStr.split("~", 2);
                            String leftValue = valueArray[0];
                            String rightValue = valueArray[1];
                            if (StringUtil.isNotNull(leftValue) && StringUtil.isNotNull(rightValue)) {
                                queryWrapper.between(key, leftValue, rightValue);
                            } else if (StringUtil.isNotNull(leftValue)) {
                                queryWrapper.ge(key, leftValue);
                            } else if (StringUtil.isNotNull(rightValue)) {
                                queryWrapper.le(key, rightValue);
                            }
                        } else if ("is null".equalsIgnoreCase(valueStr)) {
                            if (isNot) {
                                queryWrapper.isNotNull(key);
                            } else {
                                queryWrapper.isNull(key);
                            }
                        } else if ("is not null".equalsIgnoreCase(valueStr)) {
                            if (isNot) {
                                queryWrapper.isNull(key);
                            } else {
                                queryWrapper.isNotNull(key);
                            }
                        } else {
                            if (valueStr.indexOf(",") >= 0 && !valueStr.endsWith("*")) {
                                valueStr = valueStr.replaceAll("^,|,$", "");
                                if (StringUtil.isNull(valueStr)) {
                                    return;
                                }
                                if (valueStr.indexOf(",") > -1) {
                                    if (isNot) {
                                        queryWrapper.notIn(key, Arrays.asList(valueStr.split(",")));
                                    } else {
                                        queryWrapper.in(key, Arrays.asList(valueStr.split(",")));
                                    }
                                } else {
                                    if (isNot) {
                                        queryWrapper.ne(key,value);
                                    } else {
                                        queryWrapper.eq(key,value);
                                    }
                                }
                            } else {
                                if (valueStr.startsWith("*") || valueStr.endsWith("*")) {
                                    String tempValue = valueStr.replaceAll("^\\*|\\*$", "");
                                    if (StringUtil.isNull(tempValue)) {
                                        return;
                                    }
                                    valueStr = valueStr.replaceAll("§", ",");
                                    queryWrapper.like(key,tempValue);
                                } else {
                                    queryWrapper.eq(key,value);
                                }
                            }
                        }
                    } else if (value instanceof Collection) {
                        if (isNot) {
                            queryWrapper.notIn(key,(Collection) value);
                        } else {
                            queryWrapper.in(key,(Collection) value);
                        }
                    } else {
                        if (isNot) {
                            queryWrapper.ne(key, value);
                        } else {
                            queryWrapper.eq(key, value);
                        }
                    }
                });
        queryWrapper.orderByDesc("CREATE_DATETIME");
        return queryWrapper;
    }

    default Wrapper<T> getListWrapper(Object object){
        Map<String, Object> param = objectToMap(object);
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
//        if (param == null || param.isEmpty()) {
//            return null;
//        }
        param.forEach(
                (key, value) -> {
                    if (StringUtil.isNull(value)) {
                        return;
                    }
                    if ("orderBy".equals(key)) {
                        String valueStr = StringUtil.valueOf(value);
                        if (valueStr.startsWith("-")) {
                            valueStr = valueStr.replace("-", "");
                            if (StringUtil.isNull(valueStr)) {
                                return;
                            }
                            queryWrapper.orderByDesc(StringUtil.camel2Underline(valueStr));
                        } else {
                            valueStr = valueStr.replace("+", "");
                            if (StringUtil.isNull(valueStr)) {
                                return;
                            }
                            queryWrapper.orderByAsc(StringUtil.camel2Underline(valueStr));
                        }
                    }
                    if ("orderBy".equals(key) || "limit".equals(key)) {
                        return;
                    }
                    boolean isNot = key.startsWith("!");
                    if (isNot) {
                        key = key.replace("!", "");
                    }
                    key = StringUtil.camel2Underline(key);

                    if (StringUtil.isNotNull(value) && value instanceof String) {
                        String valueStr = StringUtil.valueOf(value);
//                        if ("id".equalsIgnoreCase(key)) {
//                            valueStr = valueStr.replaceAll("\\*", "");
//                        }

                        if (valueStr.startsWith("!=")) {
                            valueStr = valueStr.substring(2);
                            queryWrapper.ne(key,valueStr);
                        } else if (valueStr.startsWith(">=")) {
                            valueStr = valueStr.substring(2);
                            queryWrapper.ge(key,valueStr);
                        } else if (valueStr.startsWith(">")) {
                            valueStr = valueStr.substring(1);
                            queryWrapper.gt(key,valueStr);
                        } else if (valueStr.startsWith("<=")) {
                            valueStr = valueStr.substring(2);
                            queryWrapper.le(key,valueStr);
                        } else if (valueStr.startsWith("<")) {
                            valueStr = valueStr.substring(1);
                            queryWrapper.lt(key,valueStr);
                        } else if (valueStr.indexOf("~") >= 0) {
                            String[] valueArray = valueStr.split("~", 2);
                            String leftValue = valueArray[0];
                            String rightValue = valueArray[1];
                            if (StringUtil.isNotNull(leftValue) && StringUtil.isNotNull(rightValue)) {
                                queryWrapper.between(key, leftValue, rightValue);
                            } else if (StringUtil.isNotNull(leftValue)) {
                                queryWrapper.ge(key, leftValue);
                            } else if (StringUtil.isNotNull(rightValue)) {
                                queryWrapper.le(key, rightValue);
                            }
                        } else if ("is null".equalsIgnoreCase(valueStr)) {
                            if (isNot) {
                                queryWrapper.isNotNull(key);
                            } else {
                                queryWrapper.isNull(key);
                            }
                        } else if ("is not null".equalsIgnoreCase(valueStr)) {
                            if (isNot) {
                                queryWrapper.isNull(key);
                            } else {
                                queryWrapper.isNotNull(key);
                            }
                        } else {
                            if (valueStr.indexOf(",") >= 0 && !valueStr.endsWith("*")) {
                                valueStr = valueStr.replaceAll("^,|,$", "");
                                if (StringUtil.isNull(valueStr)) {
                                    return;
                                }
                                if (valueStr.indexOf(",") > -1) {
                                    if (isNot) {
                                        queryWrapper.notIn(key, Arrays.asList(valueStr.split(",")));
                                    } else {
                                        queryWrapper.in(key, Arrays.asList(valueStr.split(",")));
                                    }
                                } else {
                                    if (isNot) {
                                        queryWrapper.ne(key,value);
                                    } else {
                                        queryWrapper.eq(key,value);
                                    }
                                }
                            } else {
                                if (valueStr.startsWith("*") || valueStr.endsWith("*")) {
                                    String tempValue = valueStr.replaceAll("^\\*|\\*$", "");
                                    if (StringUtil.isNull(tempValue)) {
                                        return;
                                    }
                                    valueStr = valueStr.replaceAll("§", ",");
                                    queryWrapper.like(key,tempValue);
                                } else {
                                    queryWrapper.eq(key,value);
                                }
                            }
                        }
                    } else if (value instanceof Collection) {
                        if (isNot) {
                            queryWrapper.notIn(key,(Collection) value);
                        } else {
                            queryWrapper.in(key,(Collection) value);
                        }
                    } else {
                        if (isNot) {
                            queryWrapper.ne(key, value);
                        } else {
                            queryWrapper.eq(key, value);
                        }
                    }
                });
        queryWrapper.orderByDesc("CREATE_DATETIME");
        return queryWrapper;
    }

    public static Map<String, Object> objectToMap(Object object){
        Map<String,Object> dataMap = new HashMap<>();
        Class<?> clazz = object.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            try {
                field.setAccessible(true);
                if(null==field.get(object)){
                    continue;
                }
                dataMap.put(field.getName(),field.get(object));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return dataMap;
    }

    /**
     * 响应请求分页数据
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static TableDataInfo getDataTable(List<?> list)
    {
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(HttpStatus.SUCCESS);
        rspData.setMsg("查询成功");
        rspData.setRows(list);
        rspData.setTotal(new PageInfo(list).getTotal());
        return rspData;
    }
}
