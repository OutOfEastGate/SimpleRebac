package xyz.wanghongtao.rebac.service;

import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import xyz.wanghongtao.rebac.object.dataObject.CheckPermissionRecordDo;
import xyz.wanghongtao.rebac.repository.CheckPermissionRecordRepository;

import java.util.List;

/**
 * @author wanghongtao <wanghongtao05@kuaishou.com>
 * Created on 2024-05-03
 */
@ConditionalOnProperty(name = "wht.back.mockDatabase", havingValue = "false", matchIfMissing = true)
@AllArgsConstructor
@Service
public class CheckPermissionRecordServiceImpl implements CheckPermissionRecordService {
  private final CheckPermissionRecordRepository checkPermissionRecordRepository;
  @Override
  public List<CheckPermissionRecordDo> findAll() {
    return checkPermissionRecordRepository.findAll();
  }

  @Override
  public void save(CheckPermissionRecordDo checkPermissionRecordDo) {
    checkPermissionRecordRepository.save(checkPermissionRecordDo);
  }

  @Override
  public List<CheckPermissionRecordDo> getTop20CheckPermissionLogList() {
    return checkPermissionRecordRepository.findTop20ByOrderByCreateTimestampDesc();
  }
}
