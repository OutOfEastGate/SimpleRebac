package xyz.wanghongtao.rebac.object.runtime;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author wanghongtao <wanghongtao05@kuaishou.com>
 * Created on 2024-04-24
 */
@Component
public class LoginInterceptorRuntime {
  private final CacheRuntime cacheRuntime;


  public LoginInterceptorRuntime( CacheRuntime cacheRuntime) {
    this.cacheRuntime = cacheRuntime;
  }

  public String getTokenByUsername(String usernameByToken) {
    return (String) cacheRuntime.getCache(usernameByToken);
  }
}
