package xyz.wanghongtao.rebac.gateway;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;
import xyz.wanghongtao.rebac.object.config.RuntimeConfigParam;
import xyz.wanghongtao.rebac.object.dataObject.CheckPermissionRecordDo;
import xyz.wanghongtao.rebac.repository.CheckPermissionRecordRepository;
import xyz.wanghongtao.rebac.service.CheckPermissionRecordService;
import xyz.wanghongtao.rebac.service.gateway.DatabaseLogGateway;

import java.util.List;

/**
 * @author wanghongtao
 * @data 2024/4/30 18:23
 */
@AllArgsConstructor
@Component
public class DatabaseLogGatewayImpl implements DatabaseLogGateway {
  private final CheckPermissionRecordService checkPermissionRecordService;
  private final RuntimeConfigParam runtimeConfigParam;
  @Override
  public List<CheckPermissionRecordDo> getCheckPermissionLogList() {
    return checkPermissionRecordService.findAll();
  }

  @Override
  public List<CheckPermissionRecordDo> getCheckPermissionLogListByAppKey(String appKey) {
    return null;
  }

}
