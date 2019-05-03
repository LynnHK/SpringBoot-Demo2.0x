package com.example.demo.utils;

import java.util.List;
import java.util.Map;

import org.thymeleaf.util.StringUtils;

import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * 1. ObjectMapper是JSON操作的核心，Jackson的所有JSON操作都是在ObjectMapper中实现。<br>
 * 2. ObjectMapper有多个JSON序列化的方法，可以把JSON字符串保存File、OutputStream等不同的介质中。<br>
 * 3. writeValue(File arg0, Object arg1)把arg1转成json序列，并保存到arg0文件中。<br>
 * 4. writeValue(OutputStream arg0, Object arg1)把arg1转成json序列，并保存到arg0输出流中。<br>
 * 5. writeValueAsBytes(Object arg0)把arg0转成json序列，并把结果输出成字节数组。<br>
 * 6. writeValueAsString(Object arg0)把arg0转成json序列，并把结果输出成字符串。
 */
public class JSONUtil {
	
    /**
     * 初始化变量
     */
    private static ObjectMapper mapper = new ObjectMapper();
    
    static {
        // 解决实体未包含字段反序列化时抛出异常
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        
        // 对于空的对象转json的时候不抛出错误
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        
        // 允许属性名称没有引号
        mapper.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        
        // 允许单引号
        mapper.configure(Feature.ALLOW_SINGLE_QUOTES, true);
    }
    
    /**
     * 
     * <b>Title:</b> toJSONString<br>
     * <b>Description:</b> 将一个object转换为json, 可以是一个java对象，也可以是集合<br>
     * 
     * @param obj
     *            - 传入的数据
     * @return JSONString
     */
    public static String toJSONString(Object obj) {
        String json = null;
        if (obj == null) return json;
        
        try {
            json = mapper.writeValueAsString(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }
    
    /**
     * <b>Title:</b> parseObject<br>
     * <b>Description:</b> 将json结果集转化为对象<br>
     * 
     * @param json
     *            - json数据
     * @param beanType
     *            - 转换的实体类型
     * @return T
     */
    public static <T> T parseObject(String json, Class<T> beanType) {
        T t = null;
        if (StringUtils.isEmpty(json)) return t;
        
        try {
            t = mapper.readValue(json, beanType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }
    
    /**
     * <b>Title:</b> parseMap<br>
     * <b>Description:</b> 将json数据转换成Map<br>
     * 
     * @param json
     *            - 转换的数据
     * @return
     */
    public static Map<String, Object> parseMap(String json) {
        Map<String, Object> map = null;
        if (StringUtils.isEmpty(json)) return map; 
        
        try {
            map = mapper.readValue(json, new TypeReference<Map<String, Object>>() {});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
    
    /**
     * 
     * <b>Title:</b> parseList<br>
     * <b>Description:</b> 将json数据转换成list <br>
     * 
     * @param json
     *            - 转换的数据
     * @return
     */
    public static <T> List<T> parseList(String json, Class<T> beanType) {
        List<T> list = null;
        if (StringUtils.isEmpty(json)) return list; 
        
        try {
            JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, beanType);
            list = mapper.readValue(json, javaType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    /**
     * 
     * <b>Title:</b> findValue<br>
     * <b>Description:</b> 获取json对象数据的属性<br>
     * 
     * @param resData
     *            - 请求的数据
     * @param resPro
     *            - 请求的属性
     * @return 返回String类型数据
     */
    public static String findValue(String resData, String resPro) {
        String result = null;
        if (StringUtils.isEmpty(resData)) return result;
        
        try {
            JsonNode node = mapper.readTree(resData);
            JsonNode resProNode = node.get(resPro);
            result = JSONUtil.toJSONString(resProNode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
