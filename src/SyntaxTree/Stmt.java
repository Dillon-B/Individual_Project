package SyntaxTree;



import java.util.List;

public abstract class Stmt {
  public interface Visitor<R> {
  public R visitExpressionStmt(Expression stmt);
    public R visitPrintStmt(Print stmt);
    public R visitIfStmt(StmIf stmt);
  }
  public static class Expression extends Stmt {
  public Expression(Exp expression)  {
      this.expression = expression;
    }

    public final Exp expression;

      @Override
      public <R> R accept(Visitor<R> visitor) {
          return visitor.visitExpressionStmt(this);
      }
  }

  public static class Print extends Stmt {
   public Print(Exp expression)  {
      this.expression = expression;
    }

    public final Exp expression;

      @Override
      public <R> R accept(Visitor<R> visitor) {
          return visitor.visitPrintStmt(this);
      }
  }

  public static class StmIf extends Stmt {
      public StmIf(Exp condition, Stmt ifBranch, Stmt elseBranch) {
          this.condition = condition;
          this.ifBranch = ifBranch;
          this.elseBranch = elseBranch;
      }
      public final Exp condition;
      public final Stmt ifBranch;
      public final Stmt elseBranch;

      @Override
      public <R> R accept(Visitor<R> visitor) {
          return visitor.visitIfStmt(this);
      }
  }
    public abstract <R> R accept(Visitor<R> visitor);
}
