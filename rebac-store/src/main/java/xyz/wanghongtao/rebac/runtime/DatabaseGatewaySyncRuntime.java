package xyz.wanghongtao.rebac.runtime;

import org.springframework.stereotype.Component;
import xyz.wanghongtao.rebac.object.dataObject.CheckPermissionRecordDo;
import xyz.wanghongtao.rebac.repository.CheckPermissionRecordRepository;

/**
 * @author wanghongtao
 * @data 2024/4/28 15:05
 */
@Component
public class DatabaseGatewaySyncRuntime {
  private final CheckPermissionRecordRepository checkPermissionRecordRepository;

  public DatabaseGatewaySyncRuntime(CheckPermissionRecordRepository checkPermissionRecordRepository) {
    this.checkPermissionRecordRepository = checkPermissionRecordRepository;
  }

  public void saveCheckPermissionRecord(CheckPermissionRecordDo checkPermissionRecordDo) {
    checkPermissionRecordRepository.save(checkPermissionRecordDo);
  }
}
