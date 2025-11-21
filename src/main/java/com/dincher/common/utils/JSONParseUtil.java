package com.dincher.common.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.IdentityHashMap;
import java.util.Map;

/**
 * @author yangshuai
 * @date 2023-3-9 0009 11:22
 */
public class JSONParseUtil {
    public static Map<String, Object> getKeyValue(String json) {
        Map<String, Object> result = new IdentityHashMap<>();
        JSONObject jsonObject = JSONObject.parseObject(json);
        JSONObject jsonObjectBody = (JSONObject) jsonObject.get("body");
        parseLoop(jsonObjectBody, "", result);
        return result;
    }

    public static Map<String, Object> getKeyValue(JSONObject jsonObject) {
        Map<String, Object> result = new IdentityHashMap<>();
        parseLoop(jsonObject, "", result);
        return result;
    }

    private static void parseLoop(JSONObject jsonObject, String headKey, Map<String, Object> map) {

        for (String key : jsonObject.keySet()) {
            Object value = jsonObject.get(key);

            if (value instanceof String || value instanceof Number) {
                map.put(montageStr(headKey, key), value);
                continue;
            }
            if (value instanceof JSONObject) {
                JSONObject object = (JSONObject) value;
                String tmpKey = montageStr(headKey, key);
                map.put(tmpKey, "JSONObject");
                parseLoop(object, tmpKey, map);
                continue;
            }
            if (value instanceof JSONArray) {
                String tmpKey = montageStr(headKey, key);
                map.put(tmpKey, "JSONArray");
                JSONArray jsonArray = (JSONArray) value;
                if (!jsonArray.isEmpty()) {
                    for (int i = 0; i < jsonArray.size(); i++) {
                        JSONObject o1 = jsonArray.getJSONObject(i);
                        parseLoop(o1, tmpKey, map);
                    }
                }
            }
        }
    }

    public static String montageStr(String str, String old) {
        if (StringUtils.isEmpty(str)) {
            return old;
        }
        return str.concat(":").concat(old);
    }
}
