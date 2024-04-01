package xyz.wanghongtao.rebac.util;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

/**
 * @author wanghongtao
 * @data 2024/3/28 17:48
 */
public class JacksonUtils {
  private static final ObjectMapper objectMapper = new ObjectMapper();

  public static ObjectMapper getObjectMapper() {
    return objectMapper;
  }

  public static <T> String toJson(List<T> tList) {
    if(CollectionUtils.isEmpty(tList)) {
      return null;
    }
    ObjectMapper objectMapper = getObjectMapper();
    try {
      return objectMapper.writeValueAsString(tList);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }
  public static <T> String toJson(Object obj) {
    if(Objects.isNull(obj)) {
      return null;
    }
    ObjectMapper objectMapper = getObjectMapper();
    try {
      objectMapper.writeValueAsString(obj);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
    throw new RuntimeException("Json序列化失败");
  }

  public static <T> T fromJsonStr(String json, Class<T> tClass) {
    if (StringUtils.isEmpty(json)) {
      return null;
    }

    ObjectMapper objectMapper = getObjectMapper();
    try {
      return objectMapper.readValue(json, tClass);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  public static <T> T fromJsonStr(String json, TypeReference<T> tClass) {
    if (StringUtils.isEmpty(json)) {
      return null;
    }

    ObjectMapper objectMapper = getObjectMapper();
    try {
      return objectMapper.readValue(json, tClass);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }
}
