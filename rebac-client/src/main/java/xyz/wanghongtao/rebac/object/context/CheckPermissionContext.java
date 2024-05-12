package xyz.wanghongtao.rebac.object.context;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import xyz.wanghongtao.rebac.object.dataObject.ModelDo;
import xyz.wanghongtao.rebac.object.dataObject.RelationDo;
import xyz.wanghongtao.rebac.object.dataObject.model.PolicyDo;
import xyz.wanghongtao.rebac.object.engine.formula.Expression;

import java.util.ArrayList;
import java.util.List;
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

    String groovyScript;

    Expression expressionComputed;

    Map<String, Object> checkPermissionParam;

    List<RelationDo> relatiosFromExpression;

    Boolean currentObject;

  public CheckPermissionContext clone()  {
    return CheckPermissionContext.builder()
      .model(this.model.clone())
      .policy(this.policy.clone())
      .permissionContext(permissionContext.clone())
      .originPermissionContext(originPermissionContext)
      .relationHasPermission(relationHasPermission)
      .groovyScript(groovyScript)
      .expressionComputed(expressionComputed)
      .build();
  }
  public void pushRelationFromExpression(List<RelationDo> relationDoList) {
    if(this.relatiosFromExpression == null) {
      this.relatiosFromExpression = new ArrayList<>();
    }

    relatiosFromExpression.addAll(relationDoList);
  }

  public void pushRelationFromExpression(RelationDo relationDo) {
    if(this.relatiosFromExpression == null) {
      this.relatiosFromExpression = new ArrayList<>();
    }

    relatiosFromExpression.add(relationDo);
  }

  public void clearRelationFromExpression() {
    this.relatiosFromExpression = new ArrayList<>();
  }

  public List<RelationDo> getRelatiosFromExpression() {
    if(relatiosFromExpression == null) {
      return new ArrayList<>();
    }
    return relatiosFromExpression;
  }
}
