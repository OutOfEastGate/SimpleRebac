package xyz.wanghongtao.rebac.engine.formula.expression;

import xyz.wanghongtao.rebac.object.engine.formula.Token;

public class StringLiteral extends AbstractExpression {
    public StringLiteral(Token token, String value) {
        super(token, value);
    }

  public StringLiteral() {
    super();
  }

    public String toString() {
        return getValue();
    }
}
