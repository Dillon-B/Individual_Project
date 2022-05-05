package TypeCheck;

public enum TokenType {
    // Single-character tokens.
    LBR, RBR, LCBR, RCBR,
    COMMA, PERIOD, MINUS, PLUS, SEMICOLON, DIVIDE, MULTIPLY, COLON,

    // Multi-character tokens
    EXCLAIM ,NOTEQUAL,
    EQUAL, EQUALEQUAL,
    GREATERTHAN, GREATEREQUAL,
    LESSTHAN, LESSEQUAL,

    // Literals
    STRING, INT, BOOLEAN,

    // Key-Words
    AND, CLASS, ELSE, FALSE, DEF, FOR, IF, NOT, OR,
    PRINT, RETURN, SUPER, THIS, TRUE, VAR, WHILE, DO, MAIN, NEW, ID,

    EOF
}
