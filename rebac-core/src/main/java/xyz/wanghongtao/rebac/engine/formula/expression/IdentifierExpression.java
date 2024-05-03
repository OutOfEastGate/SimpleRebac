package xyz.wanghongtao.rebac.engine.formula.expression;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.wanghongtao.rebac.object.engine.formula.Token;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class IdentifierExpression extends AbstractExpression {

    public IdentifierExpression(Token token, String value) {
        super(token, value);
    }

    public IdentifierExpression() {
      super();
    }

}
