package xyz.wanghongtao.rebac.object.engine.formula;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public enum TokenType {
    LeftCurly,   // {
    RightCurly,  // }
    LeftBracket,     // [
    RightBracket,    // ]
    LeftParen,   // (
    RightParen,  // )
    NewLine,
    Whitespace,
    Number,
    String,
    Identifier,
    SyntaxSymbol, // $


    Plus,   // +
    Minus,  // -
    Multiply,   // *
    Divide,     // /
    Percentage, // %

    Bang,   // !
    NotEqual, // !=
    Equal,  // =
    EqualEqual, // ==
    Greater,    // >
    GreaterEqual, // >=
    Less, // <
    LessEqual, // <=

    LogicAnd, // &&
    LogicOr, // ||

    SingleQuote,
    DoubleQuote,
    QuasiQuote, // `

    CommentLine,
    CommentBlock,

    Semicolon,   // ;
    Colon,  // :
    Dot,    // .
    Comma,  //,




    // keywords end

    ;

    public static Set<TokenType> BlankTypes = new HashSet<>(Arrays.asList(new TokenType[] {
            TokenType.NewLine, TokenType.Whitespace, TokenType.CommentLine, TokenType.CommentBlock
    }));
}
