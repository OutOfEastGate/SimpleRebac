package xyz.wanghongtao.rebac.object.dataObject.graph;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * @author wanghongtao
 * @data 2024/3/28 17:16
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
public class Edge extends GraphBase {
  List<Map<String, Object>> labels;
  List<Object> vertices;
  Cell source;
  Cell target;
  String text;

  @NoArgsConstructor
  @AllArgsConstructor
  @Data
  public static class Cell {
    @JsonProperty("cell")
    String cell;
    String port;
  }
}
