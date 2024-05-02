package xyz.wanghongtao.rebac.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import oshi.software.os.NetworkParams;
import xyz.wanghongtao.rebac.object.viewObject.system.SystemInfoDto;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.OperatingSystem;
import xyz.wanghongtao.rebac.runtime.SystemServiceRuntime;

import java.text.DecimalFormat;

/**
 * @author wanghongtao
 * @date 2024/3/25 9:56
 */
@AllArgsConstructor
@Service
public class SystemServiceImpl implements SystemService {

  private final SystemServiceRuntime systemServiceRuntime;

  @Override
  public SystemInfoDto getSystemInfo() {


    SystemInfoDto systemInfoDto = systemServiceRuntime.getSystemRunInfo();
    return systemInfoDto;
  }


}
