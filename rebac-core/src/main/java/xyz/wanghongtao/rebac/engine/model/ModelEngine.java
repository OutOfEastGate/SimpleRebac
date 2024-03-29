package xyz.wanghongtao.rebac.engine.model;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;
import xyz.wanghongtao.rebac.exception.CustomException;
import xyz.wanghongtao.rebac.object.form.AddModelForm;

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
        log.info("#预检查Policy开始: {}", addModelForm.getName());


        Map<String, Map<String, List<String>>> permissionMap = new HashMap<>();
        Map<String, Map<String, String>> relationMap = new HashMap<>();

        StringBuilder errorInfo = new StringBuilder();

        addModelForm.getPolicy().getDefinitions().forEach(definition -> {
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
                    errorInfo.append("Relation定义错误: ").append(subjectType).append("不存在; ");
                }
            });
        });
        //检查关联关系
        permissionMap.forEach((objectType, permissionMaps) -> {
            if (permissionMaps !=null && permissionMaps.size() > 0) {
                permissionMaps.forEach((permission, relationsCanAccess) -> {
                    relationsCanAccess.forEach(relationCanAccess -> {
                        if (!relationMap.get(objectType).containsKey(relationCanAccess)) {
                            errorInfo.append("Permission定义错误: ").append(relationCanAccess).append("不存在; ");
                        }
                    });
                });
            }
        });
        stopWatch.stop();
        if (!errorInfo.isEmpty()) {
            throw new CustomException(errorInfo.toString());
        }
        log.info("#预检查Policy结束: 耗时{}", stopWatch.getTotalTimeSeconds());
    }
}
