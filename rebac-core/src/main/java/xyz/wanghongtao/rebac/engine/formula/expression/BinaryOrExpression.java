package xyz.wanghongtao.rebac.engine.formula.expression;


import lombok.Data;
import xyz.wanghongtao.rebac.object.engine.formula.Token;
import xyz.wanghongtao.rebac.service.engine.formula.Expression;

/**
 * @author kongweixian
 * Created on 2023-05-11
 */
@Data
public class BinaryOrExpression implements Expression {

    private Token token;
    private Expression left;
    private Expression right;

    public BinaryOrExpression(Token token, Expression left) {
        this.token = token;
        this.left = left;
    }
}
