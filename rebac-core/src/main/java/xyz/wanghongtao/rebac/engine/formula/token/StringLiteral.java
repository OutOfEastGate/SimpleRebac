package xyz.wanghongtao.rebac.engine.formula.token;

import xyz.wanghongtao.rebac.engine.formula.expression.AbstractExpression;
import xyz.wanghongtao.rebac.object.engine.formula.Token;

public class StringLiteral extends AbstractExpression {
    public StringLiteral(Token token, String value) {
        super(token, value);
    }

    public String toString() {
        return getValue();
    }
}
