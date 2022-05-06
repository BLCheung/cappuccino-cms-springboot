package com.blcheung.cappuccino.util;

import com.blcheung.cappuccino.common.exceptions.ServerErrorException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;

public class JSONConverterUtil {

    private static ObjectMapper objectMapper;

    /**
     * 把任意类型的Java实体字段序列化到数据库
     *
     * @param value
     * @param <T>
     * @return
     */
    public static <T> String convertObjectToJSON(T value) {
        if (value == null) return null;
        try {
            return JSONConverterUtil.getObjectMapper()
                                    .writeValueAsString(value);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new ServerErrorException(9999, "序列化出错!");
        }
    }

    /**
     * 把数据库字段反序列化到Java实体字段
     *
     * @param s
     * @param typeReference
     * @param <T>
     * @return
     */
    public static <T> T convertJSONToObject(String s, TypeReference<T> typeReference) {
        if (StringUtils.isEmpty(s)) return null;
        try {
            return JSONConverterUtil.getObjectMapper()
                                    .readValue(s, typeReference);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new ServerErrorException(9999, "反序列化出错!");
        }
    }


    private static ObjectMapper getObjectMapper() {
        if (null == JSONConverterUtil.objectMapper) {
            JSONConverterUtil.objectMapper = new ObjectMapper();
        }
        return JSONConverterUtil.objectMapper;
    }
}
