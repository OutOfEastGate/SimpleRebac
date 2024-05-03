package xyz.wanghongtao.rebac.service;

import xyz.wanghongtao.rebac.object.dataObject.CheckPermissionRecordDo;

import java.util.List;

/**
 * @author wanghongtao <wanghongtao05@kuaishou.com>
 * Created on 2024-05-03
 */
public interface CheckPermissionRecordService {

  List<CheckPermissionRecordDo> findAll();

  void save(CheckPermissionRecordDo checkPermissionRecordDo);

  List<CheckPermissionRecordDo> getTop20CheckPermissionLogList();
}
