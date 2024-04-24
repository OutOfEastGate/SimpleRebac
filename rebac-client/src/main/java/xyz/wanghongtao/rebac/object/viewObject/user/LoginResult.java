package xyz.wanghongtao.rebac.object.viewObject.user;

import lombok.Builder;
import lombok.Data;

/**
 * @author wanghongtao <wanghongtao05@kuaishou.com>
 * Created on 2024-04-24
 */
@Builder
@Data
public class LoginResult {
  String token;
}
