package Scan;

import java.util.ArrayList;
import java.util.List;

import static TypeCheck.TokenType.*;
import TypeCheck.Token;
import TypeCheck.TokenType;


public class Scanner {

    private final String source;
    private final List<Token> tokens = new ArrayList<>();
    private int start = 0;
    private int current = 0;
    private int line = 1;

    public Scanner(String source) {
        this.source = source;
    }

    List<Token> scanTokens() {
        while (!isAtEnd()) {
            // We are at the beginning of the next lexeme.
            start = current;
            scanToken();
        }

        tokens.add(new Token(EOF, "", null, line));
        return tokens;
    }

    private void scanToken () {
        char c = advance();
        switch (c) {
            case '(': addToken(LBR); break;
            case ')': addToken(RBR); break;
            case '{': addToken(LCBR); break;
            case '}': addToken(RCBR); break;
            case ',': addToken(COMMA); break;
            case '.': addToken(PERIOD); break;
            case '-': addToken(MINUS); break;
            case '+': addToken(PLUS); break;
            case ';': addToken(SEMICOLON); break;
            case '*': addToken(MULTIPLY); break;

        }
    }


    private boolean isAtEnd() {
        return current >= source.length();
    }

    private char advance() {
        return source.charAt(current++);
    }

    private void addToken(TokenType type) {
        addToken(type, null);
    }

    private void addToken(TokenType type, Object literal) {
        String text = source.substring(start, current);
        tokens.add(new Token(type, text, literal, line));
    }
}
