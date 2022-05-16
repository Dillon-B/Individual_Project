package Compile;

import Interpret.Interpreter;
import Interpret.RuntimeError;
import Parser.Parser;
import SyntaxTree.Exp;
import SyntaxTree.Stmt;
import SyntaxTree.printAST;
import TypeCheck.Token;
import Scan.Scanner;
import TypeCheck.TokenType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;


public class Main {
    private static final Interpreter interpret = new Interpreter();
    static boolean hasError = false;
    static boolean hasRuntimeE = false;
    public static void main(String[] args) throws IOException {
        if (args.length > 1) {
            System.out.println("Too Many Arguments");
            System.exit(64);
        } else if (args.length == 1) {
            runFile(args[0]);
        }

    }

    private static void runFile(String path) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        run(new String(bytes, Charset.defaultCharset()));
        if (hasError) System.exit(65);
        if (hasRuntimeE) System.exit(70);
    }


    private static void run(String source) {
        Scanner scanner = new Scanner(source);
        List<Token> tokens = scanner.scanTokens();

        Parser parser = new Parser(tokens);

        List<Stmt> stms = parser.parse();


        interpret.interpreter(stms);


        }



    public static void error(int line, String message) {
        report(line, " " , message);

    }
    public static void reportRuntimeError(RuntimeError error) {
        System.err.println(error.getMessage() + "\n[line " + error.token.line + "]");
        hasRuntimeE = true;

    }

    static void report (int line, String pos, String message) {
        System.err.println("[Line" + line + "] Error" + pos + ": " + message );
    }

    static void reportError(Token token, String message) {
        if (token.type == TokenType.EOF) {
            report(token.line, " at end", message);
        } else {
            report(token.line, " at '" + token.lex + "'", message);
        }
    }

}
