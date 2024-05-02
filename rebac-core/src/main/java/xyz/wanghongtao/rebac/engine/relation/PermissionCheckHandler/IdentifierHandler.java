package xyz.wanghongtao.rebac.engine.relation.PermissionCheckHandler;

import xyz.wanghongtao.rebac.engine.formula.expression.IdentifierExpression;
import xyz.wanghongtao.rebac.object.context.CheckPermissionContext;
import xyz.wanghongtao.rebac.object.runtime.PermissionRuntime;
import xyz.wanghongtao.rebac.service.engine.formula.Expression;

import static xyz.wanghongtao.rebac.engine.relation.RelationEngine.checkPermissionDirect;

/**
 * @author wanghongtao
 * @data 2024/5/2 21:10
 */
public class IdentifierHandler implements PermissionCheckHandler {
  @Override
  public Boolean handle(CheckPermissionContext checkPermissionContext, PermissionRuntime permissionRuntime, Expression expression) {
    IdentifierExpression identifierExpression = (IdentifierExpression) expression;
    String relation = identifierExpression.getValue();
    checkPermissionContext.setRelationHasPermission(relation);
    return checkPermissionDirect(checkPermissionContext, permissionRuntime);
  }
}
