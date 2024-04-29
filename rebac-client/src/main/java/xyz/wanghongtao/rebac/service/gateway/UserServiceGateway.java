package xyz.wanghongtao.rebac.service.gateway;

import xyz.wanghongtao.rebac.object.form.user.LoginForm;
import xyz.wanghongtao.rebac.object.form.user.RegisterForm;
import xyz.wanghongtao.rebac.object.viewObject.user.LoginResult;
import xyz.wanghongtao.rebac.object.viewObject.user.RegisterResult;

public interface UserServiceGateway {
  LoginResult login(LoginForm loginForm);

  RegisterResult register(RegisterForm registerForm);
}
