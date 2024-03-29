package xyz.wanghongtao.rebac;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * @author wanghongtao
 * @data 2024/3/28 17:38
 */
public abstract class AbstractTest {

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
}
