package xyz.wanghongtao.rebac.engine.relation.PermissionCheckHandler;

import xyz.wanghongtao.rebac.engine.formula.expression.EqualEqualExpression;
import xyz.wanghongtao.rebac.engine.formula.expression.NotEqualExpression;
import xyz.wanghongtao.rebac.object.context.CheckPermissionContext;
import xyz.wanghongtao.rebac.object.runtime.PermissionRuntime;
import xyz.wanghongtao.rebac.object.engine.formula.Expression;

/**
 * @author wanghongtao
 * @data 2024/5/2 21:15
 */
public class NotEqualHandler implements PermissionCheckHandler {
  EqualEqualHandler equalEqualHandler = new EqualEqualHandler();
  @Override
  public Boolean handle(CheckPermissionContext checkPermissionContext, PermissionRuntime permissionRuntime, Expression expression) {
    NotEqualExpression notEqualExpression = (NotEqualExpression) expression;
    EqualEqualExpression equalEqualExpression = new EqualEqualExpression(notEqualExpression.getToken(), notEqualExpression.getLeft());
    equalEqualExpression.setRight(notEqualExpression.getRight());
    return !equalEqualHandler.handle(checkPermissionContext, permissionRuntime, equalEqualExpression);
  }
}
