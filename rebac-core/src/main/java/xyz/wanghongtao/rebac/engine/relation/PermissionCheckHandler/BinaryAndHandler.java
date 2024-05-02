package xyz.wanghongtao.rebac.engine.relation.PermissionCheckHandler;

import xyz.wanghongtao.rebac.engine.formula.expression.BinaryAndExpression;
import xyz.wanghongtao.rebac.object.context.CheckPermissionContext;
import xyz.wanghongtao.rebac.object.runtime.PermissionRuntime;
import xyz.wanghongtao.rebac.service.engine.formula.Expression;

import static xyz.wanghongtao.rebac.engine.relation.RelationEngine.recursionExpression;

/**
 * @author wanghongtao
 * @data 2024/5/2 21:05
 */
public class BinaryAndHandler implements PermissionCheckHandler {
  @Override
  public Boolean handle(CheckPermissionContext checkPermissionContext, PermissionRuntime permissionRuntime, Expression expression) {
    BinaryAndExpression binaryAndExpression = (BinaryAndExpression) expression;
    Expression left = binaryAndExpression.getLeft();
    Expression right = binaryAndExpression.getRight();
    return recursionExpression(checkPermissionContext, permissionRuntime, left) && recursionExpression(checkPermissionContext, permissionRuntime, right);
  }
}
