package Parser;

import java.util.List;

import SyntaxTree.Exp;
import TypeCheck.Token;
import TypeCheck.TokenType;
import TypeCheck.TokenType.*;

public class Parser {
    private final List<Token> tokens;
    private int current = 0;


    Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    private Exp Expression() {
        return Eq();
    }

    private Exp Eq() {
        Exp exp = compare();
        while (match(NOT_EQUAL, EQUAL_EQUAL)) {
            Token op = lastChar();
            Exp exp2 = compare();
            exp = new Exp.Op(exp, op, exp2);
        }
    }

    private boolean match(TokenType... types){
        for (TokenType type: types) {
            if (check(type)) {
                checkNextChar();
                return true;
            }
        }
        return false;
    }

}

