package SyntaxTree;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

public class generateAST {
    public static void main (String [] args) {
        if (args.length != 1) {
            System.err.println("Usage: generate_ast <output directory>");
            System.exit(64);
        }
        String outputDir = args[0];
       // defineAst(outputDir, "Exp", Arrays.asList());
    }
}