package SyntaxTree;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
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
                "Eq : Token operator, Exp right"
        ));
    }

    public static void defineAst (String outputDir, String baseName, List<String> types)
            throws IOException {
        String path = outputDir + "/" + baseName + ".java";
        PrintWriter writer = new PrintWriter(path, StandardCharsets.UTF_8);
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

        String[] list_fields = fields.split(", ");
        for (String field: list_fields) {
            String name = field.split(" ")[1];
            writer.println("      this." + name + " = " + name + ";");
        }
        writer.println("    }");

        writer.println();
        for (String field : list_fields) {
            writer.println("    final " + field + ";");
        }

        writer.println("  }");
    }
}
