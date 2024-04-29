package xyz.wanghongtao.rebac.runtime;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;
import xyz.wanghongtao.rebac.object.context.CheckPermissionContext;
import xyz.wanghongtao.rebac.object.dataObject.RelationDo;
import xyz.wanghongtao.rebac.object.result.CheckPermissionResult;
import xyz.wanghongtao.rebac.service.gateway.DatabaseGateway;
import xyz.wanghongtao.rebac.service.gateway.DatabaseGatewaySync;

import java.util.List;

/**
 * @author wanghongtao
 * @data 2024/4/29 17:08
 */
@Data
@AllArgsConstructor
@Component
public class PermissionServiceRuntime {

  private final DatabaseGateway databaseGateway;
  private final DatabaseGatewaySync databaseGatewaySync;

  public List<RelationDo> getRelationByTriple(String triple) {
    return databaseGateway.getRelationByTriple(triple);
  }

  public void saveCheckPermissionRecord(CheckPermissionResult checkPermissionResult, CheckPermissionContext checkPermissionContext) {
    databaseGatewaySync.saveCheckPermissionRecord(checkPermissionResult, checkPermissionContext);
  }
}
