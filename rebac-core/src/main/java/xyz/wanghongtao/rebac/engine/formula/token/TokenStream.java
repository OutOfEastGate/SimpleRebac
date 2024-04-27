package xyz.wanghongtao.rebac.engine.formula.token;

import xyz.wanghongtao.rebac.object.engine.formula.Token;
import xyz.wanghongtao.rebac.object.engine.formula.TokenType;

import java.util.Collection;
import java.util.List;


public class TokenStream extends IndexedStream<Token, TokenType> {
    public TokenStream(Collection<Token> input) {
        super(input, (token, tokenType) -> token.type == tokenType);
    }

    public List<Token> getTokenList() {
      return getInput();
    }


}
