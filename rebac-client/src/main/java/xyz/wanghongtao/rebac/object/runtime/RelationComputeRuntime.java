package xyz.wanghongtao.rebac.object.runtime;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import xyz.wanghongtao.rebac.service.RelationComputeService;

/**
 * @author wanghongtao <wanghongtao05@kuaishou.com>
 * Created on 2024-04-26
 */
@Slf4j
@Component
public class RelationComputeRuntime {
  @Value("${wht.back.startGraphCompute}")
  Boolean startGraphCompute;
  private final RelationComputeService relationComputeService;

  public RelationComputeRuntime(RelationComputeService relationComputeService) {
    this.relationComputeService = relationComputeService;
  }

  @PostConstruct
  public void init() {
    if(startGraphCompute) {
      log.info("开启图数据库");
    }
  }
}
