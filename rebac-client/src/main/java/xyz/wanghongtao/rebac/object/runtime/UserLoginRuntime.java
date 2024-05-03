package xyz.wanghongtao.rebac.object.runtime;


import lombok.Data;
import org.springframework.stereotype.Component;
import xyz.wanghongtao.rebac.object.config.RuntimeConfigParam;
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

  private final RuntimeConfigParam configParam;

  private final UserService userService;

  private final CacheRuntime cacheRuntime;

  public UserLoginRuntime(RuntimeConfigParam configParam, UserService userService, CacheRuntime cacheRuntime) {
    this.configParam = configParam;
    this.userService = userService;
    this.cacheRuntime = cacheRuntime;
  }

  public UserDo getUserByUsername(String username) {
    return userService.getUserByUsername(username);
  }

  public void saveUser(RegisterForm registerForm) {
    userService.saveUser(registerForm);
  }

  public void saveToken(String username, String token) {
    cacheRuntime.saveCache(username, token);
  }

  public String getSalt() {
    return configParam.getSalt();
  }
}
