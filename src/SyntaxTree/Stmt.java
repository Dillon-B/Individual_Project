package SyntaxTree;



import java.util.List;

abstract class Stmt {
  interface Visitor<R> {
    R visitExpressionStmt(Expression stmt);
    R visitPrintStmt(Print stmt);
  }
  static class Expression extends Stmt {
   Expression(Exp expression)  {
      this.expression = expression;
    }

    final Exp expression;

      @Override
      public <R> R accept(Visitor<R> visitor) {
          return visitor.visitExpressionStmt(this);
      }
  }

  static class Print extends Stmt {
   Print(Exp expression)  {
      this.expression = expression;
    }

    final Exp expression;

      @Override
      public <R> R accept(Visitor<R> visitor) {
          return visitor.visitPrintStmt(this);
      }
  }
    public abstract <R> R accept(Visitor<R> visitor);
}
