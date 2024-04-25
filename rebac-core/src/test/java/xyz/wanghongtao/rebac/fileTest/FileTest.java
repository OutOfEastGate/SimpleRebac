package xyz.wanghongtao.rebac.fileTest;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import xyz.wanghongtao.rebac.AbstractTest;
import xyz.wanghongtao.rebac.util.JacksonUtils;

import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wanghongtao <wanghongtao05@kuaishou.com>
 * Created on 2024-04-25
 */
@Slf4j
public class FileTest extends AbstractTest {
  @Test
  public void test() {
    Map<String, String> map = new HashMap<>();
    map.put("a", "b");
    String jsonString = JacksonUtils.toJson(map);

    String fileUrl = "/mock/mock.json";
    URL resource = getClass().getResource(fileUrl);

    assert resource != null;
    String path = resource.getPath();
    File file = new File(path);

    try (FileOutputStream fos = new FileOutputStream(file)) {
      byte[] bytes = jsonString.getBytes();
      fos.write(bytes);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
