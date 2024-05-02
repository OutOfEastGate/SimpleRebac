package xyz.wanghongtao.rebac.engine.formula.expression;

import xyz.wanghongtao.rebac.object.engine.formula.Token;

/**
 * @author wanghongtao
 * @date 2024/5/2 17:14
 */
public class SyntaxSymbolLiteral extends AbstractExpression {
  public SyntaxSymbolLiteral(Token token, String value) {
    super(token, value);
  }

  public String toString() {
    return getValue();
  }
}
