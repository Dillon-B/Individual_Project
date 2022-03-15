package Scan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                addToken(matchNext('=') ? NOTEQUAL : EXCLAIM);
                break;
            case '=':
                addToken(matchNext('=') ? EQUALEQUAL : EQUAL);
                break;
            case '<':
                addToken(matchNext('=') ? LESSEQUAL : LESSTHAN);
                break;
            case '>':
                addToken(matchNext('=') ? GREATEREQUAL : GREATERTHAN);
                break;

            case '/':
                if (matchNext('/')) {
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

    private static final Map<String, TokenType> keywords;

    static {
        keywords = new HashMap<>();
        keywords.put("and",    AND);
        keywords.put("class",  CLASS);
        keywords.put("else",   ELSE);
        keywords.put("false",  FALSE);
        keywords.put("for",    FOR);
        keywords.put("if",     IF);
        keywords.put("not",    NOT);
        keywords.put("or",     OR);
        keywords.put("print",  PRINT);
        keywords.put("return", RETURN);
        keywords.put("super",  SUPER);
        keywords.put("this",   THIS);
        keywords.put("true",   TRUE);
        keywords.put("var",    VAR);
        keywords.put("while",  WHILE);
        keywords.put("def", FUNCTION);
        keywords.put("main", MAIN);
        keywords.put("new", NEW);
        keywords.put("do", DO);
        keywords.put("bool", BOOLEAN);
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
        addTokenPrint(STRING, value);
    }


    private boolean isAtEnd() {
        return current >= source.length();
    }

    private char nextChar() {
        return source.charAt(current++);
    }

    private boolean matchNext(char expected) {
        if (isAtEnd()) return false;
        if (source.charAt(current) != expected) return false;

        current++;
        return true;
    }

    private void addToken(TokenType type) {
        addTokenPrint(type, null);
    }

    private void addTokenPrint(TokenType type, Object literal) {
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
        addTokenPrint(INT, Double.parseDouble(source.substring(start,current)));
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

        String text = source.substring(start, current);
        TokenType type = keywords.get(text);
        if (type == null) type = ID;
        addToken(type);

       // addToken(ID);
    }
}
