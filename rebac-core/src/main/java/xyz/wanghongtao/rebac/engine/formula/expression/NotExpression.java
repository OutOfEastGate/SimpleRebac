package xyz.wanghongtao.rebac.engine.formula.expression;

import lombok.Builder;
import lombok.NoArgsConstructor;
import xyz.wanghongtao.rebac.object.engine.formula.Expression;
import xyz.wanghongtao.rebac.object.engine.formula.Token;

/**
 * @author wanghongtao
 * @date 2024/5/2 15:19
 */
@NoArgsConstructor
@Builder
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
