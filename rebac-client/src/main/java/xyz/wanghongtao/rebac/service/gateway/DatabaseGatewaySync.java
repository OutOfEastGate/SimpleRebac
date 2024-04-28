package xyz.wanghongtao.rebac.service.gateway;

import xyz.wanghongtao.rebac.object.context.CheckPermissionContext;
import xyz.wanghongtao.rebac.object.result.CheckPermissionResult;

public interface DatabaseGatewaySync {
  void saveCheckPermissionRecord(CheckPermissionResult checkPermissionResult, CheckPermissionContext checkPermissionContext);
}
