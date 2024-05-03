package xyz.wanghongtao.rebac.service.mockService;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import xyz.wanghongtao.rebac.object.dataObject.CheckPermissionRecordDo;
import xyz.wanghongtao.rebac.service.CheckPermissionRecordService;
import xyz.wanghongtao.rebac.util.IdUtil;
import xyz.wanghongtao.rebac.util.JacksonUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wanghongtao <wanghongtao05@kuaishou.com>
 * Created on 2024-05-03
 */
@ConditionalOnProperty("wht.back.mockDatabase")
@Service
public class CheckPermissionRecordServiceMockImpl extends AbstractMockService implements CheckPermissionRecordService {
  private Map<String, CheckPermissionRecordDo> checkPermissionRecordDoMap = new HashMap<>();

  private final String mockDataUrl = "/mockDatabase/mockCheckPermissionRecordDatabase.json";

  {
    checkPermissionRecordDoMap = JacksonUtils.fromJsonStr(readFileToString(mockDataUrl), new TypeReference<>() {});
  }

  private void updateDatabase() {
    writeStringToFile(JacksonUtils.toJson(checkPermissionRecordDoMap), mockDataUrl);
  }
  @Override
  public List<CheckPermissionRecordDo> findAll() {
    return checkPermissionRecordDoMap.values().stream().toList();
  }

  @Override
  public void save(CheckPermissionRecordDo checkPermissionRecordDo) {
    String id = IdUtil.generateId();
    checkPermissionRecordDo.setId(id);
    checkPermissionRecordDoMap.put(id, checkPermissionRecordDo);
    updateDatabase();
  }

  @Override
  public List<CheckPermissionRecordDo> getTop20CheckPermissionLogList() {
    return checkPermissionRecordDoMap.values().stream().toList();
  }
}
