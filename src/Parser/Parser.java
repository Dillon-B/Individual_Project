package Parser;

import java.util.List;

import SyntaxTree.Exp;
import TypeCheck.Token;
import TypeCheck.TokenType;
import static TypeCheck.TokenType.*;



public class Parser {
    private final List<Token> tokens;
    private int current = 0;


    Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    private Exp Expression() {
        return Op();
    }

    private Exp Op() {
        Exp exp = compare();
        while (match(NOTEQUAL, EQUAL)) {
            Token op = lastChar();
            Exp exp2 = compare();
            exp = new Exp.Op(exp, op, exp2);
        }
        return exp;
    }

    private Exp compare() {
        Exp exp = translate();

        while (match(GREATERTHAN, GREATEREQUAL, LESSTHAN, LESSEQUAL)){
            Token op = lastChar();
            Exp exp2 = translate();
            exp = new Exp.Op(exp, op, exp2);
        }
        return exp;
    }

    private Exp translate() {
        Exp exp = factor();

        while(match(PLUS, MINUS)) {
            Token op = lastChar();
            Exp exp2 = factor();
            exp = new Exp.Op(exp, op, exp2);
        }
        return exp;
    }

    private Exp factor(){
        return null;
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

    private boolean check(TokenType type){
        if (isAtEnd()) return false;
        return token().type == type;
    }

    private Token checkNextChar() {
        if (!isAtEnd()) current++;
        return lastChar();
    }

    private boolean isAtEnd() {
        return token().type == EOF;
    }

    private Token token() {
        return tokens.get(current);
    }

    private Token lastChar() {
        return tokens.get(current-1);
    }

}

