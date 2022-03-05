package Scan;

import java.util.ArrayList;
import java.util.List;

import static TypeCheck.TokenType.*;

import Compile.Main;
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

   public List<Token> scanTokens() {
        while (!isAtEnd()) {
            start = current;
            scanToken();
        }

        tokens.add(new Token(EOF, "", null, line));
        return tokens;
    }

    private void scanToken () {
        char c = nextChar();
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
            case ':': addToken(COLON); break;
            case '!':
                addToken(match('=') ? NOTEQUAL : EXCLAIM);
                break;
            case '=':
                addToken(match('=') ? EQUALEQUAL : EQUAL);
                break;
            case '<':
                addToken(match('=') ? LESSEQUAL : LESSTHAN);
                break;
            case '>':
                addToken(match('=') ? GREATEREQUAL : GREATERTHAN);
                break;

            case '/':
                if (match('/')) {
                    // A comment requires the entire line
                    while (check() != '\n' && !isAtEnd()) nextChar();
                } else {
                    addToken(DIVIDE);
                }
                break;
                // Ignoring whitespaces
            case ' ':
            case '\r':
            case '\t':
                break;
            case '\n':
                line++;
                break;

            case '"':
                string();
                break;

            default:
                if (checkIfDigit(c)) {
                    integer();
                } else if (checkIfLetter(c)) {
                    id();
                }
                else {
                    Main.error(line, "Unexpected character.");
                    break;
                }

        }
    }

    private void string() {
        while (check() != '"' && !isAtEnd()) {
            if (check() == '\n') line++; nextChar();
        }
        if (isAtEnd()) {
            Main.error(line, "Undetermined String");
            return;
        }
        nextChar();

        String value = source.substring(start + 1, current - 1);
        addToken(STRING, value);
    }


    private boolean isAtEnd() {
        return current >= source.length();
    }

    private char nextChar() {
        return source.charAt(current++);
    }

    private boolean match(char expected) {
        if (isAtEnd()) return false;
        if (source.charAt(current) != expected) return false;

        current++;
        return true;
    }

    private void addToken(TokenType type) {
        addToken(type, null);
    }

    private void addToken(TokenType type, Object literal) {
        String text = source.substring(start, current);
        tokens.add(new Token(type, text, literal, line));
    }

    private char check() {
        if (isAtEnd()) return '\0';
        return source.charAt(current);
    }

    private boolean checkIfDigit(char c) {
        return c >= '0' && c <= '9';
    }

    private void integer() {
        while (checkIfDigit(check())) nextChar();
        if (check() == '.' && checkIfDigit(checkNextChar())) {
            nextChar();
            while (checkIfDigit(check())) nextChar();
        }
        addToken(INT, Double.parseDouble(source.substring(start,current)));
    }

    private char checkNextChar() {
        if (current + 1 >= source.length()) return '\0';
        return source.charAt(current + 1);
    }

    private boolean checkIfLetter(char c) {
        return (c >= 'a' && c <= 'z') ||
                (c >= 'A' && c <= 'Z') ||
                c == '_';
    }

    private boolean checkLetterOrNum(char c) {
        return checkIfLetter(c) || checkIfDigit(c);
    }

    private void id() {
        while (checkLetterOrNum(check())) nextChar();

        addToken(ID);
    }
}
