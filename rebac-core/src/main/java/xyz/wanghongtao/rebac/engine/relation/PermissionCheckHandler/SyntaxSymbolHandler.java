package xyz.wanghongtao.rebac.engine.relation.PermissionCheckHandler;

import xyz.wanghongtao.rebac.engine.formula.expression.SyntaxSymbolLiteral;
import xyz.wanghongtao.rebac.object.context.CheckPermissionContext;
import xyz.wanghongtao.rebac.object.dataObject.RelationDo;
import xyz.wanghongtao.rebac.object.runtime.PermissionRuntime;
import xyz.wanghongtao.rebac.service.engine.formula.Expression;

/**
 * @author wanghongtao
 * @data 2024/5/2 21:08
 */
public class SyntaxSymbolHandler implements PermissionCheckHandler {
  @Override
  public Boolean handle(CheckPermissionContext checkPermissionContext, PermissionRuntime permissionRuntime, Expression expression) {
    SyntaxSymbolLiteral syntaxSymbolLiteral = (SyntaxSymbolLiteral) expression;
    String symbolLiteralValue = syntaxSymbolLiteral.getValue();
    if(symbolLiteralValue.equals("$Object")) {
      RelationDo relationDo = RelationDo.builder()
        .object(checkPermissionContext.getPermissionContext().getObject())
        .objectType(checkPermissionContext.getPermissionContext().getObjectType())
        .build();
      checkPermissionContext.pushRelationFromExpression(relationDo);
    } else if (symbolLiteralValue.equals("$Subject")){
      RelationDo relationDo = RelationDo.builder()
        .object(checkPermissionContext.getPermissionContext().getSubject())
        .objectType(checkPermissionContext.getPermissionContext().getSubjectType())
        .build();
      checkPermissionContext.pushRelationFromExpression(relationDo);
    }
    return false;
  }
}
