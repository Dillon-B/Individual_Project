package TypeCheck;

public class Token {

    final TokenType type;
    final String lex;
    final Object literal;
    final int line;

    public Token(TokenType type, String lex, Object literal, int line) {
        this.type = type;
        this.lex = lex;
        this.literal = literal;
        this.line = line;
    }

    @Override
    public String toString() {
        return type + " " + lex + " " + literal;
    }
}