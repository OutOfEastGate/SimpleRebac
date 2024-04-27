package xyz.wanghongtao.rebac.engine.formula.token;

import java.util.List;


import xyz.wanghongtao.rebac.object.engine.formula.Token;
import xyz.wanghongtao.rebac.service.engine.formula.Expression;

public class FuncCallExpr implements Expression {
    private Token token;
    private Expression function;
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

    public FuncCallExpr(Token token, Expression function) {
        this.token = token;
        this.function = function;
    }

    public void setArguments(List<Expression> newArguments) {
        this.arguments = newArguments;
    }
}
