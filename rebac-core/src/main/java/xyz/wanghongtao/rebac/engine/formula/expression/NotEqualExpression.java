package xyz.wanghongtao.rebac.engine.formula.expression;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.wanghongtao.rebac.object.engine.formula.Expression;
import xyz.wanghongtao.rebac.object.engine.formula.Token;

/**
 * @author wanghongtao
 * @date 2024/5/2 15:47
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class NotEqualExpression implements Expression {
  private Token token;
  private Expression left;
  private Expression right;

  public NotEqualExpression(Token token, Expression left) {
    this.token = token;
    this.left = left;
  }
}
