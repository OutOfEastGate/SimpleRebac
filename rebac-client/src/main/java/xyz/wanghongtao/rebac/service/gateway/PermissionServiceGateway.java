package xyz.wanghongtao.rebac.service.gateway;

import xyz.wanghongtao.rebac.object.form.permission.CheckPermissionForm;
import xyz.wanghongtao.rebac.object.form.relation.CheckRelationForm;

public interface PermissionServiceGateway {

    Boolean checkPermission(CheckPermissionForm checkPermission);

    Boolean checkRelation(CheckRelationForm checkRelationForm);
}
