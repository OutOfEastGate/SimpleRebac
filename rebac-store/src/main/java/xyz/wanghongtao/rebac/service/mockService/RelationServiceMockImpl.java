package xyz.wanghongtao.rebac.service.mockService;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import xyz.wanghongtao.rebac.object.dataObject.RelationDo;
import xyz.wanghongtao.rebac.service.RelationService;
import xyz.wanghongtao.rebac.util.IdUtil;
import xyz.wanghongtao.rebac.util.JacksonUtils;

import java.util.*;

/**
 * @author wanghongtao <wanghongtao05@kuaishou.com>
 * Created on 2024-04-24
 */
@ConditionalOnProperty(name = "wht.back.mockDatabase", havingValue = "true", matchIfMissing = true)
@Service
public class RelationServiceMockImpl extends AbstractMockService implements RelationService {
  Map<Long, RelationDo> relationDoMap = new HashMap<>();

  private final String mockDataUrl = "/mockDatabase/mockRelationDatabase.json";

  {
    relationDoMap = JacksonUtils.fromJsonStr(readFileToString(mockDataUrl), new TypeReference<>() {});
  }

  private void updateDatabase() {
    writeStringToFile(JacksonUtils.toJson(relationDoMap), mockDataUrl);
  }
  @Override
  public RelationDo addRelation(RelationDo relationDo) {
    relationDo.setId(IdUtil.generateLongId());
    RelationDo put = relationDoMap.put(relationDo.getId(), relationDo);
    updateDatabase();
    return put;
  }

  @Override
  public void batchAddRelation(List<RelationDo> relationDoList) {
    for (RelationDo relationDo : relationDoList) {
      relationDo.setId(IdUtil.generateLongId());
      relationDoMap.put(relationDo.getId(), relationDo);
    }
    updateDatabase();
  }

  @Override
  public List<RelationDo> getByTriple(String triple) {
    List<RelationDo> relationDoList = new ArrayList<>();
    relationDoMap.values().forEach(relationDo -> {
      if(relationDo.getTriple().equals(triple)) {
        relationDoList.add(relationDo);
      }
    });
    return relationDoList;
  }

  @Override
  public List<RelationDo> getRelationByModelId(Long modelId) {
    List<RelationDo> relationDoList = new ArrayList<>();
    relationDoMap.values().forEach(relationDo -> {
      if (Objects.equals(relationDo.getModelId(), modelId)) {
        relationDoList.add(relationDo);
      }
    });
    return relationDoList;
  }

  @Override
  public void deleteRelation(Long modelId, Long id) {
    Set<Long> idList = new HashSet<>();
    relationDoMap.values().forEach(relationDo -> {
      if (Objects.equals(relationDo.getModelId(), modelId) && Objects.equals(relationDo.getId(), id)) {
        idList.add(relationDo.getId());
      }
    });
    idList.forEach(item -> relationDoMap.remove(item));
    updateDatabase();
  }

  @Override
  public int deleteRelation(Long modelId) {
    Set<Long> idList = new HashSet<>();
    relationDoMap.values().forEach(relationDo -> {
      if (Objects.equals(relationDo.getModelId(), modelId)) {
        idList.add(relationDo.getId());
      }
    });
    idList.forEach(item -> relationDoMap.remove(item));
    updateDatabase();
    return 0;
  }
}
