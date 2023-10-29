package xyz.wanghongtao.rebac.object.runtime;

import lombok.Builder;
import lombok.Data;
import xyz.wanghongtao.rebac.object.dataObject.RelationDo;
import xyz.wanghongtao.rebac.service.gateway.DatabaseGateway;

import java.util.List;

/**
 * <p>
 *   权限检查时bean依赖
 * </p>
 * @author wanghongtao
 * @data 2023/10/29 11:04
 */
@Data
@Builder
public class PermissionRuntime {
  DatabaseGateway databaseGateway;

  public List<RelationDo> getRelationByTriple(String triple) {
    return databaseGateway.getRelationByTriple(triple);
  }
}
