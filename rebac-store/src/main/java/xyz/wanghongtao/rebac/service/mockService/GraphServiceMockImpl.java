package xyz.wanghongtao.rebac.service.mockService;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import xyz.wanghongtao.rebac.engine.relation.GraphPreviewToRelationEngine;
import xyz.wanghongtao.rebac.object.dataObject.RelationDo;
import xyz.wanghongtao.rebac.object.dataObject.graph.GraphBase;
import xyz.wanghongtao.rebac.object.dataObject.graph.GraphDo;
import xyz.wanghongtao.rebac.object.runtime.GraphPreviewConvertToRelationRuntime;
import xyz.wanghongtao.rebac.service.GraphService;
import xyz.wanghongtao.rebac.util.JacksonUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wanghongtao <wanghongtao05@kuaishou.com>
 * Created on 2024-04-24
 */
@Slf4j
@ConditionalOnProperty(name = "wht.back.mockDatabase", havingValue = "true", matchIfMissing = true)
@Service
public class GraphServiceMockImpl extends AbstractMockService implements GraphService {
  Map<String, GraphDo> graphDoMap = new HashMap<>();
  private final RelationServiceMockImpl relationService;

  private final String mockDataUrl = "/mockDatabase/mockGraphDatabase.json";

  {
    graphDoMap = JacksonUtils.fromJsonStr(readFileToString(mockDataUrl), new TypeReference<>() {});
  }

  private void updateDatabase() {
    writeStringToFile(JacksonUtils.toJson(graphDoMap), mockDataUrl);
  }

  public GraphServiceMockImpl(RelationServiceMockImpl relationServiceMock) {
    this.relationService = relationServiceMock;
  }


  @Override
  public GraphDo getGraph(String id) {
    return graphDoMap.get(id);
  }

  @Override
  public void saveGraph(GraphDo graphDo) {
    List<GraphBase> graphBaseList = graphDo.getCells();
    GraphPreviewConvertToRelationRuntime graphPreviewConvertToRelationRuntime = GraphPreviewConvertToRelationRuntime.builder()
      .modelId(Long.valueOf(graphDo.getId()))
      .build();
    log.info("#图转化为关系模型-开始");
    List<RelationDo> relationDos = GraphPreviewToRelationEngine.convertGraphPreviewToRelation(graphBaseList, graphPreviewConvertToRelationRuntime);
    log.info("#图转化为关系模型-结束，关系个数：{}", relationDos.size());
    log.info("#存储关系/图-开始");
    relationService.deleteRelation(Long.valueOf(graphDo.getId()));
    relationService.batchAddRelation(relationDos);
    log.info("#存储关系-结束");
    graphDoMap.put(graphDo.getId(), graphDo);
    log.info("#存储图-结束");
    updateDatabase();
  }
}
