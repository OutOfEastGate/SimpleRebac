package xyz.wanghongtao.rebac.service;

import xyz.wanghongtao.rebac.object.context.CheckPermissionContext;
import xyz.wanghongtao.rebac.object.context.CheckRelationContext;
import xyz.wanghongtao.rebac.object.result.CheckPermissionResult;
import xyz.wanghongtao.rebac.object.runtime.PermissionRuntime;

public interface PermissionService {
  CheckPermissionResult checkPermission(PermissionRuntime permissionRuntime, CheckPermissionContext checkPermissionContext);

    Boolean checkRelation(CheckRelationContext checkRelationContext);
}
