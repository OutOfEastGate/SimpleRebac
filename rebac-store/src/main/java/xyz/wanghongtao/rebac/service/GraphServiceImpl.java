package xyz.wanghongtao.rebac.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.wanghongtao.rebac.engine.relation.GraphPreviewToRelationEngine;
import xyz.wanghongtao.rebac.object.dataObject.RelationDo;
import xyz.wanghongtao.rebac.object.dataObject.graph.GraphBase;
import xyz.wanghongtao.rebac.object.dataObject.graph.GraphDo;
import xyz.wanghongtao.rebac.object.runtime.GraphPreviewConvertToRelationRuntime;
import xyz.wanghongtao.rebac.repository.GraphRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author wanghongtao
 * @data 2024/3/27 22:38
 */
@Slf4j
@AllArgsConstructor
@Service
public class GraphServiceImpl implements GraphService {
  private final GraphRepository graphRepository;
  private final RelationService relationService;


  @Override
  public GraphDo getGraph(String id) {
    Optional<GraphDo> byId = graphRepository.findById(id);
    return byId.orElse(null);
  }

  @Override
  @Transactional
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
    graphRepository.save(graphDo);
    log.info("#存储图-结束");
  }
}
