package xyz.wanghongtao.rebac.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.wanghongtao.rebac.object.dataObject.graph.GraphDo;
import xyz.wanghongtao.rebac.repository.GraphRepository;

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


  @Override
  public GraphDo getGraph(String id) {
    Optional<GraphDo> byId = graphRepository.findById(id);
    return byId.orElse(null);
  }

  @Override
  public void saveGraph(GraphDo graphDo) {
    graphRepository.save(graphDo);
  }
}
