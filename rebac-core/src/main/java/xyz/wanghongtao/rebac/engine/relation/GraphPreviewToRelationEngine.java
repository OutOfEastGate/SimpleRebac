package xyz.wanghongtao.rebac.engine.relation;

import lombok.extern.slf4j.Slf4j;
import xyz.wanghongtao.rebac.object.dataObject.RelationDo;
import xyz.wanghongtao.rebac.object.dataObject.graph.Edge;
import xyz.wanghongtao.rebac.object.dataObject.graph.GraphBase;
import xyz.wanghongtao.rebac.object.dataObject.graph.Node;
import xyz.wanghongtao.rebac.object.runtime.GraphPreviewConvertToRelationRuntime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wanghongtao
 * @date 2024/3/28 16:48
 */
@Slf4j
public class GraphPreviewToRelationEngine {

  public static List<RelationDo> convertGraphPreviewToRelation(List<GraphBase> graphBaseList, GraphPreviewConvertToRelationRuntime runtime) {
    List<RelationDo> relationDoList = new ArrayList<>();

    Map<String, Node> idToNodeMap = new HashMap<>();

    graphBaseList.forEach(graphBase -> {
      if(graphBase instanceof Node node) {
        idToNodeMap.put(node.getId(), node);
      }
    });

    graphBaseList.forEach(graphBase -> {
      if(graphBase instanceof Edge edge) {
        //object
        Edge.Cell source = edge.getSource();
        //subject
        Edge.Cell target = edge.getTarget();
        //relation
        String relation = getEdgeRelation(edge);
        Boolean isPenetrate = getIsPenetrate(edge);

        Node sourceNode = idToNodeMap.get(source.getCell());
        Node targetNode = idToNodeMap.get(target.getCell());

        String sourceObjectType = getObjectTypeFromNode(sourceNode);
        String targetObjectType = getObjectTypeFromNode(targetNode);

        String sourceText = (String)((Map<?, ?>) sourceNode.getAttrs().get("text")).get("text");
        String targetText = (String)((Map<?, ?>) targetNode.getAttrs().get("text")).get("text");

        //document:doc1#reader@user:user1
        String triple = sourceObjectType + ":" + sourceText + "#" + relation + "@" + targetObjectType + ":" + targetText;



        RelationDo relationDo = RelationDo.builder()
          .modelId(runtime.getModelId())
          .object(sourceText)
          .objectType(sourceObjectType)
          .subject(targetText)
          .subjectType(targetObjectType)
          .relation(relation)
          .triple(triple)
          .isPenetrate(isPenetrate != null && isPenetrate)
          .build();
        relationDoList.add(relationDo);
      }
    });
    return relationDoList;
  }

  private static Boolean getIsPenetrate(Edge edge) {
    List<Map<String, Object>> labels = edge.getLabels();
    if (labels == null || labels.isEmpty()) {
      return null;
    }
    Map<String, Object> stringObjectMap = labels.get(0);
    Map<String, Object> attrs = (Map<String, Object>)stringObjectMap.get("attrs");
    Boolean isPenetrate = (Boolean) attrs.get("isPenetrate");
    return isPenetrate;
  }

  private static String getEdgeRelation(Edge edge) {
    List<Map<String, Object>> labels = edge.getLabels();
    if (labels == null || labels.isEmpty()) {
      return null;
    }
    Map<String, Object> stringObjectMap = labels.get(0);
    Map<String, Object> attrs = (Map<String, Object>)stringObjectMap.get("attrs");
    String relation = (String) attrs.get("relation");
    return relation;
  }

  private static String getObjectTypeFromNode(Node sourceNode) {
    Map<String, Object> attrs = sourceNode.getAttrs();
    Map<String, String> objectTypeMap = (Map<String, String>) attrs.get("objectType");
    if(objectTypeMap == null) {
      return null;
    }
    return objectTypeMap.get("objectType");
  }
}
