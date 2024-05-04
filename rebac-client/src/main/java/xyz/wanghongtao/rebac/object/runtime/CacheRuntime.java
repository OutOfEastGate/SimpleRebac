package xyz.wanghongtao.rebac.object.runtime;

import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import xyz.wanghongtao.rebac.object.config.RuntimeConfigParam;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wanghongtao
 * @data 2024/5/3 16:46
 */
@AllArgsConstructor
@Component
public class CacheRuntime {
  private final RedisTemplate<String, Object> redisTemplate;
  private final RuntimeConfigParam configParam;

  public static Map<String, Object> cache = new HashMap<>();

  public void saveCache(String key, Object value) {
    if (configParam.getMockDatabase()) {
      cache.put(key, value);
      return;
    }
    try {
      redisTemplate.opsForValue().set(key, value);
    } catch (Exception e) {
      cache.put(key, value);
    }
  }

  public Object getCache(String key) {
    if (configParam.getMockDatabase()) {
      return cache.get(key);
    }

    try {
      return redisTemplate.opsForValue().get(key);
    } catch (Exception e) {
      return cache.get(key);
    }

  }

  public void deleteCache(String key) {
    if (configParam.getMockDatabase()) {
      cache.remove(key);
      return;
    }
     try {
       redisTemplate.delete(key);
     } catch (Exception e) {
       cache.remove(key);
     }
  }
}
