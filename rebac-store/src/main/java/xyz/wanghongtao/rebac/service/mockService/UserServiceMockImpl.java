package xyz.wanghongtao.rebac.service.mockService;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import xyz.wanghongtao.rebac.object.dataObject.UserDo;
import xyz.wanghongtao.rebac.object.form.user.RegisterForm;
import xyz.wanghongtao.rebac.service.UserService;
import xyz.wanghongtao.rebac.util.JacksonUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wanghongtao <wanghongtao05@kuaishou.com>
 * Created on 2024-04-24
 */
@ConditionalOnProperty(name = "wht.back.mockDatabase", havingValue = "true", matchIfMissing = true)
@Service
public class UserServiceMockImpl extends AbstractMockService implements UserService {
  public static Map<String,UserDo> userMap = new HashMap<>();
  private final String mockDataUrl = "/mockDatabase/mockUserDatabase.json";

  {
    userMap = JacksonUtils.fromJsonStr(readFileToString(mockDataUrl), new TypeReference<>() {});
  }
  private void updateDatabase() {
    writeStringToFile(JacksonUtils.toJson(userMap), mockDataUrl);
  }
  @Override
  public UserDo getUserByUsername(String username) {
    return userMap.get(username);
  }

  @Override
  public void saveUser(RegisterForm registerForm) {
    userMap.put(registerForm.getUsername(), UserDo.builder()
      .username(registerForm.getUsername())
      .password(registerForm.getPassword()).build());
    updateDatabase();
  }

}
