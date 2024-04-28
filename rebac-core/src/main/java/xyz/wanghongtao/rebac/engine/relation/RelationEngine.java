package xyz.wanghongtao.rebac.engine.relation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;
import xyz.wanghongtao.rebac.engine.formula.expression.BinaryAndExpression;
import xyz.wanghongtao.rebac.engine.formula.expression.BinaryDotExpression;
import xyz.wanghongtao.rebac.engine.formula.expression.BinaryOrExpression;
import xyz.wanghongtao.rebac.engine.formula.expression.IdentifierExpression;
import xyz.wanghongtao.rebac.exception.CustomException;
import xyz.wanghongtao.rebac.exception.ErrorCode;
import xyz.wanghongtao.rebac.object.context.CheckPermissionContext;
import xyz.wanghongtao.rebac.object.context.PermissionContext;
import xyz.wanghongtao.rebac.object.context.RelationContext;
import xyz.wanghongtao.rebac.object.dataObject.RelationDo;
import xyz.wanghongtao.rebac.object.dataObject.model.Definition;
import xyz.wanghongtao.rebac.object.dataObject.model.PolicyDo;
import xyz.wanghongtao.rebac.object.runtime.PermissionRuntime;
import xyz.wanghongtao.rebac.service.engine.formula.Expression;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author wanghongtao
 * @data 2023/7/23 16:24
 */
@Slf4j
public class RelationEngine {

  //直接关系搜索
  public static Boolean checkPermissionDirect(CheckPermissionContext checkPermissionContext, PermissionRuntime permissionRuntime) {
    String relationHasPermission = checkPermissionContext.getRelationHasPermission();
    String stringBuilder = checkPermissionContext.getPermissionContext().getObjectType() +
      ":" +
      checkPermissionContext.getPermissionContext().getObject() +
      "#" +
      relationHasPermission +
      "@" +
      checkPermissionContext.getPermissionContext().getSubjectType() +
      ":" +
      checkPermissionContext.getPermissionContext().getSubject();
    List<RelationDo> relationByTriple = permissionRuntime.getRelationByTriple(stringBuilder);
    return relationByTriple.size() > 0;
  }


  //推导关系
  public static Boolean checkPermissionCompute(CheckPermissionContext checkPermissionContext, PermissionRuntime permissionRuntime) {
    //TODO 推导关系
    Expression expressionComputed = checkPermissionContext.getExpressionComputed();
    checkPermissionContext.setOriginPermissionContext(checkPermissionContext.getPermissionContext());
    return recursionExpression(checkPermissionContext, permissionRuntime, expressionComputed);
  }

    /**
     * 虚拟线程预检验relation
     * 1. 检验objectType
     * 2. 检验subjectType
     * 3. 检验relation
     * @param relationContext relation信息
     * @param policyById 权限策略定义
     */
    public static void preCheckRelation(RelationContext relationContext, PolicyDo policyById) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        log.info("#开始预检验Relation: {}", relationContext.getTriple());
        List<Definition> definitions = policyById.getDefinitions();
        //检查objectType
        CountDownLatch countDownLatch = new CountDownLatch(2);
        AtomicReference<Boolean> isObjectTypeExist = new AtomicReference<>(false);
        AtomicReference<Boolean> isRelationExist = new AtomicReference<>(false);
        Thread.startVirtualThread(() -> {
            definitions.forEach(definition -> {
                if (definition.getObjectType().equals(relationContext.getObjectType())) {
                    isObjectTypeExist.set(true);
                }
            });
            countDownLatch.countDown();
        });
        //检查subjectType
        AtomicReference<Boolean> isSubjectTypeExist = new AtomicReference<>(false);
        Thread.startVirtualThread(() -> {
            definitions.forEach(definition -> {
                if (definition.getObjectType().equals(relationContext.getSubjectType())) {
                    isSubjectTypeExist.set(true);
                }

                if (definition.getRelations() != null && definition.getRelations().size() > 0) {
                    definition.getRelations().forEach(relation -> {
                        if (relation.getSubjectType().equals(relationContext.getSubjectType()) &&
                                relation.getRelation().equals(relationContext.getRelation())) {
                            isRelationExist.set(true);
                        }
                    });
                }
            });
            countDownLatch.countDown();
        });
        stopWatch.stop();
        log.info("#预检验Relation完成：耗时{}秒", stopWatch.getTotalTimeSeconds());
        //检查relation
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (!isObjectTypeExist.get() || !isSubjectTypeExist.get()) {
            throw new CustomException(ErrorCode.OBJECT_OR_SUBJECT_NOT_EXIST);
        }

        if (!isRelationExist.get()) {
            throw new CustomException(ErrorCode.Relation_NOT_EXIST);
        }
    }

    public static Boolean recursionExpression(CheckPermissionContext checkPermissionContext, PermissionRuntime permissionRuntime, Expression expression) {
        if (expression instanceof BinaryOrExpression binaryOrExpression) {
          Expression left = binaryOrExpression.getLeft();
          Expression right = binaryOrExpression.getRight();
          return recursionExpression(checkPermissionContext, permissionRuntime, left) || recursionExpression(checkPermissionContext, permissionRuntime, right);
        } else if(expression instanceof BinaryAndExpression binaryAndExpression) {
          Expression left = binaryAndExpression.getLeft();
          Expression right = binaryAndExpression.getRight();
          return recursionExpression(checkPermissionContext, permissionRuntime, left) && recursionExpression(checkPermissionContext, permissionRuntime, right);
        } else if (expression instanceof BinaryDotExpression binaryDotExpression) {
          //TODO 借助图数据库
          Expression left = binaryDotExpression.getLeft();
          Expression right = binaryDotExpression.getRight();
          PermissionContext permissionContext = checkPermissionContext.getPermissionContext();

          List<RelationDo> relationDoListFromLeft = new ArrayList<>();
          if (left instanceof IdentifierExpression identifierExpression) {
            relationDoListFromLeft = permissionRuntime
              .getRelationBySubjectAndRelation(checkPermissionContext.getModel().getId(), RelationContext.builder()
                .subject(permissionContext.getSubject())
                .subjectType(permissionContext.getSubjectType())
                .relation(identifierExpression.getValue())
                .build());
          } else {
            Boolean isTrue = recursionExpression(checkPermissionContext, permissionRuntime, left);
            if (isTrue) {
              return true;
            }
          }

          if(right instanceof IdentifierExpression identifierExpression) {
            for (RelationDo relationDo : relationDoListFromLeft) {
              List<RelationDo> relationDoList = permissionRuntime
                .getRelationBySubjectAndRelation(checkPermissionContext.getModel().getId(), RelationContext.builder()
                  .subject(relationDo.getObject())
                  .subjectType(relationDo.getObjectType())
                  .relation(identifierExpression.getValue())
                  .build());
              for (RelationDo aDo : relationDoList) {
                if(aDo.getObject().equals(checkPermissionContext.getOriginPermissionContext().getObject()) &&
                    aDo.getObjectType().equals(checkPermissionContext.getOriginPermissionContext().getObjectType())) {
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
              if (isTrue) {
                return true;
              }
            }
          }
        } else if(expression instanceof IdentifierExpression identifierExpression) {
          String relation = identifierExpression.getValue();
          checkPermissionContext.setRelationHasPermission(relation);
          return checkPermissionDirect(checkPermissionContext, permissionRuntime);
        }
        return false;
    }
}
