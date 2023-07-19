package xyz.wanghongtao.rebac.service.gateway;

import xyz.wanghongtao.rebac.object.form.CheckPermissionForm;
import xyz.wanghongtao.rebac.object.form.CheckRelationForm;

public interface PermissionServiceGateway {

    Boolean checkPermission(CheckPermissionForm checkPermission);

    Boolean checkRelation(CheckRelationForm checkRelationForm);
}
