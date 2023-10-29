package xyz.wanghongtao.rebac.service;

import xyz.wanghongtao.rebac.object.context.CheckPermissionContext;
import xyz.wanghongtao.rebac.object.context.CheckRelationContext;
import xyz.wanghongtao.rebac.object.form.CheckPermissionForm;
import xyz.wanghongtao.rebac.object.form.CheckRelationForm;
import xyz.wanghongtao.rebac.object.runtime.PermissionRuntime;

public interface PermissionService {
    Boolean checkPermission(PermissionRuntime permissionRuntime, CheckPermissionContext checkPermissionContext);

    Boolean checkRelation(CheckRelationContext checkRelationContext);
}
