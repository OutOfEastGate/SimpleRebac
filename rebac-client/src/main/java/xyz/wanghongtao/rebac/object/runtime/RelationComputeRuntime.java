package xyz.wanghongtao.rebac.object.runtime;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import xyz.wanghongtao.rebac.object.config.RuntimeConfigParam;
import xyz.wanghongtao.rebac.service.RelationComputeService;

/**
 * @author wanghongtao <wanghongtao05@kuaishou.com>
 * Created on 2024-04-26
 */
@Slf4j
@Component
public class RelationComputeRuntime {
  private final RuntimeConfigParam configParam;
  private final RelationComputeService relationComputeService;

  public RelationComputeRuntime(RuntimeConfigParam configParam, RelationComputeService relationComputeService) {
    this.configParam = configParam;
    this.relationComputeService = relationComputeService;
  }

  @PostConstruct
  public void init() {
    if(configParam.getStartGraphCompute()) {
      log.info("开启图数据库");
    }
  }
}
