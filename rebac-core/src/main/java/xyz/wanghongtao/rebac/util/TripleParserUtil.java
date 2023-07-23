package xyz.wanghongtao.rebac.util;

import xyz.wanghongtao.rebac.object.context.PermissionContext;
import xyz.wanghongtao.rebac.object.context.RelationContext;

/**
 * @author wanghongtao
 * @data 2023/7/19 22:46
 */
public class TripleParserUtil {
    public static RelationContext parseRelation(String triple) {
        //解析三元组
        String[] parts = triple.split("#|@|:");

        String objectType = parts[0];
        String object = parts[1];
        String relation = parts[2];
        String subjectType = parts[3];
        String subject = parts[4];

        return RelationContext.builder()
                .objectType(objectType)
                .object(object)
                .relation(relation)
                .subject(subject)
                .subjectType(subjectType)
                .triple(triple)
                .build();
    }

    public static PermissionContext parsePermission(String triple) {
        //解析三元组
        String[] parts = triple.split("#|@|:");

        String objectType = parts[0];
        String object = parts[1];
        String permission = parts[2];
        String subjectType = parts[3];
        String subject = parts[4];

        return PermissionContext.builder()
                .objectType(objectType)
                .object(object)
                .permission(permission)
                .subject(subject)
                .subjectType(subjectType)
                .build();
    }
}
