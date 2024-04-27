package xyz.wanghongtao.rebac.object.context;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import xyz.wanghongtao.rebac.object.dataObject.ModelDo;
import xyz.wanghongtao.rebac.object.dataObject.model.PolicyDo;
import xyz.wanghongtao.rebac.service.engine.formula.Expression;

/**
 * @author wanghongtao
 * @data 2023/7/19 23:19
 */
@Setter
@Getter
@Builder
@Data
public class CheckPermissionContext {
    ModelDo model;

    PolicyDo policy;

    String triple;

    PermissionContext permissionContext;

    String relationHasPermission;

    Expression expressionComputed;
}
