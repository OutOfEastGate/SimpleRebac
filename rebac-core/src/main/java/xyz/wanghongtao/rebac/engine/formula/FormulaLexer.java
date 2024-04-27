package xyz.wanghongtao.rebac.engine.formula;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import xyz.wanghongtao.rebac.exception.CustomException;
import xyz.wanghongtao.rebac.object.engine.formula.Token;
import xyz.wanghongtao.rebac.object.engine.formula.TokenType;


public class FormulaLexer {
    private static final Pattern REG = Pattern.compile(
            "(?<LeftCurly>\\{)|(?<RightCurly>\\})|(?<LeftBracket>\\[)|(?<RightBracket>\\])|(?<LeftParen>\\()|(?<RightParen>\\))"
                    + "|(?<Colon>:)|(?<Comma>,)|(?<Semicolon>;)"
                    + "|(?<Bang>!)|(?<Percentage>%)"
                    + "|(?<NewLine>\\n)"
                    + "|(?<Whitespace>(?:\t| |\r)+)"
                    + "|(?<Number>-?(?:0|[1-9]\\d*)(?:\\.\\d+)?(?:[Ee][+-]?\\d+)?)"
                    + "|(?<String>\"(?:[^\"\\\\]*|\\\\(?:[\"\\\\bfnrt\\/]|u[0-9A-Fa-f]{4}))*\")"
                    + "|(?<Identifier>(?:(?:[a-zA-Z_])(?:[a-zA-Z0-9_])*)|\\+|\\-[>]?|\\*|\\/|>[=]?|<[=]?|\\.|@)"
                    + "|(?<SyntaxSymbol>(?:\\$([a-zA-Z_][a-zA-Z0-9_]*)?))"
    );

    public static Map<String, TokenType> keywordMapping = new HashMap<>();
    static {
        keywordMapping.put("+", TokenType.Plus);
        keywordMapping.put("-", TokenType.Minus);
        keywordMapping.put("*", TokenType.Multiply);
        keywordMapping.put("/", TokenType.Divide);
        keywordMapping.put("AND", TokenType.LogicAnd);
        keywordMapping.put("and", TokenType.LogicAnd);
        keywordMapping.put("OR", TokenType.LogicOr);
        keywordMapping.put("or", TokenType.LogicOr);
    }

    public static List<Token> lex(String input) throws CustomException {
        List<Token> tokenList = new ArrayList<Token>();
        int startat = 0;
        int row = 1;
        int column = 1;
        Matcher matcher = REG.matcher(input);
        while (startat < input.length()) {
            if (!matcher.find(startat)) {
                throw new CustomException("Invalid Token" + row + column);
            }
            for (TokenType tokenType : TokenType.values()) {
                String value = null;
                try {
                    value = matcher.group(tokenType.name());
                } catch (Exception ignored) {
                }
                if (Objects.nonNull(value)) {
                    Token token = new Token(tokenType, value, row, column);
                    if (TokenType.Identifier == tokenType && keywordMapping.containsKey(value)) {
                        token.setType(keywordMapping.get(value));
                    }
                    tokenList.add(token);
                  switch (tokenType) {
                    case NewLine, CommentLine -> {
                      row++;
                      column = 1;
                      startat += value.length();
                    }
                    case Whitespace -> {
                      startat += value.length();
                      column += value.length();
                    }
                    default -> {
                      startat += value.length();
                      column += value.length();
                    }
                  }
                    break;
                }
            }
        }
        return tokenList;
    }
}
