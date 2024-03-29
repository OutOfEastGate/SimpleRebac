package xyz.wanghongtao.rebac.object.form.graph;

import lombok.Data;
import xyz.wanghongtao.rebac.object.dataObject.graph.GraphDo;

/**
 * @author wanghongtao
 * @data 2024/3/27 22:44
 */
@Data
public class AddGraphParam {
  String modelId;

  GraphDo graphDo;
}
