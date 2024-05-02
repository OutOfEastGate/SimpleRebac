package xyz.wanghongtao.rebac.engine.relation.PermissionCheckHandler;

import xyz.wanghongtao.rebac.engine.formula.expression.NotExpression;
import xyz.wanghongtao.rebac.object.context.CheckPermissionContext;
import xyz.wanghongtao.rebac.object.runtime.PermissionRuntime;
import xyz.wanghongtao.rebac.service.engine.formula.Expression;

import static xyz.wanghongtao.rebac.engine.relation.RelationEngine.recursionExpression;

/**
 * @author wanghongtao
 * @data 2024/5/2 21:16
 */
public class NotHandler implements PermissionCheckHandler {
  @Override
  public Boolean handle(CheckPermissionContext checkPermissionContext, PermissionRuntime permissionRuntime, Expression expression) {
    NotExpression notExpression = (NotExpression) expression;
    return !recursionExpression(checkPermissionContext, permissionRuntime, notExpression.getRight());
  }
}
