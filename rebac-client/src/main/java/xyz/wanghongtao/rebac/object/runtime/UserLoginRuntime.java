package xyz.wanghongtao.rebac.object.runtime;


import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import xyz.wanghongtao.rebac.object.dataObject.UserDo;
import xyz.wanghongtao.rebac.object.form.user.RegisterForm;
import xyz.wanghongtao.rebac.service.UserService;

/**
 * @author wanghongtao <wanghongtao05@kuaishou.com>
 * Created on 2024-04-24
 */
@Data
@Component
public class UserLoginRuntime {
  @Value("wht.back.salt")
  private String salt;

  private final UserService userService;

  private final RedisTemplate<String, Object> redisTemplate;

  public UserLoginRuntime(UserService userService, RedisTemplate<String, Object> redisTemplate) {
    this.userService = userService;
    this.redisTemplate = redisTemplate;
  }

  public UserDo getUserByUsername(String username) {
    return userService.getUserByUsername(username);
  }

  public void saveUser(RegisterForm registerForm) {
    userService.saveUser(registerForm);
  }

  public void saveToken(String username, String token) {
    redisTemplate.opsForValue().set(username, token);
  }
}
