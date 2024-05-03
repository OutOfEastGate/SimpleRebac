package xyz.wanghongtao.rebac.engine.formula.expression;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.wanghongtao.rebac.object.engine.formula.Expression;
import xyz.wanghongtao.rebac.object.engine.formula.Token;

/**
 * @author kongweixian
 * Created on 2023-05-11
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class BinaryAndExpression implements Expression {

    private Token token;
    private Expression left;
    private Expression right;

    public BinaryAndExpression(Token token, Expression left) {
        this.token = token;
        this.left = left;
    }
}
