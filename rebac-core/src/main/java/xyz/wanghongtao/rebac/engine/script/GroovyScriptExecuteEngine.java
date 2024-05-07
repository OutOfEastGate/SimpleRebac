package xyz.wanghongtao.rebac.engine.script;

import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;

import java.util.HashMap;

/**
 * @author wanghongtao <wanghongtao05@kuaishou.com>
 * Created on 2024-05-05
 */
public class GroovyScriptExecuteEngine {

  private static final GroovyClassLoader classLoader = new GroovyClassLoader();

  public static Object execute(String groovyScript) {

    try {
      Class<GroovyObject> groovyClass = classLoader.parseClass(groovyScript);
      GroovyObject taskScript = groovyClass.getDeclaredConstructor().newInstance();
      Object res = taskScript.invokeMethod("fn", null);
      return res;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
}
