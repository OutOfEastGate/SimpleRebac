package xyz.wanghongtao.rebac.formula;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import xyz.wanghongtao.rebac.engine.formula.FormulaParser;
import xyz.wanghongtao.rebac.service.engine.formula.Expression;

/**
 * @author wanghongtao <wanghongtao05@kuaishou.com>
 * Created on 2024-04-27
 */
@Slf4j
public class FormulaTest {

  @Test
  public void testAnd() {
    String express = "a and";
    FormulaParser formulaParser = new FormulaParser(express);
    Expression expression = formulaParser.parseExpression();
    log.info(expression.toString());
  }
}
