package xyz.wanghongtao.rebac.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.wanghongtao.rebac.object.viewObject.Result;
import xyz.wanghongtao.rebac.object.viewObject.system.SystemInfoDto;
import xyz.wanghongtao.rebac.service.SystemService;

/**
 * @author wanghongtao
 * @data 2024/3/25 10:19
 */
@AllArgsConstructor
@RestController
@RequestMapping("/system")
public class SystemController {
  private final SystemService systemService;

  @GetMapping("/getSystemInfo")
  public Result<SystemInfoDto> getSystemInfo() {
    return Result.success(systemService.getSystemInfo());
  }
}
