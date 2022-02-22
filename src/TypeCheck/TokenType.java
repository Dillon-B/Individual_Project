package TypeCheck;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

 public enum TokenType {
    // Single-character tokens.
    LBR, RBR, LCBR, RCBR,
    COMMA, PERIOD, MINUS, PLUS, SEMICOLON, DIVIDE, MULTIPLY,

    // Multi-character tokens
    EQUAL, EQUAL_EQUAL,
    GREATER, GREATER_EQUAL,
    LESS, LESS_EQUAL,

    // Literals
    STRING, INT, BOOL,

    // Key-Words
    AND, CLASS, ELSE, FALSE, DEF, FOR, IF, NOT, OR,
    PRINT, RETURN, SUPER, THIS, TRUE, VAR, WHILE, DO,

    EOF
}
