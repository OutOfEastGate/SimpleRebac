package xyz.wanghongtao.rebac.engine.relation.PermissionCheckHandler;

import xyz.wanghongtao.rebac.engine.formula.expression.EqualEqualExpression;
import xyz.wanghongtao.rebac.engine.formula.expression.StringLiteral;
import xyz.wanghongtao.rebac.object.context.CheckPermissionContext;
import xyz.wanghongtao.rebac.object.dataObject.RelationDo;
import xyz.wanghongtao.rebac.object.runtime.PermissionRuntime;
import xyz.wanghongtao.rebac.object.engine.formula.Expression;

import java.util.ArrayList;
import java.util.List;

import static xyz.wanghongtao.rebac.engine.relation.RelationEngine.recursionExpression;

/**
 * @author wanghongtao
 * @data 2024/5/2 20:53
 */
public class EqualEqualHandler implements PermissionCheckHandler {
  @Override
  public Boolean handle(CheckPermissionContext checkPermissionContext, PermissionRuntime permissionRuntime, Expression expression) {
    EqualEqualExpression equalEqualExpression = (EqualEqualExpression) expression;
    if (equalEqualExpression.getLeft() instanceof StringLiteral stringLiteralLeft) {
      if(equalEqualExpression.getRight() instanceof StringLiteral stringLiteralRight) {
        return stringLiteralLeft.getValue().replaceAll("^\"|\"$", "").equals(stringLiteralRight.getValue().replaceAll("^\"|\"$", ""));
      } else {
        recursionExpression(checkPermissionContext, permissionRuntime, equalEqualExpression.getRight());
        List<RelationDo> relationsFromExpressionRight = new ArrayList<>(checkPermissionContext.getRelatiosFromExpression());
        for (RelationDo relationDo : relationsFromExpressionRight) {
          if (relationDo.getSubject().equals(stringLiteralLeft.getValue())) {
            return true;
          }
        }
      }
    } else {
      if(equalEqualExpression.getRight() instanceof StringLiteral stringLiteralRight) {
        recursionExpression(checkPermissionContext, permissionRuntime, equalEqualExpression.getLeft());
        List<RelationDo> relatiosFromExpression = checkPermissionContext.getRelatiosFromExpression();
        List<RelationDo> relationsFromExpressionLeft = relatiosFromExpression == null ? new ArrayList<>() : new ArrayList<>(relatiosFromExpression);
        for (RelationDo relationDo : relationsFromExpressionLeft) {
          if (relationDo.getObject().equals(stringLiteralRight.getValue().replaceAll("^\"|\"$", ""))) {
            return true;
          }
        }
      } else {
        recursionExpression(checkPermissionContext, permissionRuntime, equalEqualExpression.getLeft());
        List<RelationDo> relationsFromExpressionLeft = new ArrayList<>(checkPermissionContext.getRelatiosFromExpression());
        checkPermissionContext.clearRelationFromExpression();
        recursionExpression(checkPermissionContext, permissionRuntime, equalEqualExpression.getRight());
        List<RelationDo> relationsFromExpressionRight = new ArrayList<>(checkPermissionContext.getRelatiosFromExpression());
        checkPermissionContext.clearRelationFromExpression();
        for (RelationDo relationDoLeft : relationsFromExpressionLeft) {
          for (RelationDo relationDoRight : relationsFromExpressionRight) {
            if(relationDoLeft.getObject().replaceAll("^\"|\"$", "").equals(relationDoRight.getObject().replaceAll("^\"|\"$", ""))) {
              return true;
            }
          }
        }
        return false;
      }
    }
    return false;
  }

}
