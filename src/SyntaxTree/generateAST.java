package SyntaxTree;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.List;

public class generateAST {
    public static void main (String [] args) throws IOException {
        if (args.length != 1) {
            System.err.println("Usage: generate_ast <output directory>");
            System.exit(64);
        }
        String outputDir = args[0];
        defineAst(outputDir, "Exp", Arrays.asList(
                "Op : Exp left, Token operator, Exp right",
                "Stm : Exp expression",
                "Literal : Object value",
                "Float : Token operator, Exp right"
        ));
    }

    public static void defineAst (String outputDir, String baseName, List<String> types)
            throws IOException {
        String path = outputDir + "/" + baseName + ".java";
        PrintWriter writer = new PrintWriter(path, "UTF-8");
        writer.println("Package Compile;");
        writer.println();
        writer.println("import java.util.List;");
        writer.println();
        writer.println("abstract class " + baseName + " {");
        for (String type : types) {
            String className = type.split(":")[0].trim();
            String fields = type.split(":")[1].trim();
            defineType(writer, baseName, className, fields);
        }

        writer.println("}");
        writer.close();
    }

    public static void defineType(PrintWriter writer, String baseName, String className, String fields) {
        writer.println("  static class " + className + " extends " + baseName + " {");
        writer.println("   " + className + "(" + fields + ")  {");
    }
}
