package xyz.wanghongtao.rebac.object.dataObject.graph;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author wanghongtao
 * @data 2024/3/28 17:20
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
public class Node extends GraphBase {
  private Position position;
  private Size size;
  private Ports ports;
  private Boolean visible;
  protected Map<String,Object> attrs;
}
