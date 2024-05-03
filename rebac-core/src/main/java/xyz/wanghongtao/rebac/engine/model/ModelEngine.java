package xyz.wanghongtao.rebac.engine.model;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;
import xyz.wanghongtao.rebac.engine.formula.FormulaParser;
import xyz.wanghongtao.rebac.engine.formula.expression.*;
import xyz.wanghongtao.rebac.exception.CustomException;
import xyz.wanghongtao.rebac.object.form.model.AddModelForm;
import xyz.wanghongtao.rebac.object.form.policy.PolicyForm;
import xyz.wanghongtao.rebac.object.form.policy.UpdatePolicyForm;
import xyz.wanghongtao.rebac.object.engine.formula.Expression;

import java.util.*;

/**
 * @author wanghongtao
 * @data 2023/7/23 16:41
 */
@Slf4j
public class ModelEngine {

    /**
     * 预检查policy
     * 1. 检查对象定义之间是否重复
     * 2. 检查关联对象的关系是否合理
     * @param addModelForm model定义
     */
    public static void preCheckPolicy(AddModelForm addModelForm) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        if(addModelForm.getPolicy() == null) {
          log.info("#policy为空，初始化policy");
          addModelForm.setPolicy(PolicyForm.builder()
            .description(addModelForm.getDescription())
            .definitions(new ArrayList<>())
            .build());
          return;
        }

        log.info("#新增-预检查Policy开始: {}", addModelForm.getPolicy());


