package xyz.wanghongtao.rebac.service.mockService;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * @author wanghongtao <wanghongtao05@kuaishou.com>
 * Created on 2024-04-25
 */
public abstract class AbstractMockService {

  protected InputStream locate(String fileUrl) {
    InputStream inputStream = getClass().getResourceAsStream(fileUrl);
    if (inputStream == null) {
      throw new RuntimeException("未找到文件：" + fileUrl);
    }
    return inputStream;
  }

  protected String readFileToString(String fileUrl) {
    InputStreamReader inputStreamReader = new InputStreamReader(locate(fileUrl), StandardCharsets.UTF_8);
    int len = 0;
    StringBuilder stringBuilder = new StringBuilder();
    try {
      while ((len = inputStreamReader.read()) != -1) {
        stringBuilder.append((char) len);
      }
      inputStreamReader.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return stringBuilder.toString();
  }

  protected void writeStringToFile(String jsonString, String fileUrl) {
    URL resource = getClass().getResource(fileUrl);
    assert resource != null;
    String path = resource.getPath();
    // 创建一个 BufferedWriter 对象
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
      // 将 JSON 字符串写入文件
      writer.write(jsonString);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
