package SyntaxTree;
import TypeCheck.Token;

import java.util.List;

abstract class Exp {
  interface Visitor<R> {
    R visitOpExp(Op exp);
    R visitStmExp(Stm exp);
    R visitLiteralExp(Literal exp);
    R visitEqExp(Eq exp);
  }
  static class Op extends Exp {
   Op(Exp left, Token operator, Exp right)  {
      this.left = left;
      this.operator = operator;
      this.right = right;
    }

    final Exp left;
    final Token operator;
    final Exp right;

      @Override
      <R> R accept(Visitor<R> visitor) {
          return visitor.visitOpExp(this);
      }
  }

  static class Stm extends Exp {
   Stm(Exp expression)  {
      this.expression = expression;
    }

    final Exp expression;

      @Override
      <R> R accept(Visitor<R> visitor) {
          return visitor.visitStmExp(this);
      }
  }


  static class Literal extends Exp {
   Literal(Object value)  {
      this.value = value;
    }

    final Object value;

      @Override
      <R> R accept(Visitor<R> visitor) {
          return visitor.visitLiteralExp(this);
      }
  }


  static class Eq extends Exp {
   Eq(Token operator, Exp right)  {
      this.operator = operator;
      this.right = right;
    }

    final Token operator;
    final Exp right;

      @Override
      <R> R accept(Visitor<R> visitor) {
          return visitor.visitEqExp(this);
      }
  }

  abstract <R> R accept(Visitor<R> visitor);
}
