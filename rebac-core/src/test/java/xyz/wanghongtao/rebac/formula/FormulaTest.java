package xyz.wanghongtao.rebac.formula;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import xyz.wanghongtao.rebac.engine.formula.FormulaParser;
import xyz.wanghongtao.rebac.engine.model.ModelEngine;
import xyz.wanghongtao.rebac.object.context.PermissionContext;
import xyz.wanghongtao.rebac.service.engine.formula.Expression;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wanghongtao <wanghongtao05@kuaishou.com>
 * Created on 2024-04-27
 */
@Slf4j
public class FormulaTest {

  @Test
  public void testAnd() {
    String express = "a and b and c";
    FormulaParser formulaParser = new FormulaParser(express);
    Expression expression = formulaParser.parseExpression();
    StringBuilder errorInfo = new StringBuilder();
    Map<String, String> stringStringMap = new HashMap<>();
    stringStringMap.put("a", "type");
    ModelEngine.recursionExpression(expression, stringStringMap, errorInfo);
    log.error(errorInfo.toString());
  }

  @Test
  public void testDot() {
    String express = "a.(b and c)";
    FormulaParser formulaParser = new FormulaParser(express);
    Expression expression = formulaParser.parseExpression();
    StringBuilder errorInfo = new StringBuilder();
    Map<String, String> stringStringMap = new HashMap<>();
    stringStringMap.put("a", "type");
    ModelEngine.recursionExpression(expression, stringStringMap, errorInfo);
    log.error(errorInfo.toString());
  }

  @Test
  public void testTokenSize() {
    String express = "a and b";
    FormulaParser formulaParser = new FormulaParser(express);
    log.info("token size {}", formulaParser.getTokenLength());
  }

  @Test
  public void testClone() {
    PermissionContext context = new PermissionContext();
  }
}
