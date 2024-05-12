package groovy

import xyz.wanghongtao.rebac.engine.script.GroovyScriptBase

import java.nio.charset.StandardCharsets


class groovyTest extends GroovyScriptBase {

  def fn() {
    System.currentTimeMillis()
    InputStreamReader inputStreamReader = new InputStreamReader(getClass().getResourceAsStream("/mockDatabase/mockKeyDatabase.json"), StandardCharsets.UTF_8);
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
    return xyz.wanghongtao.rebac.util.JacksonUtils.fromJsonStr(stringBuilder.toString(), Object.class);

  }

}
