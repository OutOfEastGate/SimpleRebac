package xyz.wanghongtao.rebac.engine.formula.token;

import xyz.wanghongtao.rebac.engine.formula.FormulaParser;
import xyz.wanghongtao.rebac.object.engine.formula.Token;
import xyz.wanghongtao.rebac.service.engine.formula.Expression;

public interface PrefixParser {
    Expression apply(FormulaParser parser, Token token, boolean canAssign);
}
