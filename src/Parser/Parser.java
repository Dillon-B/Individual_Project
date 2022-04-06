package Parser;

import java.util.List;

import TypeCheck.Token;
import TypeCheck.TokenType.*;

public class Parser {
    private final List<Token> tokens;
    private int current = 0;


    Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

}
