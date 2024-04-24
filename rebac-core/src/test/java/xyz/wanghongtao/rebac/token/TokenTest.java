package xyz.wanghongtao.rebac.token;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import xyz.wanghongtao.rebac.AbstractTest;
import xyz.wanghongtao.rebac.util.JwtUtil;

/**
 * @author wanghongtao <wanghongtao05@kuaishou.com>
 * Created on 2024-04-24
 */
@Slf4j
public class TokenTest extends AbstractTest {
  @Test
  public void testToken() {
    String usernameByToken = JwtUtil.getUsernameByToken("eyJhbGciOiJIUzM4NCJ9.eyJ1c2VybmFtZSI6IndodC0xIiwiaWQiOjIsImlhdCI6MTcxMzk0OTU2NCwiZXhwIjoxNzE0MDM1OTY0fQ.WLkDKAf3oYYavuQ1eWH5yT18mt3xvAmG01Wz8fYIpe5GGhzj0G3o_-1wUlPMhK27");
    log.info(usernameByToken);
  }
}
