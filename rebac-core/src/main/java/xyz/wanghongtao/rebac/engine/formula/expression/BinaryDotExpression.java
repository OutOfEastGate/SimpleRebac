package xyz.wanghongtao.rebac.engine.formula.expression;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.wanghongtao.rebac.object.engine.formula.Expression;
import xyz.wanghongtao.rebac.object.engine.formula.Token;

/**
 * @author wanghongtao <wanghongtao05@kuaishou.com>
 * Created on 2024-04-27
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class BinaryDotExpression implements Expression {
  private Token token;
  private Expression left;
  private Expression right;

  public BinaryDotExpression(Token token, Expression left) {
    this.token = token;
    this.left = left;
  }
}
