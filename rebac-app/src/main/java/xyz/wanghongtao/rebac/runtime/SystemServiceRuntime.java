package xyz.wanghongtao.rebac.runtime;

import lombok.Data;
import org.springframework.stereotype.Component;
import oshi.SystemInfo;

/**
 * @author wanghongtao
 * @data 2024/4/29 17:11
 */
@Data
@Component
public class SystemServiceRuntime {
  private final SystemInfo systemInfo = new SystemInfo();
}
