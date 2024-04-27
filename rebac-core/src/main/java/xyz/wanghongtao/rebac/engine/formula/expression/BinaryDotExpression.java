package xyz.wanghongtao.rebac.engine.formula.expression;

import lombok.Data;
import xyz.wanghongtao.rebac.object.engine.formula.Token;
import xyz.wanghongtao.rebac.service.engine.formula.Expression;

/**
 * @author wanghongtao <wanghongtao05@kuaishou.com>
 * Created on 2024-04-27
 */
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
