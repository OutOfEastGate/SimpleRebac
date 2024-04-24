package xyz.wanghongtao.rebac.graph;

import org.springframework.stereotype.Component;
import xyz.wanghongtao.rebac.object.dataObject.RelationDo;
import xyz.wanghongtao.rebac.object.viewObject.graph.GraphVo;
import xyz.wanghongtao.rebac.object.viewObject.graph.LinkVo;
import xyz.wanghongtao.rebac.object.viewObject.graph.NodeVo;

import java.util.*;

/**
 * @author wanghongtao
 * @data 2023/7/23 21:30
 */
public class FrontGraphTransform {


    /**
     * 将关系转化为前端展示图模型
     * @param relationByModelId 数据库查询数据
     * @return 展示数据
     */
    public static GraphVo transform(List<RelationDo> relationByModelId) {
        GraphVo graphVo = new GraphVo();

        Map<String, NodeVo> objects = new HashMap<>();
        relationByModelId.forEach(relationDo -> {
            objects.put(relationDo.getObject(), null);
            objects.put(relationDo.getSubject(), null);
        });

        List<NodeVo> nodes = new ArrayList<>();
        objects.forEach((object, node) -> {
            NodeVo nodeVo = NodeVo.builder()
              .id(object)
              .attrs(getNodeAttrs())
              .label(object)
              .build();
            nodes.add(nodeVo);
            objects.put(object, nodeVo);
        });

        List<LinkVo> links = new ArrayList<>();

        relationByModelId.forEach(relationDo -> {
            LinkVo linkVo = LinkVo.builder().build();
            linkVo.setSource(objects.get(relationDo.getObject()).getId());
            linkVo.setTarget(objects.get(relationDo.getSubject()).getId());
            linkVo.setText(relationDo.getRelation());
            linkVo.setLabel(relationDo.getRelation());
            linkVo.setAttrs(getEdgeAttrs());
            links.add(linkVo);
        });

        graphVo.setNodes(nodes);
        graphVo.setEdges(links);
        return graphVo;
    }

  private static Map<String, Object> getEdgeAttrs() {
    Map<String, Object> attrs = new HashMap<>();
    Map<String, Object> line = new HashMap<>();
    line.put("stroke", "#8f8f8f");
    line.put("strokeWidth", 1);
    attrs.put("line", line);
    return attrs;
  }

  private static Map<String, Object> getNodeAttrs() {
    Map<String, Object> attrs = new HashMap<>();
    Map<String, Object> body = new HashMap<>();
    body.put("stroke", "#8f8f8f");
    body.put("strokeWidth", 1);
    body.put("fill", "#fff");
    body.put("rx", 6);
    body.put("ry", 6);
    attrs.put("body", body);
    return attrs;
  }
}
