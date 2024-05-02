package xyz.wanghongtao.rebac.engine.relation.PermissionCheckHandler;

import xyz.wanghongtao.rebac.engine.formula.expression.BinaryDotExpression;
import xyz.wanghongtao.rebac.engine.formula.expression.IdentifierExpression;
import xyz.wanghongtao.rebac.object.context.CheckPermissionContext;
import xyz.wanghongtao.rebac.object.context.PermissionContext;
import xyz.wanghongtao.rebac.object.context.RelationContext;
import xyz.wanghongtao.rebac.object.dataObject.RelationDo;
import xyz.wanghongtao.rebac.object.runtime.PermissionRuntime;
import xyz.wanghongtao.rebac.service.engine.formula.Expression;

import java.util.ArrayList;
import java.util.List;

import static xyz.wanghongtao.rebac.engine.relation.RelationEngine.recursionExpression;

/**
 * @author wanghongtao
 * @data 2024/5/2 20:57
 */
public class BinaryDotHandler implements PermissionCheckHandler {
  @Override
  public Boolean handle(CheckPermissionContext checkPermissionContext, PermissionRuntime permissionRuntime, Expression expression) {
    BinaryDotExpression binaryDotExpression = (BinaryDotExpression) expression;
    //TODO 借助图数据库
    Expression left = binaryDotExpression.getLeft();
    Expression right = binaryDotExpression.getRight();
    PermissionContext permissionContext = checkPermissionContext.getPermissionContext();

    List<RelationDo> relationDoListFromLeft;
    if (left instanceof IdentifierExpression identifierExpression) {
      relationDoListFromLeft = permissionRuntime
        .getRelationBySubjectAndRelation(checkPermissionContext.getModel().getId(), RelationContext.builder()
          .subject(permissionContext.getSubject())
          .subjectType(permissionContext.getSubjectType())
          .relation(identifierExpression.getValue())
          .build());
    }  else {
      recursionExpression(checkPermissionContext, permissionRuntime, left);
      relationDoListFromLeft = new ArrayList<>(checkPermissionContext.getRelatiosFromExpression());
      checkPermissionContext.clearRelationFromExpression();
    }

    if(right instanceof IdentifierExpression identifierExpression) {
      for (RelationDo relationDo : relationDoListFromLeft) {
        List<RelationDo> relationDoList = permissionRuntime
          .getRelationBySubjectAndRelation(checkPermissionContext.getModel().getId(), RelationContext.builder()
            .subject(relationDo.getObject())
            .subjectType(relationDo.getObjectType())
            .relation(identifierExpression.getValue())
            .build());
        checkPermissionContext.pushRelationFromExpression(relationDoList);
        for (RelationDo re : relationDoList) {
          if(re.getObject().equals(checkPermissionContext.getOriginPermissionContext().getObject()) &&
            re.getObjectType().equals(checkPermissionContext.getOriginPermissionContext().getObjectType())) {
            return true;
          }
        }
      }

    } else {
      CheckPermissionContext clone = checkPermissionContext.clone();
      for (RelationDo relationDo : relationDoListFromLeft) {
        clone.setPermissionContext(PermissionContext.builder()
          .subject(relationDo.getObject())
          .subjectType(relationDo.getObjectType())
          .build());
        Boolean isTrue = recursionExpression(clone, permissionRuntime, right);
        checkPermissionContext.pushRelationFromExpression(clone.getRelatiosFromExpression());
        if (isTrue) {
          return true;
        }
      }
    }
    return false;
  }
}
