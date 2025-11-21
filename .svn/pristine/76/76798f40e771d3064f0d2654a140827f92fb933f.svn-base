package com.dincher.common.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.List;

/**
 * @author wangbin
 * @version V2.0
 * @className XmlUtil
 * @description
 * @date 2023/11/09 22:36
 **/

/**
 * xml数据转成json
 *
 * @author 。
 */
public class XmlUtil {

    /**
     * 将所有xml数据转成json
     *
     * @param outputXml 要解析的xml数据
     * @return
     * @throws Exception
     */
    public static JSONObject xmlToJson(String outputXml) throws Exception {
        Document document = DocumentHelper.parseText(outputXml);
        Element root = document.getRootElement();
        // 遍历所有子节点
        return elementJson(root);
    }


    /**
     * xml节点转成JsonObject
     *
     * @param node
     * @return
     */
    public static JSONObject elementJson(Element node) {
        JSONObject result = new JSONObject();
        List<Attribute> listAttr = node.attributes();
        for (Attribute attr : listAttr) {
            result.put(attr.getName(), attr.getValue());
        }
        List<Element> listElement = node.elements();
        if (!listElement.isEmpty()) {
            for (Element e : listElement) {
                if (e.attributes().isEmpty() && e.elements().isEmpty()) {
                    result.put(e.getName(), e.getTextTrim());
                } else {
                    if (!result.containsKey(e.getName())) {
                        result.put(e.getName(), new JSONArray());
                    }
                    ((JSONArray) result.get(e.getName())).add(elementJson(e));
                }
            }
        }
        return result;
    }
}
