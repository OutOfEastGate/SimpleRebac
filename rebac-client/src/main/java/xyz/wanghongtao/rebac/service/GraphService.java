package xyz.wanghongtao.rebac.service;

import xyz.wanghongtao.rebac.object.dataObject.graph.GraphDo;

public interface GraphService {
  GraphDo getGraph(String id);

  void saveGraph(GraphDo graphDo);
}
