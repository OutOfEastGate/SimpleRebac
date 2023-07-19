package xyz.wanghongtao.rebac.object.context;

import lombok.Builder;
import lombok.Data;
import xyz.wanghongtao.rebac.object.dataObject.ModelDo;
import xyz.wanghongtao.rebac.object.dataObject.model.PolicyDo;

/**
 * @author wanghongtao
 * @data 2023/7/19 22:44
 */
@Builder
@Data
public class CheckRelationContext {
    ModelDo model;

    PolicyDo policy;

    String triple;

    RelationContext relationContext;
}
