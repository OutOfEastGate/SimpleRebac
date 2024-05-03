package xyz.wanghongtao.rebac.service.gateway;

import xyz.wanghongtao.rebac.object.dataObject.CheckPermissionRecordDo;

import java.util.List;

public interface DatabaseLogGateway {
  List<CheckPermissionRecordDo> getCheckPermissionLogList();

  List<CheckPermissionRecordDo> getTop20CheckPermissionLogList();

  List<CheckPermissionRecordDo> getCheckPermissionLogListByAppKey(String appKey);
}
