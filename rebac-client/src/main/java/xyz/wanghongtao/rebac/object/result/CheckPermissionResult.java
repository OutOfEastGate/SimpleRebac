package xyz.wanghongtao.rebac.object.result;

import lombok.Builder;
import lombok.Data;

/**
 * @author wanghongtao
 * @data 2024/4/28 14:50
 */
@Builder
@Data
public class CheckPermissionResult {
  Boolean hasPermission;

  Double useTime;
}
