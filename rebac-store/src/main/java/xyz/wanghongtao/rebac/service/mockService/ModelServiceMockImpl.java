package xyz.wanghongtao.rebac.service.mockService;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import xyz.wanghongtao.rebac.object.dataObject.ModelDo;
import xyz.wanghongtao.rebac.object.dataObject.model.PolicyDo;
import xyz.wanghongtao.rebac.service.ModelService;
import xyz.wanghongtao.rebac.util.JacksonUtils;

import java.util.*;

/**
 * @author wanghongtao <wanghongtao05@kuaishou.com>
 * Created on 2024-04-24
 */
@ConditionalOnProperty("wht.back.mockDatabase")
@Service
public class ModelServiceMockImpl extends AbstractMockService implements ModelService {
  Map<String, PolicyDo> policyDoMap = new HashMap<>();
  Map<Long, ModelDo> modelDoMap = new HashMap<>();

  private final String mockModelDataUrl = "/mockDatabase/mockModelDatabase.json";
  private final String mockPolicyDataUrl = "/mockDatabase/mockPolicyDatabase.json";
  {
    policyDoMap = JacksonUtils.fromJsonStr(readFileToString(mockPolicyDataUrl), new TypeReference<>() {});
    modelDoMap = JacksonUtils.fromJsonStr(readFileToString(mockModelDataUrl), new TypeReference<>() {});
  }

  private void updateDatabase() {
    writeStringToFile(JacksonUtils.toJson(modelDoMap), mockModelDataUrl);
    writeStringToFile(JacksonUtils.toJson(policyDoMap), mockPolicyDataUrl);
  }
  long id = 1;
  @Override
  public void addModel(PolicyDo policyDo, ModelDo modelDo) {
    String uuid = UUID.randomUUID().toString();
    policyDo.setId(uuid);
    policyDoMap.put(uuid, policyDo);
    modelDo.setId(id);
    modelDo.setPolicyId(uuid);
    modelDoMap.put(id, modelDo);
    id++;
    updateDatabase();
  }

  @Override
  public void updatePolicy(PolicyDo policyDo) {
    policyDoMap.put(policyDo.getId(),policyDo);
    updateDatabase();
  }

  @Override
  public List<ModelDo> getAllModelByStoreId(Long storeId) {
    List<ModelDo> modelDos = new ArrayList<>();
    modelDoMap.values().forEach(modelDo -> {
      if (Objects.equals(storeId, modelDo.getStoreId())) {
        modelDos.add(modelDo);
      }
    });
    return modelDos;
  }

  @Override
  public PolicyDo getPolicyById(String id) {
    return policyDoMap.get(id);
  }

  @Override
  public ModelDo getModelById(Long id) {
    return modelDoMap.get(id);
  }

  @Override
  public void deleteModelById(Long id) {
    modelDoMap.remove(id);
    updateDatabase();
  }
}
