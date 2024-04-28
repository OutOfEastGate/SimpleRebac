package xyz.wanghongtao.rebac.object.context;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import xyz.wanghongtao.rebac.object.dataObject.ModelDo;
import xyz.wanghongtao.rebac.object.dataObject.model.PolicyDo;
import xyz.wanghongtao.rebac.service.engine.formula.Expression;

import java.util.Map;

/**
 * @author wanghongtao
 * @data 2023/7/19 23:19
 */
@Setter
@Getter
@Builder
@Data
public class CheckPermissionContext {
    String appKey;

    ModelDo model;

    PolicyDo policy;

    String triple;

    PermissionContext permissionContext;
    PermissionContext originPermissionContext;

    String relationHasPermission;

    Expression expressionComputed;

    Map<String, Object> checkPermissionParam;

  public CheckPermissionContext clone()  {
    return CheckPermissionContext.builder()
      .model(this.model)
      .policy(this.policy)
      .originPermissionContext(originPermissionContext)
      .relationHasPermission(relationHasPermission)
      .expressionComputed(expressionComputed)
      .build();
  }
}