      StringBuilder errorInfo = preCheckPolicy(addModelForm.getPolicy());
      stopWatch.stop();
        if (!errorInfo.isEmpty()) {
          log.error("#预检查未通过: 耗时{}", stopWatch.getTotalTimeSeconds());
          log.error(errorInfo.toString());
            throw new CustomException(errorInfo.toString());
        }
        log.info("#预检查Policy结束: 耗时{}", stopWatch.getTotalTimeSeconds());
    }

  /**
   * 预检查policy
   * 1. 检查对象定义之间是否重复
   * 2. 检查关联对象的关系是否合理
   * @param updatePolicyForm model定义
   */
  public static void preCheckPolicy(UpdatePolicyForm updatePolicyForm) {
    StopWatch stopWatch = new StopWatch();
    stopWatch.start();


    log.info("#更新-预检查Policy开始: {}", updatePolicyForm.getId());


    StringBuilder errorInfo = preCheckPolicy(PolicyForm.builder()
      .description(updatePolicyForm.getDescription())
      .definitions(updatePolicyForm.getDefinitions())
      .build());
    stopWatch.stop();
    if (!errorInfo.isEmpty()) {
      log.error("#预检查未通过: 耗时{}", stopWatch.getTotalTimeSeconds());
      log.error(errorInfo.toString());
      throw new CustomException(errorInfo.toString());
    }
    log.info("#预检查Policy结束: 耗时{}", stopWatch.getTotalTimeSeconds());
  }

  private static StringBuilder preCheckPolicy(PolicyForm policyForm) {
    Map<String, Map<String, List<String>>> permissionMap = new HashMap<>();
    Map<String, Map<String, String>> relationMap = new HashMap<>();

    StringBuilder errorInfo = new StringBuilder();

    policyForm.getDefinitions().forEach(definition -> {
        if (permissionMap.containsKey(definition.getObjectType())) {
            errorInfo.append("ObjectType: ").append(definition.getObjectType()).append("定义重复; ");
        } else {
            Map<String, List<String>> permission_relationsCanAccess = new HashMap<>();
            if (definition.getPermissions() != null && definition.getPermissions().size() > 0) {
                definition.getPermissions().forEach(permission -> {
                    //TODO 解析表达式 + - ->成RelationList
                    if (permission_relationsCanAccess.containsKey(permission.getPermission())) {
                        errorInfo.append("Permission：").append(permission.getPermission()).append("定义重复; ");
                    }else {
                        List<String> relationCanAccess = List.of(permission.getRelationCanAccess());
                        permission_relationsCanAccess.put(permission.getPermission(), relationCanAccess);
                    }
                });
            }
            permissionMap.put(definition.getObjectType(), permission_relationsCanAccess);

            Map<String, String> relation_subjectType = new HashMap<>();
            if (definition.getRelations() != null && definition.getRelations().size() > 0) {
                definition.getRelations().forEach(relation -> {
                    if (relation_subjectType.containsKey(relation.getRelation())) {
                        errorInfo.append("Relation：").append(relation.getRelation()).append("定义重复; ");
                    }else {
                        relation_subjectType.put(relation.getRelation(), relation.getSubjectType());
                    }
                });
            }
            relationMap.put(definition.getObjectType(), relation_subjectType);
        }
    });

    //检验relation
    relationMap.forEach((objectType, relation_subjectType) -> {
        relation_subjectType.forEach((relation, subjectType) -> {
          if (!relationMap.containsKey(subjectType)) {
                errorInfo.append("Relation定义错误: 对象类型[").append(subjectType).append("]不存在; ");
            }
        });
    });
    //检查关联关系
    permissionMap.forEach((objectType, permissionMaps) -> {
        if (permissionMaps !=null && permissionMaps.size() > 0) {
            permissionMaps.forEach((permission, relationsCanAccess) -> {
                relationsCanAccess.forEach(relationCanAccess -> {
                  //解析表达式
                  FormulaParser formulaParser = new FormulaParser(relationCanAccess);
                  Expression expression = formulaParser.parseExpression();
                  Map<String, String> relationObjectTypeMap = relationMap.get(objectType);
                  recursionExpression(expression, relationObjectTypeMap, errorInfo);
//                  if (!relationMap.get(objectType).containsKey(relationCanAccess)) {
//                    errorInfo.append("Permission定义错误: ").append(relationCanAccess).append("不存在; ");
//                  }
                });
            });
        }
    });
    return errorInfo;
  }

  public static String recursionExpression(Expression expression, Map<String, String> relationObjectTypeMap , StringBuilder errorInfo) {
    if (expression instanceof BinaryAndExpression binaryAndExpression) {
      log.info("[login and]");
      recursionExpression(binaryAndExpression.getLeft(), relationObjectTypeMap, errorInfo);
      recursionExpression(binaryAndExpression.getRight(), relationObjectTypeMap, errorInfo);
    } else if (expression instanceof BinaryOrExpression binaryOrExpression) {
      log.info("[login or]");
      recursionExpression(binaryOrExpression.getLeft(), relationObjectTypeMap, errorInfo);
      recursionExpression(binaryOrExpression.getRight(), relationObjectTypeMap, errorInfo);
    } else if (expression instanceof BinaryDotExpression binaryDotExpression) {
      log.info("[login dot]");
      //TODO 关系运算符 . 检查需要特殊处理
//      recursionExpression(binaryDotExpression.getLeft(), relationObjectTypeMap, errorInfo);
//      recursionExpression(binaryDotExpression.getRight(), relationObjectTypeMap, errorInfo);
    } else if(expression instanceof NotExpression notExpression) {
      log.info("[logic not]");
      recursionExpression(notExpression.getRight(), relationObjectTypeMap, errorInfo);
    } else if(expression instanceof EqualEqualExpression equalEqualExpression) {
      log.info("[logic equal equal]");
      recursionExpression(equalEqualExpression.getLeft(), relationObjectTypeMap, errorInfo);
      recursionExpression(equalEqualExpression.getRight(), relationObjectTypeMap, errorInfo);
    } else if(expression instanceof NotEqualExpression notEqualExpression) {
      log.info("[logic not equal]");
      recursionExpression(notEqualExpression.getLeft(), relationObjectTypeMap, errorInfo);
      recursionExpression(notEqualExpression.getRight(), relationObjectTypeMap, errorInfo);
    } else if(expression instanceof IdentifierExpression identifier) {
      String relationCanAccess = identifier.getValue();
      log.info(identifier.getValue());
      if (!relationObjectTypeMap.containsKey(relationCanAccess)) {
        errorInfo.append("Permission定义错误: 关系[").append(relationCanAccess).append("]不存在; ");
      }
      return identifier.getValue();
    } else if(expression instanceof SyntaxSymbolLiteral syntaxSymbolLiteral) {
      String symbolLiteralValue = syntaxSymbolLiteral.getValue();
      log.info("[syntaxSymbolLiteral] {}", symbolLiteralValue);
      if (symbolLiteralValue.equals("$Object") || symbolLiteralValue.equals("$Subject")) {

      } else {
        errorInfo.append("[").append(symbolLiteralValue).append("]").append("不存在");
      }
    }else {
      log.error("UnKnow Expression");
    }
    return null;
  }
}
