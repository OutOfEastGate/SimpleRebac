package xyz.wanghongtao.rebac.engine.relation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;
import xyz.wanghongtao.rebac.exception.CustomException;
import xyz.wanghongtao.rebac.exception.ErrorCode;
import xyz.wanghongtao.rebac.object.context.RelationContext;
import xyz.wanghongtao.rebac.object.dataObject.model.Definition;
import xyz.wanghongtao.rebac.object.dataObject.model.PolicyDo;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author wanghongtao
 * @data 2023/7/23 16:24
 */
@Slf4j
public class RelationEngine {


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
}
