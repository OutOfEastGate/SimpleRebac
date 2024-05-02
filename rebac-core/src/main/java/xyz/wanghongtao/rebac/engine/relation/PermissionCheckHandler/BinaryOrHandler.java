package xyz.wanghongtao.rebac.engine.relation.PermissionCheckHandler;

import xyz.wanghongtao.rebac.engine.formula.expression.BinaryOrExpression;
import xyz.wanghongtao.rebac.object.context.CheckPermissionContext;
import xyz.wanghongtao.rebac.object.runtime.PermissionRuntime;
import xyz.wanghongtao.rebac.service.engine.formula.Expression;

import static xyz.wanghongtao.rebac.engine.relation.RelationEngine.recursionExpression;

/**
 * @author wanghongtao
 * @data 2024/5/2 21:04
 */
public class BinaryOrHandler implements PermissionCheckHandler {
  @Override
  public Boolean handle(CheckPermissionContext checkPermissionContext, PermissionRuntime permissionRuntime, Expression expression) {
    BinaryOrExpression binaryOrExpression = (BinaryOrExpression) expression;
    Expression left = binaryOrExpression.getLeft();
    Expression right = binaryOrExpression.getRight();
    return recursionExpression(checkPermissionContext, permissionRuntime, left) || recursionExpression(checkPermissionContext, permissionRuntime, right);

  }
}
