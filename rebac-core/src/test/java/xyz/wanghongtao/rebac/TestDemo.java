package xyz.wanghongtao.rebac;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import xyz.wanghongtao.rebac.object.dataObject.graph.Edge;
import xyz.wanghongtao.rebac.object.dataObject.graph.GraphBase;
import xyz.wanghongtao.rebac.object.dataObject.graph.GraphDo;
import xyz.wanghongtao.rebac.object.dataObject.graph.Node;
import xyz.wanghongtao.rebac.util.JacksonUtils;

/**
 * @author wanghongtao
 * @data 2024/3/28 17:43
 */
@Slf4j
public class TestDemo extends AbstractTest {

  @Test
  public void demo() {
    String s = readFileToString("/Demo.json");
    GraphDo graphDo = JacksonUtils.fromJsonStr(s, GraphDo.class);
    assert graphDo != null;
    GraphBase graphBase = graphDo.getCells().get(0);
    GraphBase graphBase1 = graphDo.getCells().get(13);
    log.info(String.valueOf(graphBase instanceof Node));
    log.info(String.valueOf(graphBase1 instanceof Edge));
  }

  @Test
  public void test() {
    String template = "";
    System.out.printf("%s %n", "a");
  }
}
