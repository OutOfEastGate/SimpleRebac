package xyz.wanghongtao.rebac.service.gateway;

import xyz.wanghongtao.rebac.object.form.permission.CheckPermissionForm;
import xyz.wanghongtao.rebac.object.form.relation.CheckRelationForm;
import xyz.wanghongtao.rebac.object.result.CheckPermissionResult;

public interface PermissionServiceGateway {

  CheckPermissionResult checkPermission(CheckPermissionForm checkPermission);

    Boolean checkRelation(CheckRelationForm checkRelationForm);
}
