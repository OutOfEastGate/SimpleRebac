package xyz.wanghongtao.rebac.object.dataObject.graph;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * @author wanghongtao
 * @data 2024/3/27 22:33
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Document(collection = "graph")
public class GraphDo {
  /**
   * 唯一标识
   */
  @Id
  private String id;

  private List<Object> nodes;

  private List<Object> edges;

  private List<GraphBase> cells;
}
