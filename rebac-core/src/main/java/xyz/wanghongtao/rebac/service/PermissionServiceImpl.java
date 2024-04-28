package xyz.wanghongtao.rebac.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;
import xyz.wanghongtao.rebac.engine.formula.FormulaParser;
import xyz.wanghongtao.rebac.engine.relation.RelationEngine;
import xyz.wanghongtao.rebac.object.context.CheckPermissionContext;
import xyz.wanghongtao.rebac.object.context.CheckRelationContext;
import xyz.wanghongtao.rebac.object.dataObject.RelationDo;
import xyz.wanghongtao.rebac.object.dataObject.model.Permission;
import xyz.wanghongtao.rebac.object.dataObject.model.PolicyDo;
import xyz.wanghongtao.rebac.object.result.CheckPermissionResult;
import xyz.wanghongtao.rebac.object.runtime.PermissionRuntime;
import xyz.wanghongtao.rebac.service.engine.formula.Expression;
import xyz.wanghongtao.rebac.service.gateway.DatabaseGateway;
import xyz.wanghongtao.rebac.service.gateway.DatabaseGatewaySync;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author wanghongtao
 * @data 2023/7/19 22:18
 */
@Slf4j
@AllArgsConstructor
@Service
public class PermissionServiceImpl implements PermissionService {
    private final DatabaseGateway databaseGateway;
    private final DatabaseGatewaySync databaseGatewaySync;
    @Override
    public CheckPermissionResult checkPermission(PermissionRuntime permissionRuntime, CheckPermissionContext checkPermissionContext) {
        //TODO 实现图关联模型查询
      StopWatch stopWatch = new StopWatch();
      stopWatch.start();
        Set<String> relationsHasPermission = new HashSet<>();
        AtomicReference<List<Permission>> permissions = new AtomicReference<>(new ArrayList<>());
        //查询出具有该权限的关系
        PolicyDo policy = checkPermissionContext.getPolicy();
        policy.getDefinitions().forEach(definition -> {
            if (definition.getObjectType().equals(checkPermissionContext.getPermissionContext().getSubjectType())) {
                permissions.set(definition.getPermissions());
            }
        });

        permissions.get().forEach(permission -> {
            if (checkPermissionContext.getPermissionContext().getPermission().equals(permission.getPermission())) {
                relationsHasPermission.add(permission.getRelationCanAccess());
            }
        });
        AtomicReference<Boolean> result = new AtomicReference<>(false);
        CountDownLatch countDownLatch = new CountDownLatch(relationsHasPermission.size());
        //根据关系查询出relation
        relationsHasPermission.forEach(relationHasPermission -> Thread.startVirtualThread(() -> {
          //解析表达式
          checkPermissionContext.setRelationHasPermission(relationHasPermission);
          FormulaParser formulaParser = new FormulaParser(relationHasPermission);
          //是否有关系推导
          if(formulaParser.getTokenLength() == 1) {
            //检查直接关系
            Boolean isHasDirectRelation = RelationEngine.checkPermissionDirect(checkPermissionContext, permissionRuntime);
            if (isHasDirectRelation) {
              result.set(true);
            }
          } else {
            Expression expression = formulaParser.parseExpression();
            checkPermissionContext.setExpressionComputed(expression);
            Boolean isHasComputedRelation = RelationEngine.checkPermissionCompute(checkPermissionContext, permissionRuntime);
            if (isHasComputedRelation) {
              result.set(true);
            }
          }
            countDownLatch.countDown();
        }));
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
      stopWatch.stop();

        log.info("权限校验用时：{}秒",stopWatch.getTotalTimeSeconds());
      CheckPermissionResult checkPermissionResult = CheckPermissionResult.builder()
        .hasPermission(result.get())
        .useTime(stopWatch.getTotalTimeSeconds())
        .build();
      databaseGatewaySync.saveCheckPermissionRecord(checkPermissionResult, checkPermissionContext);
      return checkPermissionResult;
    }

    @Override
    public Boolean checkRelation(CheckRelationContext checkRelationContext) {
        AtomicReference<List<RelationDo>> relationByTriple = new AtomicReference<>();
        CountDownLatch countDownLatch = new CountDownLatch(1);
        //检查数据库是否有该条关系
        Thread.startVirtualThread(() -> {
            relationByTriple.set(databaseGateway.getRelationByTriple(checkRelationContext.getTriple()));
            countDownLatch.countDown();
        });
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return relationByTriple.get().size() > 0;
    }
}
