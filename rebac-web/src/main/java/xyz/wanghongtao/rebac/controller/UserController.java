package xyz.wanghongtao.rebac.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.wanghongtao.rebac.object.form.user.LoginForm;
import xyz.wanghongtao.rebac.object.form.user.RegisterForm;
import xyz.wanghongtao.rebac.object.viewObject.Result;
import xyz.wanghongtao.rebac.object.viewObject.user.LoginResult;
import xyz.wanghongtao.rebac.object.viewObject.user.RegisterResult;
import xyz.wanghongtao.rebac.service.gateway.UserServiceGateway;

/**
 * @author wanghongtao <wanghongtao05@kuaishou.com>
 * Created on 2024-04-24
 */
@AllArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {
  private final UserServiceGateway userServiceGateway;
  @PostMapping("/login")
  public Result<LoginResult> login(@RequestBody @Valid LoginForm loginForm) {
    return Result.success(userServiceGateway.login(loginForm));
  }

  @PostMapping("/register")
  public Result<RegisterResult> register(@RequestBody @Valid RegisterForm registerForm) {
    return Result.success(userServiceGateway.register(registerForm));
  }
}
