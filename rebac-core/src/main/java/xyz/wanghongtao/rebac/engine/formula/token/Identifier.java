package xyz.wanghongtao.rebac.engine.formula.token;

import xyz.wanghongtao.rebac.engine.formula.expression.AbstractExpression;
import xyz.wanghongtao.rebac.object.engine.formula.Token;

public class Identifier extends AbstractExpression {

    public Identifier(Token token, String value) {
        super(token, value);
    }

}
