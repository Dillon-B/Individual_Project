package SyntaxTree;
import TypeCheck.Token;

import java.util.List;

public abstract class Exp {
  public interface Visitor<R> {
    R visitOpExp(Op exp);
    R visitStmExp(Stm exp);
    R visitLiteralExp(Literal exp);
    R visitEqExp(Eq exp);
  }
  public static class Op extends Exp {
   public Op(Exp left, Token operator, Exp right)  {
      this.left = left;
      this.operator = operator;
      this.right = right;
    }

    public final Exp left;
    public final Token operator;
    public final Exp right;

      @Override
      public <R> R accept(Visitor<R> visitor) {
          return visitor.visitOpExp(this);
      }
  }

  public static class Stm extends Exp {
   public Stm(Exp expression)  {
      this.expression = expression;
    }

    public final Exp expression;

      @Override
     public <R> R accept(Visitor<R> visitor) {
          return visitor.visitStmExp(this);
      }
  }

  public static class Literal extends Exp {
   public Literal(Object value)  {
      this.value = value;
    }

    public final Object value;

      @Override
     public <R> R accept(Visitor<R> visitor) {
          return visitor.visitLiteralExp(this);
      }
  }


  public static class Eq extends Exp {
   public Eq(Token operator, Exp right)  {
      this.operator = operator;
      this.right = right;
    }

    public final Token operator;
    public final Exp right;

      @Override
     public <R> R accept(Visitor<R> visitor) {
          return visitor.visitEqExp(this);
      }
  }
  public abstract <R> R accept(Visitor<R> visitor);
}
