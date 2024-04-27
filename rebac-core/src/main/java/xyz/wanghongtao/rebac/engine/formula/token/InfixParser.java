package xyz.wanghongtao.rebac.engine.formula.token;

import xyz.wanghongtao.rebac.engine.formula.FormulaParser;
import xyz.wanghongtao.rebac.object.engine.formula.Token;
import xyz.wanghongtao.rebac.service.engine.formula.Expression;

public interface InfixParser {
    Expression apply(FormulaParser parser, Expression left, Token token, boolean canAssign);
}
