package xyz.wanghongtao.rebac.object.dataObject.model;

import lombok.Builder;
import lombok.Data;

/**
 * @author wanghongtao
 * @data 2023/7/17 22:24
 */
@Builder
@Data
public class Relation {
    /**
     * 定义何种关系，如 writer、reader
     */
    String relation;

    /**
     * 定义关联对象的命名空间（暂定不实现）
     */
    String subjectNamespaceCode;

    /**
     * 定义关联对象类型，如 document、book
     */
    String subjectType;
}
