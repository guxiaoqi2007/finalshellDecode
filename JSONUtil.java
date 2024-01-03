package com.geek.script.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * <p>
 *
 * @author eric.yu 2022/1/29
 * @since 4.3.x1500
 */
public class JSONUtil {
    private static final ObjectMapper MAPPER;

    static {
        MAPPER = new ObjectMapper();
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); // 忽略json和bean的字段数量不匹配
        MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false); //
//		mapper.configure(Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true); //解决json中存在转移字符Illegal unquoted character
    }

    public static ObjectMapper getMapper() {
        return MAPPER;
    }

    /**
     * 将json转成指定类型在java bean
     *
     * @author 刘俊 2015年10月15日
     * @param json	json字串
     * @param clz	java类型
     * @return
     */
    public static <T> T jsonToObj(String json, Class<T> clz) {
        try {
            return MAPPER.readValue(json, clz);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

//		return null;
    }

    /**
     * 将json转成指定类型在java bean(可完成更复杂的类型转换)
     *
     * @author 刘俊 2015年10月15日
     * @param json	json字串
     * @param type	TypeReference转换类型
     * @return
     */
    public static <T> T jsonToObj(String json, TypeReference<T> type) {
        try {
            return MAPPER.readValue(json, type);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

//		return null;
    }

    /**
     * 将json串中指定某个属性转换成java bean
     *
     * @author 刘俊 2015年10月15日
     * @param json		json字串
     * @param propName	属性名
     * @param clz		java类型
     * @return
     */
    public static <T> T jsonToObj(String json, String propName, Class<T> clz) {
        try {
            JsonNode node = MAPPER.readTree(json).path(propName);

            return MAPPER.readValue(node.toString(), clz);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

//		return null;
    }

    /**
     * 将json串中指定某个属性转换成java bean(可完成更复杂的类型转换)
     *
     * @author 刘俊 2015年10月15日
     * @param json		json字串
     * @param propName	属性名
     * @param type		TypeReference转换类型
     * @return
     */
    public static <T> T jsonToObj(String json, String propName, TypeReference<T> type) {
        try {
            JsonNode node = MAPPER.readTree(json).path(propName);

            return MAPPER.readValue(node.toString(), type);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

//		return null;
    }

    /**
     * json转成Map
     */
    public static Map<String, Object> jsonToMap(String json) {
        Map<String, Object> map = null;

        try {
            map = MAPPER.readValue(json, new TypeReference<HashMap<String, Object>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return map;
    }

    /**
     * 将json字串转换成jsonNode
     *
     * @author 刘俊 2015年10月15日
     * @param json	json字串
     * @return
     */
    public static JsonNode jsonToNode(String json) {
        try {
            return MAPPER.readTree(json);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

//		return null;
    }

    /**
     * 将jsonNode转换成java bean
     *
     * @author 刘俊 2015年10月15日
     * @param node	json节点
     * @param clz	java类型
     * @return
     */
    public static <T> T nodeToObj(JsonNode node, Class<T> clz) {
        try {
            return MAPPER.readValue(node.toString(), clz);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

//		return null;
    }

    /**
     * 将jsonNode转换成java bean(可完成更复杂的类型转换)
     *
     * @author 刘俊 2015年10月15日
     * @param node	json节点
     * @param type	TypeReference转换类型
     * @return
     */
    public static <T> T nodeToObj(JsonNode node, TypeReference<T> type) {
        try {
            return MAPPER.readValue(node.toString(), type);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

//		return null;
    }

    /**
     * 将对象转为json字串
     */
    public static String objToJson(Object obj) {
        try {
            return MAPPER.writeValueAsString(obj);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

//		return null;
    }

    public static String toJSONString(Object value) {
        if (value == null) {
            return null;
        }

        try {
            return MAPPER.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T parseObject(String json, Class<T> clz) {
        if (json == null || clz == null) {
            return null;
        }

        try {
            return MAPPER.readValue(json, clz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T parseObject(String json, TypeReference<T> type) {
        if (json == null || type == null) {
            return null;
        }

        try {
            return MAPPER.readValue(json, type);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T parseObject(String json, Class<?> collType, Class<?>... elemTypes) {
        if (json == null || collType == null || elemTypes == null || elemTypes.length == 0) {
            return null;
        }

        try {
            JavaType javaType = MAPPER.getTypeFactory().constructParametricType(collType, elemTypes);
            return MAPPER.readValue(json, javaType);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
