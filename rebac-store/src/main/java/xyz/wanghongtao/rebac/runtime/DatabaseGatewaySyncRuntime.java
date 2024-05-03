package xyz.wanghongtao.rebac.runtime;

import org.springframework.stereotype.Component;
import xyz.wanghongtao.rebac.object.dataObject.CheckPermissionRecordDo;
import xyz.wanghongtao.rebac.service.CheckPermissionRecordService;

/**
 * @author wanghongtao
 * @data 2024/4/28 15:05
 */
@Component
public class DatabaseGatewaySyncRuntime {
  private final CheckPermissionRecordService checkPermissionRecordRepository;

  public DatabaseGatewaySyncRuntime(CheckPermissionRecordService checkPermissionRecordRepository) {
    this.checkPermissionRecordRepository = checkPermissionRecordRepository;
  }


  public void saveCheckPermissionRecord(CheckPermissionRecordDo checkPermissionRecordDo) {
    checkPermissionRecordRepository.save(checkPermissionRecordDo);
  }
}
