package xyz.wanghongtao.rebac.engine.formula.expression;


import lombok.AllArgsConstructor;
import lombok.Builder;
import xyz.wanghongtao.rebac.object.engine.formula.Expression;
import xyz.wanghongtao.rebac.object.engine.formula.Token;

@AllArgsConstructor
@Builder
public class BinaryOperatorExpression implements Expression {
    private final Token token;
    private final Expression left;
    private final String operator;
    private Expression right;


    public BinaryOperatorExpression(Token token, Expression left, String operator) {
        this.token = token;
        this.left = left;
        this.operator = operator;
    }

    public void setRight(Expression newRight) {
        this.right = newRight;
    }

    public String toString() {
        return "(" +
                left.toString() +
                " " + operator + " " +
                right.toString() +
                ")";
    }

    public Token getToken() {
        return token;
    }

    public Expression getLeft() {
        return left;
    }

    public String getOperator() {
        return operator;
    }

    public Expression getRight() {
        return right;
    }
}
