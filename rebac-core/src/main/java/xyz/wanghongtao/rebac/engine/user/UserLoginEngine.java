package xyz.wanghongtao.rebac.engine.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.DigestUtils;
import xyz.wanghongtao.rebac.exception.CustomException;
import xyz.wanghongtao.rebac.exception.ErrorCode;
import xyz.wanghongtao.rebac.object.dataObject.UserDo;
import xyz.wanghongtao.rebac.object.form.user.LoginForm;
import xyz.wanghongtao.rebac.object.form.user.RegisterForm;
import xyz.wanghongtao.rebac.object.runtime.UserLoginRuntime;
import xyz.wanghongtao.rebac.object.viewObject.user.LoginResult;
import xyz.wanghongtao.rebac.object.viewObject.user.RegisterResult;
import xyz.wanghongtao.rebac.util.JwtUtil;

import java.nio.charset.StandardCharsets;

/**
 * @author wanghongtao <wanghongtao05@kuaishou.com>
 * Created on 2024-04-24
 */
@Slf4j
public class UserLoginEngine {
  public static LoginResult login(UserLoginRuntime userLoginRuntime, LoginForm loginForm) {
    UserDo user = userLoginRuntime.getUserByUsername(loginForm.getUsername());
    if(user == null) {
      throw new CustomException(ErrorCode.USER_NOT_EXISTED);
    }
    String digest = DigestUtils.md5DigestAsHex((loginForm.getPassword() + userLoginRuntime.getSalt()).getBytes(StandardCharsets.UTF_8));
    if(!user.getPassword().equals(digest)) {
      throw new CustomException(ErrorCode.PASSWORD_ERROR);
    }
    String token = JwtUtil.generateToken(user.getUsername(), user.getId());
    userLoginRuntime.saveToken(user.getUsername(), token);
    return LoginResult.builder()
      .token(token)
      .build();
  }


  public static RegisterResult register(UserLoginRuntime userLoginRuntime, RegisterForm registerForm) {
    UserDo user = userLoginRuntime.getUserByUsername(registerForm.getUsername());
    if(user != null) {
      throw new CustomException(ErrorCode.USER_EXISTED);
    }
    String digest = DigestUtils.md5DigestAsHex((registerForm.getPassword() + userLoginRuntime.getSalt()).getBytes(StandardCharsets.UTF_8));
    registerForm.setPassword(digest);
    userLoginRuntime.saveUser(registerForm);
    LoginResult loginResult = login(userLoginRuntime, LoginForm.builder()
      .username(registerForm.getUsername())
      .password(registerForm.getPassword())
      .build());
    return RegisterResult.builder()
      .username(registerForm.getUsername())
      .token(loginResult.getToken())
      .build();
  }
}
