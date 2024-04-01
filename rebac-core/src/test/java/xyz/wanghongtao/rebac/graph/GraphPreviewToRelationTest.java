package xyz.wanghongtao.rebac.graph;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import xyz.wanghongtao.rebac.AbstractTest;
import xyz.wanghongtao.rebac.engine.relation.GraphPreviewToRelationEngine;
import xyz.wanghongtao.rebac.object.dataObject.RelationDo;
import xyz.wanghongtao.rebac.object.dataObject.graph.GraphBase;
import xyz.wanghongtao.rebac.object.runtime.GraphPreviewConvertToRelationRuntime;
import xyz.wanghongtao.rebac.util.JacksonUtils;

import java.util.List;

/**
 * @author wanghongtao
 * @date 2024/3/28 20:38
 */
@Slf4j
public class GraphPreviewToRelationTest extends AbstractTest {
  @Test
  public void convertGraphPreviewToRelation() {
    var cells = readFileToString("/graph/GraphPreview_v1.json");
    List<GraphBase> list = JacksonUtils.fromJsonStr(cells, new TypeReference<>() {});
    GraphPreviewConvertToRelationRuntime mockRuntime = new GraphPreviewConvertToRelationRuntime();
    mockRuntime.setModelId(1L);
    assert list != null;
    List<RelationDo> res = GraphPreviewToRelationEngine.convertGraphPreviewToRelation(list, mockRuntime);
    log.info(JacksonUtils.toJson(res));
  }
}
