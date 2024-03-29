/**
  * Copyright 2024 json.cn
  */
package xyz.wanghongtao.rebac.object.dataObject.graph;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Auto-generated: 2024-03-28 16:56:8
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/
 */

@JsonSubTypes({
  @JsonSubTypes.Type(value = Node.class, name = "Node"),
  @JsonSubTypes.Type(value = Edge.class, name = "Edge")
})
@NoArgsConstructor
@Data
public class GraphBase {

  @JsonProperty("shape")
  protected String shape;

  protected String id;
  protected Integer weight;
  @JsonProperty("zIndex")
  protected Integer zIndex;

  @NoArgsConstructor
  @AllArgsConstructor
  @Data
  static class Size {
      Integer width;
      Integer height;
  }

  @NoArgsConstructor
  @AllArgsConstructor
  @Data
  static class Position {
    Integer x;
    Integer y;
  }
  @NoArgsConstructor
  @AllArgsConstructor
  @Data
  static class Ports {
    Map<String, Object> groups;
    List<Object> items;
  }

  @JsonCreator
  public static GraphBase createInstance(@JsonProperty("shape") String shape) {
    if (StringUtils.isEmpty(shape) || shape.contains("node")) {
      Node node = new Node();
      node.setShape(shape);
      return node;
    } else if (shape.contains("edge")) {
      Edge edge = new Edge();
      edge.setShape(shape);
      return edge;
    } else {
      Node node = new Node();
      node.setShape(shape);
      return node;
    }
  }

}
