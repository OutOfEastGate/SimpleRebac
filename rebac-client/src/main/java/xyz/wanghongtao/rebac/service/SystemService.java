package xyz.wanghongtao.rebac.service;

import xyz.wanghongtao.rebac.object.viewObject.system.SystemInfoDto;

@FunctionalInterface
public interface SystemService {
  SystemInfoDto getSystemInfo();
}
