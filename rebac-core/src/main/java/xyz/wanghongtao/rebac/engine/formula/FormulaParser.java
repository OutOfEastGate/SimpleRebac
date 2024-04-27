package xyz.wanghongtao.rebac.engine.formula;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;




import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import xyz.wanghongtao.rebac.engine.formula.expression.BinaryAndExpression;
import xyz.wanghongtao.rebac.engine.formula.expression.BinaryOperatorExpression;
import xyz.wanghongtao.rebac.engine.formula.expression.BinaryOrExpression;
import xyz.wanghongtao.rebac.engine.formula.token.*;
import xyz.wanghongtao.rebac.object.engine.formula.Precedence;
import xyz.wanghongtao.rebac.object.engine.formula.Token;
import xyz.wanghongtao.rebac.object.engine.formula.TokenType;
import xyz.wanghongtao.rebac.service.engine.formula.Expression;

@Slf4j
@Getter
public class FormulaParser {
    private static final Map<TokenType, ParseRule> parseRuleMap = new HashMap<>();

    static {
        parseRuleMap.put(TokenType.String, ParseRule.builder()
                .precedence(Precedence.PREC_NONE)
                .prefixParser(new StringParselet())
                .build());

      parseRuleMap.put(TokenType.Identifier, ParseRule.builder()
        .precedence(Precedence.PREC_NONE)
        .prefixParser(new IdentifierParselet())
        .build());


        parseRuleMap.put(TokenType.Plus, ParseRule.builder()
                .precedence(Precedence.PREC_TERM)
                .infixParser(new BinaryExpressionParselet())
                .build());


        parseRuleMap.put(TokenType.Divide, ParseRule.builder()
                .precedence(Precedence.PREC_FACTOR)
                .infixParser(new BinaryExpressionParselet())
                .build());


        parseRuleMap.put(TokenType.LogicOr, ParseRule.builder()
                .precedence(Precedence.PREC_OR)
                .infixParser(new LogicOrInfixParselet())
                .build());

        parseRuleMap.put(TokenType.LogicAnd, ParseRule.builder()
                .precedence(Precedence.PREC_AND)
                .infixParser(new LogicAndInfixParselet())
                .build());

      parseRuleMap.put(TokenType.LeftParen, ParseRule.builder()
        .precedence(Precedence.PREC_CALL)
        .prefixParser(new GroupedExpressionParselet())
        .infixParser(new CallExpressionParselet())
        .build());

    }

    private TokenStream tokenStream;
    public FormulaParser(String input) {
        List<Token> allTokens = FormulaLexer.lex(input);
        List<Token> notBlankTokens = allTokens.stream()
                .filter(t -> !TokenType.BlankTypes.contains(t.getType()))
                .collect(Collectors.toList());
        this.tokenStream = new TokenStream(notBlankTokens);
    }





    public Expression parseExpression() {
        return parsePrecedence(Precedence.PREC_ASSIGNMENT);
    }

    protected Precedence currentPrecedence() {
        ParseRule parseRule = parseRuleMap.get(this.tokenStream.current().getType());
        if (parseRule == null) {
            return Precedence.PREC_NONE;
        } else {
            return parseRule.getPrecedence();
        }
    }

    protected Precedence peekPrecedence() {
        if (!this.tokenStream.peek().isPresent()) {
            return Precedence.PREC_NONE;
        }
        ParseRule parseRule = parseRuleMap.get(this.tokenStream.peek().get().getType());
        if (parseRule == null) {
            return Precedence.PREC_NONE;
        } else {
            return parseRule.getPrecedence();
        }
    }

    private Expression parsePrecedence(Precedence precedence) {

        ParseRule parseRule = parseRuleMap.get(this.tokenStream.current().getType());

        var prefix = parseRule.getPrefixParser();
        if (prefix == null) {
            log.error("need prefix rule for token {}", this.tokenStream.current().getType());
            return null;
        }

        boolean canAssign = precedence.getCode() <= parseRule.getPrecedence().getCode();

        var left = prefix.apply(this, this.tokenStream.current(), canAssign);

        while (!this.tokenStream.isLastToken() && precedence.getCode() <= currentPrecedence().getCode()) {
            ParseRule nextTokenParseRule = parseRuleMap.get(this.tokenStream.current().getType());
            var infix = nextTokenParseRule.getInfixParser();
            if (infix == null) {
                return left;
            }
            left = infix.apply(this, left, this.tokenStream.current(), canAssign);
        }

//        if (canAssign && this.tokenStream.current().getType() == TokenType.Equal) {
//            log.error("Invalid assignment target.");
//        }
        return left;
    }


    private static class StringParselet implements PrefixParser {
        @Override
        public Expression apply(FormulaParser parser, Token token, boolean canAssign) {
            parser.tokenStream.consume();
            return new StringLiteral(token, token.getValue());
        }
    }

  private static class IdentifierParselet implements PrefixParser {

    @Override
    public Expression apply(FormulaParser parser, Token token, boolean canAssign) {
      parser.tokenStream.consume();
      return new Identifier(token, token.getValue());
    }
  }




    private static class BinaryExpressionParselet implements InfixParser {
        @Override
        public Expression apply(FormulaParser parser, Expression left, Token token, boolean canAssign) {
            var expression = new BinaryOperatorExpression(token, left, token.getValue());
            var curPrecedence = parser.currentPrecedence();
            parser.tokenStream.consume();
            var rightExpr = parser.parsePrecedence(curPrecedence.addPrecedence(1));
            expression.setRight(rightExpr);
            return expression;
        }
    }

    private static class LogicAndInfixParselet implements InfixParser {
        @Override
        public Expression apply(FormulaParser parser, Expression left, Token token, boolean canAssign) {
            var expression = new BinaryAndExpression(token, left);
            parser.tokenStream.consume();
            var rightExpr = parser.parsePrecedence(Precedence.PREC_AND);
            expression.setRight(rightExpr);
            return expression;
        }
    }

    private static class LogicOrInfixParselet implements InfixParser {
        @Override
        public Expression apply(FormulaParser parser, Expression left, Token token, boolean canAssign) {
            var expression = new BinaryOrExpression(token, left);
            parser.tokenStream.consume();
            var rightExpr = parser.parsePrecedence(Precedence.PREC_OR);
            expression.setRight(rightExpr);
            return expression;
        }
    }


    private static class GroupedExpressionParselet implements PrefixParser {

        @Override
        public Expression apply(FormulaParser parser, Token token, boolean canAssign) {
            parser.tokenStream.consume();
            var expression = parser.parseExpression();
            parser.tokenStream.consume(TokenType.RightParen);
            return expression;
        }
    }

  private static class CallExpressionParselet implements InfixParser {
    @Override
    public Expression apply(FormulaParser parser, Expression left, Token token, boolean canAssign) {

      var call = new FuncCallExpr(token, left);
      call.setArguments(parser.parseExpressionList(TokenType.RightParen));
      return call;
    }
  }




    private List<Expression> parseExpressionList(TokenType endTokenType) {
        var list = new ArrayList<Expression>();

        this.tokenStream.consume();
        if (this.tokenStream.currentTypeIs(endTokenType)) {
            this.tokenStream.consume();
            return list;
        }

        list.add(parseExpression());

        while (this.tokenStream.currentTypeIs(TokenType.Comma)) {
            this.tokenStream.consume(); // Eat Comma
            list.add(parseExpression());
        }
        this.tokenStream.consume(endTokenType);
        return list;
    }


}
