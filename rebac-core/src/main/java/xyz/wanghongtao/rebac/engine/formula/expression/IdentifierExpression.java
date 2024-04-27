package xyz.wanghongtao.rebac.engine.formula.expression;

import xyz.wanghongtao.rebac.object.engine.formula.Token;

public class IdentifierExpression extends AbstractExpression {

    public IdentifierExpression(Token token, String value) {
        super(token, value);
    }

}
