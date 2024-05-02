package xyz.wanghongtao.rebac.engine.formula.expression;

import lombok.Data;
import xyz.wanghongtao.rebac.object.engine.formula.Token;
import xyz.wanghongtao.rebac.service.engine.formula.Expression;

/**
 * @author wanghongtao
 * @data 2024/5/2 15:38
 */
@Data
public class EqualEqualExpression implements Expression {
  private Token token;
  private Expression left;
  private Expression right;

  public EqualEqualExpression(Token token, Expression left) {
    this.token = token;
    this.left = left;
  }
}
