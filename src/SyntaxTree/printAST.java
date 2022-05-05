package SyntaxTree;

import TypeCheck.Token;
import TypeCheck.TokenType;

public class printAST implements Exp.Visitor<String> {
    public String print(Exp exp) {
        return exp.accept(this);
    }

    @Override
    public String visitOpExp(Exp.Op exp) {
        return parenthesize(exp.operator.lex, exp.left, exp.right);
    }

    @Override
    public String visitStmExp(Exp.Stm exp) {
        return parenthesize("Stm", exp.expression);
    }

    @Override
    public String visitLiteralExp(Exp.Literal exp) {
        if (exp.value == null) return "nil";
        return exp.value.toString();
    }

    @Override
    public String visitEqExp(Exp.Eq exp) {
        return parenthesize(exp.operator.lex, exp.right);
    }

    private String parenthesize(String name, Exp... exps) {
        StringBuilder builder = new StringBuilder();

        builder.append("(").append(name);
        for (Exp expr : exps) {
            builder.append(" ");
            builder.append(expr.accept(this));
        }
        builder.append(")");

        return builder.toString();
    }

}
