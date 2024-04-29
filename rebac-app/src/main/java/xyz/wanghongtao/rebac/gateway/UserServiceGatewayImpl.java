package xyz.wanghongtao.rebac.gateway;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import xyz.wanghongtao.rebac.engine.user.UserLoginEngine;
import xyz.wanghongtao.rebac.object.form.user.LoginForm;
import xyz.wanghongtao.rebac.object.form.user.RegisterForm;
import xyz.wanghongtao.rebac.object.runtime.UserLoginRuntime;
import xyz.wanghongtao.rebac.object.viewObject.user.LoginResult;
import xyz.wanghongtao.rebac.object.viewObject.user.RegisterResult;
import xyz.wanghongtao.rebac.service.gateway.UserServiceGateway;

/**
 * @author wanghongtao
 * @data 2024/4/29 16:54
 */
@AllArgsConstructor
@Component
public class UserServiceGatewayImpl implements UserServiceGateway {
  private UserLoginRuntime userLoginRuntime;

  public LoginResult login(LoginForm loginForm) {
    return UserLoginEngine.login(userLoginRuntime, loginForm);
  }

  public RegisterResult register(RegisterForm registerForm) {
    return UserLoginEngine.register(userLoginRuntime, registerForm);
  }
}
