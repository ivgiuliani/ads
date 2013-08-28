package interviewquestions.java;

/**
 * 1) Define a valid object oriented representation for math expressions
 * 2) Given a valid math expression like '5+3-4*2+1' parse it into the
 *    previously defined classes
 * 3) Print the expression back, starting from your class definitions
 *
 * Note: support only integer operations
 * Note 2: there are no fancy characters in the expression to be parsed,
 *         only numbers and operators (+, -, * and /). There are no spaces.
 */
public class MathParser {
  // 1) definition of the OO representation
  public interface Operation {
    public int eval();
  }

  public static class UnaryOperation implements Operation {
    private int value;

    public void setValue(int value) { this.value = value; }
    public int getValue() { return value; }

    @Override
    public int eval() {
      return value;
    }

    public String toString() {
      return String.valueOf(value);
    }
  }

  public static abstract class BinaryOperation implements Operation {
    private Operation left;
    private Operation right;

    public void setLeft(Operation op) { this.left = op; }
    public void setRight(Operation op) { this.right = op; }
    public Operation getLeft() { return this.left; }
    public Operation getRight() { return this.right; }

    protected abstract char getOpChar();

    public String toString() {
      return "(" + getLeft().toString() + getOpChar() + getRight().toString() + ")";
    }
  }

  public static class AdditionOperation extends BinaryOperation {
    @Override
    public int eval() {
      return getLeft().eval() + getRight().eval();
    }

    protected char getOpChar() { return '+'; }
  }

  public static class MultiplicationOperation extends BinaryOperation {
    @Override
    public int eval() {
      return getLeft().eval() * getRight().eval();
    }

    protected char getOpChar() { return '*'; }
  }

  public static class DivisionOperation extends BinaryOperation {
    @Override
    public int eval() {
      return getLeft().eval() / getRight().eval();
    }

    protected char getOpChar() { return '/'; }
  }

  // 2) parse the math expression into an Operation hierarchy
  public static Operation parse(String expression) {
    final char[] priorities = { '/', '*', '-', '+' };
    int pos;
    String left, right;
    Operation op = null;

    boolean isDigit = true;
    int start = 0;
    if (expression.charAt(0) == '-' || expression.charAt(0) == '+') start = 1;
    for (int i = start; i < expression.length() && isDigit; i++) {
      if (!Character.isDigit(expression.charAt(i)))
        isDigit = false;
    }

    if (isDigit) {
      op = new UnaryOperation();
      ((UnaryOperation)op).setValue(Integer.valueOf(expression));
    } else {
      // find the operation with the highest priority and use that as root
      // this is O(n^2) with n being the length of the expression
      // TODO: make it better
      for (char ch : priorities) {
        pos = expression.indexOf(ch, start);
        if (pos == -1) continue;

        if (ch == '-') {
          // negation is handled by using an addition and negating the values
          // of the expression
          op = getOperation('+');
          left = expression.substring(0, pos);
          right = expression.substring(pos);
        } else {
          op = getOperation(ch);
          left = expression.substring(0, pos);
          right = expression.substring(pos + 1);
        }

        ((BinaryOperation)op).setLeft(parse(left));
        ((BinaryOperation)op).setRight(parse(right));
      }
    }

    return op;
  }

  private static BinaryOperation getOperation(char ch) {
    switch (ch) {
      case '/':
        return new DivisionOperation();
      case '*':
        return new MultiplicationOperation();
      case '+':
        return new AdditionOperation();
    }

    return null;
  }

  public static void main(String[] args) {
    test(parse("1234567890").eval() == 1234567890);
    test(parse("5+3-4*2+1").eval() == 1);
    test(parse("5-3+6*3-10").eval() == 5-3+6*3-10);
    test(parse("6/3+3*2-10").eval() == 6/3+3*2-10);
    test(parse("-1-1-1-1-1-1").eval() == -1-1-1-1-1-1);
    test(parse("-1+2").eval() == -1+2);
    test(parse("-1*10").eval() == -10);
    test(parse("-15-2*10").eval() == -15-2*10);
    test(parse("45/5*100-1").eval() == 45/5*100-1);
    test(parse("10*100*1000/1000").eval() == 10 * 100);
    test(parse("10-2").eval() == 10-2);
    System.out.println(parse("1*-1").eval());
    System.out.println(parse("1*-1").toString());
    test(parse("1*-1").eval() == -1);

    test(parse("5+3-4*2+1").toString().equals("(5+((3+(-4*2))+1))"));
    test(parse("5-3+6*3-10").toString().equals("((5+-3)+((6*3)+-10))"));
    test(parse("6/3+3*2-10").toString().equals("((6/3)+((3*2)+-10))"));
    test(parse("-1-1-1-1-1-1").toString().equals("(-1+(-1+(-1+(-1+(-1+-1)))))"));
    test(parse("10*100*1000/1000").toString().equals("(10*(100*(1000/1000)))"));
  }

  public static void test(boolean condition) {
    // assertions are disabled by default in java, mimic their behaviour here
    if (!condition) {
      throw new AssertionError("invalid test");
    }
  }
}
