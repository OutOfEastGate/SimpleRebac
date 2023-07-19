package xyz.wanghongtao.rebac.gateway;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import xyz.wanghongtao.rebac.exception.CustomException;
import xyz.wanghongtao.rebac.exception.ErrorCode;
import xyz.wanghongtao.rebac.object.context.CheckPermissionContext;
import xyz.wanghongtao.rebac.object.context.CheckRelationContext;
import xyz.wanghongtao.rebac.object.dataObject.ModelDo;
import xyz.wanghongtao.rebac.object.dataObject.model.PolicyDo;
import xyz.wanghongtao.rebac.object.form.CheckPermissionForm;
import xyz.wanghongtao.rebac.object.form.CheckRelationForm;
import xyz.wanghongtao.rebac.service.PermissionService;
import xyz.wanghongtao.rebac.service.gateway.DatabaseGateway;
import xyz.wanghongtao.rebac.service.gateway.PermissionServiceGateway;
import xyz.wanghongtao.rebac.util.TripleParserUtil;

/**
 * @author wanghongtao
 * @data 2023/7/19 22:37
 */
@AllArgsConstructor
@Component
public class PermissionGatewayImpl implements PermissionServiceGateway {
    DatabaseGateway databaseGateway;

    PermissionService permissionService;


    @Override
    public Boolean checkPermission(CheckPermissionForm checkPermission) {
        Result result = preCheck(checkPermission.getModelId());

        CheckPermissionContext checkPermissionContext = CheckPermissionContext.builder()
                .triple(checkPermission.getTriple())
                .model(result.modelById())
                .policy(result.policyById())
                .permissionContext(TripleParserUtil.parsePermission(checkPermission.getTriple()))
                .build();
        return permissionService.checkPermission(checkPermissionContext);
    }

    @Override
    public Boolean checkRelation(CheckRelationForm checkRelationForm) {
        Result result = preCheck(checkRelationForm.getModelId());

        CheckRelationContext checkRelationContext = CheckRelationContext.builder()
                .policy(result.policyById())
                .model(result.modelById())
                .triple(checkRelationForm.getTriple())
                .relationContext(TripleParserUtil.parseRelation(checkRelationForm.getTriple()))
                .build();
        return permissionService.checkRelation(checkRelationContext);
    }

    private Result preCheck(Long modelId) {
        //查出Model
        ModelDo modelById = databaseGateway.getModelById(modelId);
        if (modelById == null) {
            throw new CustomException(ErrorCode.Model_NOT_EXIST);
        }
        //查出Policy
        PolicyDo policyById = databaseGateway.getPolicyById(modelById.getPolicyId());
        if (policyById == null) {
            throw new CustomException(ErrorCode.Policy_NOT_EXISTED);
        }
        return new Result(modelById, policyById);
    }

    private record Result(ModelDo modelById, PolicyDo policyById) {
    }
}
