package xyz.wanghongtao.rebac.gateway;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import xyz.wanghongtao.rebac.object.context.CheckPermissionContext;
import xyz.wanghongtao.rebac.object.dataObject.CheckPermissionRecordDo;
import xyz.wanghongtao.rebac.object.result.CheckPermissionResult;
import xyz.wanghongtao.rebac.runtime.DatabaseGatewaySyncRuntime;
import xyz.wanghongtao.rebac.service.gateway.DatabaseGatewaySync;

/**
 * <p>
 *   数据库异步操作
 * </p>
 * @author wanghongtao
 * @data 2024/4/28 15:04
 */
@AllArgsConstructor
@Slf4j
@Component
public class DatabaseGateSyncImpl implements DatabaseGatewaySync {
  private DatabaseGatewaySyncRuntime databaseGatewaySyncRuntime;
  @Override
  public void saveCheckPermissionRecord(CheckPermissionResult checkPermissionResult, CheckPermissionContext checkPermissionContext) {
    Thread.startVirtualThread(() -> databaseGatewaySyncRuntime.saveCheckPermissionRecord(CheckPermissionRecordDo.builder()
        .appKey(checkPermissionContext.getAppKey())
      .modelId(checkPermissionContext.getModel().getId())
      .policyId(checkPermissionContext.getPolicy().getId())
      .triple(checkPermissionContext.getTriple())
      .hasPermission(checkPermissionResult.getHasPermission())
      .useTimeSeconds(checkPermissionResult.getUseTime())
      .createTimestamp(System.currentTimeMillis())
      .build()));
  }
}
