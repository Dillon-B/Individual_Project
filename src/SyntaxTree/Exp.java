package SyntaxTree;


import TypeCheck.Token;

public abstract class Exp {
    public static class Op extends Exp {
        public Op(Exp left, Token operator, Exp right) {
            this.left = left;
            this.operator = operator;
            this.right = right;
        }
        final Exp left;
        final Token operator;
        final Exp right;
    }
    public static class Stm extends Exp {
        public Stm(Exp statement) {
            this.statement = statement;
        }
        final Exp statement;
    }
    public static class Literal extends Exp {
        public Literal(Object value) {
            this.value = value;
        }
        final Object value;
    }
    public static class Eq extends Exp {
       public Eq(Token operator, Exp right) {
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
