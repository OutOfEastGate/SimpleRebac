package xyz.wanghongtao.rebac.engine.formula.expression;

import java.util.List;


import xyz.wanghongtao.rebac.object.engine.formula.Token;
import xyz.wanghongtao.rebac.service.engine.formula.Expression;

public class FuncCallExpression implements Expression {
    private final Token token;
    private final Expression function;
    private List<Expression> arguments;

    public Token getToken() {
        return token;
    }

    public Expression getFunction() {
        return function;
    }

    public List<Expression> getArguments() {
        return arguments;
    }

    public FuncCallExpression(Token token, Expression function) {
        this.token = token;
        this.function = function;
    }

    public void setArguments(List<Expression> newArguments) {
        this.arguments = newArguments;
    }
}
