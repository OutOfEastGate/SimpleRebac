package xyz.wanghongtao.rebac.engine.formula.token;

import lombok.Builder;
import lombok.Data;
import xyz.wanghongtao.rebac.object.engine.formula.Precedence;

@Data
@Builder
public class ParseRule {
    private Precedence precedence;
    private PrefixParser prefixParser;
    private InfixParser infixParser;
}
