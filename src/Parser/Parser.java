package Parser;

import java.util.ArrayList;
import java.util.List;

import Compile.Main;
import SyntaxTree.Exp;
import SyntaxTree.Stmt;
import TypeCheck.Token;
import TypeCheck.TokenType;
import static TypeCheck.TokenType.*;



public class Parser {
    private static class ParseError extends RuntimeException {}
    private final List<Token> tokens;
    private int current = 0;


    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    public List<Stmt> parse() {
        List<Stmt> statements = new ArrayList<>();
        while (!isAtEnd()) {
            statements.add(statement());
        }

        return statements;
    }

//    public Exp parse() {
//        try {
//            return Expression();
//        } catch (ParseError error) {
//            return null;
//        }
//    }

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
        Exp exp = single();

        while(match(MULTIPLY, DIVIDE)) {
            Token op = lastChar();
            Exp exp2 = single();
            exp = new Exp.Op(exp, op, exp2);
        }
        return exp;
    }

    private Exp single() {
        if(match(EXCLAIM, MINUS)) {
            Token op = lastChar();
            Exp right = single();
            return new Exp.Eq(op, right);
        }
        return primary();
    }

    private Exp primary() {
        if (match(FALSE)){return new Exp.Literal(false);}
        if (match(TRUE)){return new Exp.Literal(true);}
        if (match(INT, STRING)){return new Exp.Literal(lastChar().literal);}
        if (match(LBR)){
            Exp exp = Expression();
            consume(RBR, "Expect ')' after expression.");
            return new Exp.Stm(exp);
        }
        throw error(token(), "Expect Expression");
    }

    private Token consume(TokenType type, String message) {
        if (check(type)) return checkNextChar();

        throw error(token(), message);
    }

    private ParseError error(Token token, String message) {
        Main.error(token.line, message);
        return new ParseError();
    }

    private void synchronize() {
        checkNextChar();

        while (!isAtEnd()) {
            if (lastChar().type == SEMICOLON) return;

            switch (token().type) {
                case CLASS:
                case MAIN:
                case VAR:
                case FOR:
                case IF:
                case WHILE:
                case PRINT:
                case RETURN:
                case DEF:
                    return;
            }

            checkNextChar();
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

    private Stmt statement() {
        if (match(PRINT)) return printStatement();

        return expressionStatement();
    }

    private Stmt printStatement() {
        Exp value = Expression();
       // consume(SEMICOLON, "Expect ';' after value.");
        return new Stmt.Print(value);
    }

    private Stmt expressionStatement() {
        Exp value = Expression();
       // consume(SEMICOLON, "Expect ';' after value.");
        return new Stmt.Expression(value);
    }

}

