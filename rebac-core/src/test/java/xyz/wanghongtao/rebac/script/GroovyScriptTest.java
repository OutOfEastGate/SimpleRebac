package xyz.wanghongtao.rebac.script;

import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;
import org.junit.Test;

import java.util.HashMap;

/**
 * @author wanghongtao <wanghongtao05@kuaishou.com>
 * Created on 2024-05-05
 */
public class GroovyScriptTest {

  String groovyScript = "def fn() {\n" +
    "    def lex = xyz.wanghongtao.rebac.engine.formula.FormulaLexer.lex(\"a and b\")\n" +
    "    Map<String,String> map = new HashMap<>();\n" +
    "    map.put(\"a\", \"b\")\n" +
    "    def currentTimeMillis = System.currentTimeMillis()\n" +
    "    def date = new Date(currentTimeMillis)\n" +
    "    map.put(\"currentTime\", date.dateTimeString)\n" +
    "    return lex\n" +
    "  }";
  @Test
  public void testExecute() {
    GroovyClassLoader classLoader = new GroovyClassLoader();
    try {
      Class<GroovyObject> groovyClass = classLoader.parseClass(groovyScript);
      GroovyObject taskScript = groovyClass.getDeclaredConstructor().newInstance();
      Object res = taskScript.invokeMethod("fn", null);
      System.out.println(res);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
