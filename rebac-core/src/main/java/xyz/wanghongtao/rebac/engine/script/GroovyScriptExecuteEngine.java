package xyz.wanghongtao.rebac.engine.script;

import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;
import lombok.extern.slf4j.Slf4j;
import xyz.wanghongtao.rebac.exception.CustomException;
import xyz.wanghongtao.rebac.object.viewObject.system.SystemInfoDto;
import xyz.wanghongtao.rebac.service.SystemService;
import xyz.wanghongtao.rebac.util.SpringContextUtils;

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
public class GroovyScriptExecuteEngine {

  private static final GroovyClassLoader classLoader = new GroovyClassLoader();

  public static Object execute(String groovyScript, Map<String, Object> params) {
    try {
      Class<?> groovyClass = classLoader.parseClass(groovyScript);
      Object taskScriptObject =  groovyClass.getDeclaredConstructor().newInstance();
      GroovyObject taskScript;
      if (taskScriptObject instanceof GroovyObject groovyObject) {
        taskScript = groovyObject;
      } else {
        log.error("fail");
        return null;
      }
      Map<String, Field> fields = Stream.of(groovyClass.getSuperclass().getDeclaredFields()).collect(Collectors.toMap(Field::getName, Function.identity()));
      SystemService systemService = SpringContextUtils.getBean(SystemService.class);
      fields.forEach((key, value) -> {
        try {
          if (key.equals("systemService")) {
            value.set(taskScript, systemService);
          } else if (key.equals("$Params")) {
            value.set(taskScript, params);
          }
        } catch (IllegalAccessException ignored) {
        }
      });
      return taskScript.invokeMethod("fn", null);
    } catch (Exception e) {
      e.printStackTrace();
      throw new CustomException("400", e.getMessage());
    }
  }
}
