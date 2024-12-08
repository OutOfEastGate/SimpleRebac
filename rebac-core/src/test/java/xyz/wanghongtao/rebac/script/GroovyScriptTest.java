package xyz.wanghongtao.rebac.script;

import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import xyz.wanghongtao.rebac.object.viewObject.system.SystemInfoDto;
import xyz.wanghongtao.rebac.service.SystemService;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author wanghongtao <wanghongtao05@kuaishou.com>
 * Created on 2024-05-05
 */
@Slf4j
public class GroovyScriptTest {

  String groovyScript = "import xyz.wanghongtao.rebac.engine.script.GroovyScriptBase\n" +
    "import xyz.wanghongtao.rebac.service.gateway.DatabaseGateway\n" +
    "import xyz.wanghongtao.rebac.util.SpringContextUtils\n" +
    "\n" +
    "\n" +
    "class groovyTest extends GroovyScriptBase {\n" +
    "\n" +
    "  def fn() {\n" +
    "    return $Params;\n" +
    "  }\n" +
    "\n" +
    "}";
  @Test
  public void testExecute() {
    GroovyClassLoader classLoader = new GroovyClassLoader();
    try {
      long start = System.currentTimeMillis();
      Class<?> groovyClass = classLoader.parseClass(groovyScript);
      Object taskScriptObject =  groovyClass.getDeclaredConstructor().newInstance();
      GroovyObject taskScript;
      long end = System.currentTimeMillis();
      System.out.println("编译耗时" + (end - start) + "ms");
      if (taskScriptObject instanceof GroovyObject groovyObject) {
        taskScript = groovyObject;
      } else {
        log.error("fail");
        return;
      }
      Map<String, Field> fields = Stream.of(groovyClass.getSuperclass().getDeclaredFields()).collect(Collectors.toMap(Field::getName, Function.identity()));
      SystemService systemService = () -> {
        SystemInfoDto systemInfoDto = new SystemInfoDto();
        systemInfoDto.setCpuNum(12);
        return systemInfoDto;
      };
      fields.forEach((key, value) -> {
        try {
          if (key.equals("systemService")) {
            value.set(taskScript, systemService);
          } else if (key.equals("$Params")) {
            value.set(taskScript, Map.of("key", "value"));
          }
        } catch (IllegalAccessException ignored) {
        }

      });
      Object res = taskScript.invokeMethod("fn", null);
      System.out.println(res);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
