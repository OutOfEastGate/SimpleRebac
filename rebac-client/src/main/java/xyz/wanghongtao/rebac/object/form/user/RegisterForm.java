package xyz.wanghongtao.rebac.object.form.user;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * @author wanghongtao <wanghongtao05@kuaishou.com>
 * Created on 2024-04-24
 */
@Data
public class RegisterForm {
  @NotEmpty(message = "用户名不可为空")
  String username;
  @NotEmpty(message = "密码不可为空")
  String password;

  Boolean remember;
}
