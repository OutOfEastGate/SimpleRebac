package xyz.wanghongtao.rebac.engine.formula.expression;

import xyz.wanghongtao.rebac.object.engine.formula.Token;
import xyz.wanghongtao.rebac.service.engine.formula.Expression;

/**
 * @author wanghongtao
 * @date 2024/5/2 15:19
 */
public class NotExpression implements Expression {
  private Token token;
  private Expression right;

  public NotExpression(Token token, Expression rigth) {
    this.token = token;
    this.right = rigth;
  }

  public Expression getRight() {
    return right;
  }
}
