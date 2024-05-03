package xyz.wanghongtao.rebac.runtime;

import lombok.Data;
import org.springframework.stereotype.Component;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.NetworkParams;
import oshi.software.os.OperatingSystem;
import xyz.wanghongtao.rebac.object.dataObject.CheckPermissionRecordDo;
import xyz.wanghongtao.rebac.object.viewObject.system.SystemInfoDto;
import xyz.wanghongtao.rebac.service.gateway.DatabaseLogGateway;

import java.text.DecimalFormat;
import java.util.List;

/**
 * @author wanghongtao
 * @data 2024/4/29 17:11
 */
@Data
@Component
public class SystemServiceRuntime {
  private final SystemInfo systemInfo = new SystemInfo();

  private final DatabaseLogGateway databaseLogGateway;

  public SystemInfoDto getSystemRunInfo() {
    SystemInfoDto systemInfoDto = new SystemInfoDto();
    HardwareAbstractionLayer hardware = systemInfo.getHardware();
    OperatingSystem operatingSystem = systemInfo.getOperatingSystem();
    GlobalMemory memory = hardware.getMemory();
    CentralProcessor processor = hardware.getProcessor();
    // CPU信息
    NetworkParams networkParams = operatingSystem.getNetworkParams();
    String ipv4DefaultGateway = networkParams.getIpv4DefaultGateway();
    long systemUptime = operatingSystem.getSystemUptime();
    long h = systemUptime/3600;
    long m = (systemUptime%3600)/60;
    long s = (systemUptime%3600)%60;

    systemInfoDto.setSystemRuntime( h+"小时"+m+"分"+s+"秒");
    systemInfoDto.setCpuNum(processor.getPhysicalProcessorCount());
    systemInfoDto.setCpuThreadNum(processor.getLogicalProcessorCount());
    long totalByte = memory.getTotal();
    long availableByte = memory.getAvailable();
    systemInfoDto.setTotalMemory(formatByte(totalByte));
    DecimalFormat df = new DecimalFormat("#.00");
    systemInfoDto.setMemoryUsage(Double.parseDouble(df.format((totalByte - availableByte) * 1.0 / totalByte)));

    //jvm参数
    Runtime runtime = Runtime.getRuntime();
    long jvmTotalMemoryByte = runtime.totalMemory();
    long freeMemoryByte = runtime.freeMemory();

    systemInfoDto.setJvmMemoryUsage(Double.parseDouble(df.format((jvmTotalMemoryByte - freeMemoryByte) * 1.0 / jvmTotalMemoryByte)));

    return systemInfoDto;
  }

  public SystemInfoDto getPermissionRunInfo(SystemInfoDto systemInfoDto) {
    List<CheckPermissionRecordDo> checkPermissionLogList = databaseLogGateway.getCheckPermissionLogList();
    List<CheckPermissionRecordDo> top20Log = databaseLogGateway.getTop20CheckPermissionLogList();
    systemInfoDto.setLogList(top20Log);
    //统计鉴权次数
    Integer times = checkPermissionLogList.size();

    Long successTimes = checkPermissionLogList.stream().filter(CheckPermissionRecordDo::getHasPermission).count();
    Long failTimes = times - successTimes;

    SystemInfoDto.PermissionInfo permissionInfo = SystemInfoDto.PermissionInfo.builder()
      .times(times)
      .successTimes(successTimes)
      .failTimes(failTimes)
      .build();
    systemInfoDto.setPermissionInfo(permissionInfo);
    return systemInfoDto;
  }

  private static String formatByte(long byteNumber) {
    //换算单位
    double FORMAT = 1024.0;
    double kbNumber = byteNumber / FORMAT;
    if (kbNumber < FORMAT) {
      return new DecimalFormat("#.##KB").format(kbNumber);
    }
    double mbNumber = kbNumber / FORMAT;
    if (mbNumber < FORMAT) {
      return new DecimalFormat("#.##MB").format(mbNumber);
    }
    double gbNumber = mbNumber / FORMAT;
    if (gbNumber < FORMAT) {
      return new DecimalFormat("#.##GB").format(gbNumber);
    }
    double tbNumber = gbNumber / FORMAT;
    return new DecimalFormat("#.##TB").format(tbNumber);
  }


}
