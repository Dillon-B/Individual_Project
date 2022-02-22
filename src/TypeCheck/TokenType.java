package TypeCheck;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

enum TokenType {
    // Single-character tokens.
    LBR, RBR, LCBR, RCBR,
    COMMA, PERIOD, MINUS, PLUS, SEMICOLON, SLASH, STAR,

    // Multi-character tokens
    EQUAL, EQUAL_EQUAL,
    GREATER, GREATER_EQUAL,
    LESS, LESS_EQUAL,

    // Literals
    IDENTIFIER, STRING, NUMBER, INT
}
