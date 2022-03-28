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
    static class Stm extends Exp {
        Stm(Exp statement) {
            this.statement = statement;
        }
        final Exp statement;
    }
    static class Literal extends Exp {
        Literal(Object value) {
            this.value = value;
        }
        final Object value;
    }
    static class Eq extends Exp {
        Eq(Token operator, Exp right) {
            this.operator = operator;
            this.right = right;
        }
        final Token operator;
        final Exp right;
    }

    @Override
    public String toString() {
        return "toString not overridden in class";
    }
}
