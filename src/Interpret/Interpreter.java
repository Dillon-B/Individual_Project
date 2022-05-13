package Interpret;

import Compile.Main;
import SyntaxTree.Exp;
import TypeCheck.Token;

public class Interpreter implements Exp.Visitor<Object>{

    void interpret(Exp exp) {
        try {
            Object object = evaluate(exp);
            System.out.println(isString(object));
        } catch (RuntimeError error) {
            Main.reportRuntimeError(error);
        }
    }
    @Override
    public Object visitOpExp(Exp.Op exp) {
        Object left = evaluate(exp.left);
        Object right = evaluate(exp.right);

        switch (exp.operator.type) {
            case NOTEQUAL:
                return !isEqual(left, right);
            case EQUALEQUAL:
                return isEqual(left, right);
            case GREATERTHAN:
                checkBothIfNumber(exp.operator, left, right);
                return (double)left > (double)right;
            case GREATEREQUAL:
                checkBothIfNumber(exp.operator, left, right);
                return (double)left >= (double)right;
            case LESSTHAN:
                checkBothIfNumber(exp.operator, left, right);
                return (double)left < (double)right;
            case LESSEQUAL:
                checkBothIfNumber(exp.operator, left, right);
                return (double)left <= (double)right;
            case MINUS:
                checkBothIfNumber(exp.operator, left, right);
                return (double)left - (double)right;
            case MULTIPLY:
                checkBothIfNumber(exp.operator, left, right);
                return (double)left * (double)right;
            case DIVIDE:
                checkBothIfNumber(exp.operator, left, right);
                return (double)left / (double)right;
            case PLUS:
                if (left instanceof Double && right instanceof Double) {
                    return (double)left + (double)right;
                }

                else if (left instanceof String && right instanceof String) {
                    return left + (String)right;
                }
                break;
        }
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
        Object right = evaluate(exp.right);
        switch (exp.operator.type) {
            case MINUS:
                checkIfNumber(exp.operator, right);
                return -(double)right;
            case EXCLAIM:
                return isTrue(right);
        }
        return null;
    }

    private Object evaluate(Exp exp) {
        return exp.accept(this);
    }

    private boolean isTrue(Object object) {
        if (object == null)
            return false;

        if (object instanceof Boolean)
            return (boolean) object;
            return true;

    }
    private boolean isEqual(Object a, Object b) {
        if (a == null && b == null) return true;
        if (a == null) return false;

        return a.equals(b);
    }
    private void checkIfNumber(Token operator, Object number){
        if (number instanceof Double) return;
        throw new RuntimeError(operator, "Number must be a double");
    }

    private void checkBothIfNumber(Token operator, Object left, Object right){
        if (left instanceof Double && right instanceof Double) return;
        throw new RuntimeError(operator, "Both Expressions must be Numbers or Strings");
    }

    public String isString (Object object){
        if (object == null) return "none";

        if (object instanceof Double) {
            String text = object.toString();
            if (text.endsWith(".0")) {
                text = text.substring(0, text.length() - 2);
            }
            return text;
        }
        return object.toString();
    }
}
