package xyz.wanghongtao.rebac.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.wanghongtao.rebac.object.context.CheckPermissionContext;
import xyz.wanghongtao.rebac.object.context.CheckRelationContext;
import xyz.wanghongtao.rebac.object.dataObject.RelationDo;
import xyz.wanghongtao.rebac.object.dataObject.model.Permission;
import xyz.wanghongtao.rebac.object.dataObject.model.PolicyDo;
import xyz.wanghongtao.rebac.service.gateway.DatabaseGateway;

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
@AllArgsConstructor
@Service
public class PermissionServiceImpl implements PermissionService {
    DatabaseGateway databaseGateway;

    @Override
    public Boolean checkPermission(CheckPermissionContext checkPermissionContext) {
        //TODO 实现图关联模型查询
        Set<String> relationsHasPermission = new HashSet<>();
        AtomicReference<List<Permission>> permissions = new AtomicReference<>(new ArrayList<>());
        //查询出具有该权限的关系
        PolicyDo policy = checkPermissionContext.getPolicy();
        policy.getDefinitions().forEach(definition -> {
            if (definition.getObjectType().equals(checkPermissionContext.getPermissionContext().getObjectType())) {
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
            String stringBuilder = checkPermissionContext.getPermissionContext().getObjectType() +
                    ":" +
                    checkPermissionContext.getPermissionContext().getObject() +
                    "#" +
                    relationHasPermission +
                    "@" +
                    checkPermissionContext.getPermissionContext().getSubjectType() +
                    ":" +
                    checkPermissionContext.getPermissionContext().getSubject();
            System.out.println(stringBuilder);
            List<RelationDo> relationByTriple = databaseGateway.getRelationByTriple(stringBuilder);
            if (relationByTriple.size() > 0) {
                result.set(true);
            }
            countDownLatch.countDown();
        }));
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return result.get();
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
