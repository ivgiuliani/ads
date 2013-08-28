package interviewquestions;

import java.util.Deque;
import java.util.LinkedList;


public class BalancedParenthesis {
  public static boolean isBalanced(String str) {
    // assume that the string consists only of valid characters
    // i.e. only (, [, {, }, ] and ) chars are present

    char[] close_map = new char[128];
    close_map[')'] = '(';
    close_map[']'] = '[';
    close_map['}'] = '{';
    Character tmp;

    Deque<Character> stack = new LinkedList<Character>();
    for (char ch : str.toCharArray()) {
      if (isClose(ch)) {
        // use pollFirst instead of pop() since this doesn't throw an exception
        tmp = stack.pollFirst();
        if (tmp == null || tmp != close_map[ch])
          return false;
      } else stack.push(ch);
    }
    return stack.isEmpty();
  }

  private static boolean isClose(char ch) {
    return ch == ')' || ch == ']' || ch == '}';
  }

  public static void main(String[] args) {
    test(isBalanced("((()))"));
    test(isBalanced("{[()]}"));
    test(isBalanced("{{{}}}"));
    test(isBalanced("()"));
    test(isBalanced("()()()"));
    test(isBalanced("()[]{}"));
    test(!isBalanced("([(])"));
    test(!isBalanced("{}{(){"));
    test(!isBalanced(")()()"));
  }

  public static void test(boolean condition) {
    // assertions are disabled by default in java, mimic their behaviour here
    if (!condition) {
      throw new AssertionError("invalid test");
    }
  }
}
