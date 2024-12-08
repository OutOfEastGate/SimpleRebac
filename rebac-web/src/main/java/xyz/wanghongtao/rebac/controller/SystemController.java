package xyz.wanghongtao.rebac.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.utility.RandomString;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.wanghongtao.rebac.object.viewObject.Result;
import xyz.wanghongtao.rebac.object.viewObject.system.SystemInfoDto;
import xyz.wanghongtao.rebac.service.SystemService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wanghongtao
 * @data 2024/3/25 10:19
 */
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/system")
public class SystemController {
  private final SystemService systemService;

  @GetMapping("/getSystemInfo")
  public Result<SystemInfoDto> getSystemInfo() {
    return Result.success(systemService.getSystemInfo());
  }

  @GetMapping("/test")
  public Result<Object> test() throws InterruptedException {
    List<String> list = new ArrayList<>(100);
    for (int i = 1; i < 100; i++) {
      list.add(RandomString.make(i));
    }
    log.info(Thread.currentThread().getName());
    Thread.sleep(3000L);
    return Result.success(list);
  }
}
