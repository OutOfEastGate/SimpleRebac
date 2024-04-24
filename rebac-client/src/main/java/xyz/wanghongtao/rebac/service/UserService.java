package xyz.wanghongtao.rebac.service;

import xyz.wanghongtao.rebac.object.dataObject.UserDo;
import xyz.wanghongtao.rebac.object.form.user.RegisterForm;

/**
 * @author wanghongtao <wanghongtao05@kuaishou.com>
 * Created on 2024-04-24
 */
public interface UserService {
  UserDo getUserByUsername(String username);

    void saveUser(RegisterForm registerForm);
}
