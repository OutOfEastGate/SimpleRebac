package xyz.wanghongtao.rebac.object.runtime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author wanghongtao <wanghongtao05@kuaishou.com>
 * Created on 2024-04-24
 */
@Component
public class LoginInterceptorRuntime {
  private final RedisTemplate<String, Object> redisTemplate;

  @Value("${wht.back.mockDatabase}")
  private Boolean mockDatabase;

  public LoginInterceptorRuntime(RedisTemplate<String, Object> redisTemplate) {
    this.redisTemplate = redisTemplate;
  }

  public String getTokenByUsername(String usernameByToken) {
    if (mockDatabase) {
      return (String) UserLoginRuntime.cache.get(usernameByToken);
    }
    return (String) redisTemplate.opsForValue().get(usernameByToken);
  }
}
