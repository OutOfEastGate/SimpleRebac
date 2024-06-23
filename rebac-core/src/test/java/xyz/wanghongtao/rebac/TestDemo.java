package xyz.wanghongtao.rebac;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import xyz.wanghongtao.rebac.object.dataObject.UserDo;
import xyz.wanghongtao.rebac.object.dataObject.graph.Edge;
import xyz.wanghongtao.rebac.object.dataObject.graph.GraphBase;
import xyz.wanghongtao.rebac.object.dataObject.graph.GraphDo;
import xyz.wanghongtao.rebac.object.dataObject.graph.Node;
import xyz.wanghongtao.rebac.util.JacksonUtils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

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
    Map<String, UserDo> userDoMap = new HashMap<>();
    UserDo wht = UserDo.builder()
      .username("wht")
      .password("123")
      .build();
    userDoMap.put("wht", wht);
    Map<String, UserDo> newMap = JacksonUtils.fromJsonStr(JacksonUtils.toJson(userDoMap), new TypeReference<>() {});
    UserDo userDo = newMap.get("wht");
    userDo.setUsername("wht2");
    log.info(JacksonUtils.toJson(wht));
    log.info(JacksonUtils.toJson(userDo));
  }

  @Test
  public void test2() {
    Double a = 1.9;
    System.out.println(Double.valueOf(String.valueOf(a)));
  }


}
