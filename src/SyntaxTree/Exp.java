package SyntaxTree;


import TypeCheck.Token;

public abstract class Exp {
    static class Op extends Exp {
        Op(Exp left, Token operator, Exp right) {
            this.left = left;
            this.operator = operator;
            this.right = right;
        }
        final Exp left;
        final Token operator;
        final Exp right;
    }
    @Override
    public String toString() {
        return "toString not overridden in class";
    }
}
