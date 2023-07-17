package xyz.wanghongtao.rebac.object.dataObject.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author wanghongtao
 * @data 2023/7/17 22:22
 */
@Builder
@Data
public class Definition {

    /**
     * 描述该Definition
     */
    String description;
    /**
     * 实体类型，如 document
     */
    String objectType;

    /**
     * 权限定义
     */
    List<Permission> permissions;

    /**
     * 关系定义
     */
    List<Relation> relations;
}
