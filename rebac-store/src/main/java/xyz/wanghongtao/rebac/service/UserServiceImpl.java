package xyz.wanghongtao.rebac.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.zaxxer.hikari.HikariDataSource;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import xyz.wanghongtao.rebac.object.dataObject.UserDo;
import xyz.wanghongtao.rebac.object.form.user.RegisterForm;
import xyz.wanghongtao.rebac.repository.UserMapper;
import xyz.wanghongtao.rebac.service.mockService.UserServiceMockImpl;

import javax.sql.DataSource;

/**
 * @author wanghongtao <wanghongtao05@kuaishou.com>
 * Created on 2024-04-24
 */
@ConditionalOnProperty(name = "wht.back.mockDatabase", havingValue = "false", matchIfMissing = true)
@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {
  private final UserMapper userMapper;


  @Override
  public UserDo getUserByUsername(String username) {
    LambdaQueryWrapper<UserDo> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(UserDo::getUsername, username);
    return userMapper.selectOne(queryWrapper);
  }

  @Override
  public void saveUser(RegisterForm registerForm) {
    UserDo userDo =
      new UserDo();
    userDo.setUsername(registerForm.getUsername());
    userDo.setPassword(registerForm.getPassword());
    userMapper.insert(userDo);
  }
}
