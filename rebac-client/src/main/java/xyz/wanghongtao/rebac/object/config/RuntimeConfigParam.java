package xyz.wanghongtao.rebac.object.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author wanghongtao <wanghongtao05@kuaishou.com>
 * Created on 2024-05-03
 */
@Component
@ConfigurationProperties(prefix = "wht.back")
@Data
public class RuntimeConfigParam {
  Boolean startGraphCompute;

  Boolean mockDatabase;

  String domain;

  String qq;

  String endpoint;

  String virtualMachine;

  String wifiMachine;

  String salt;
}
