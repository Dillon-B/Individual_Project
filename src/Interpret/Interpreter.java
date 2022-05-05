package Interpret;

import SyntaxTree.Exp;

public class Interpreter implements Exp.Visitor<Object>{
    @Override
    public Object visitOpExp(Exp.Op exp) {
        return null;
    }

    @Override
    public Object visitStmExp(Exp.Stm exp) {
        return evaluate(exp.expression);
    }

    @Override
    public Object visitLiteralExp(Exp.Literal exp) {
        return exp.value;
    }

    @Override
    public Object visitEqExp(Exp.Eq exp) {
        return null;
    }

    private Object evaluate(Exp exp) {
        return exp.accept(this);
    }
}
