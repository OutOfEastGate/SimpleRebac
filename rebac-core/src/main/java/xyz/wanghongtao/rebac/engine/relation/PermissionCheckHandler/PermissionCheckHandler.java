package xyz.wanghongtao.rebac.engine.relation.PermissionCheckHandler;

import xyz.wanghongtao.rebac.object.context.CheckPermissionContext;
import xyz.wanghongtao.rebac.object.runtime.PermissionRuntime;
import xyz.wanghongtao.rebac.service.engine.formula.Expression;

public interface PermissionCheckHandler {
  Boolean handle(CheckPermissionContext checkPermissionContext, PermissionRuntime permissionRuntime, Expression expression);
}
