package interviewquestions.java;

import java.util.HashSet;
import java.util.Set;

/**
 * Implement an algorithm to print all valid (e.g., properly opened and
 * closed) combinations of n-pairs of parentheses.
 * EXAMPLE:
 *   input: 3 (e.g., 3 pairs of parentheses)
 *   output: ()()(), ()(()), (())(), ((()))
 */
public class GenParenthesis {
  private static Set<String> gen(String current, int level) {
    Set<String> tmp = new HashSet<String>();
    Set<String> set = new HashSet<String>();
    if (level == 0) return set;

    tmp.add("()%".replaceAll("%", current));
    tmp.add("%()".replaceAll("%", current));
    tmp.add("(%)".replaceAll("%", current));

    for (String item : tmp) {
      set.addAll(gen(item, level - 1));
    }

    // level = 1 is actually the last level we'll do some work, since
    // level = 0 is the base case and we just return. Additionally,
    // on level = 1 we are processing the final step, hence the
    // complete list of parenthesis
    if (level == 1) set.addAll(tmp);
    return set;
  }

  public static Set<String> gen(int count) {
    return gen("", count);
  }

  public static void main(String[] args) {
    Set<String> s3 = gen(3);
    test(s3.size() == 5);
    test(s3.contains("()()()"));
    test(s3.contains("()(())"));
    test(s3.contains("(())()"));
    test(s3.contains("(()())"));
    test(s3.contains("((()))"));
  }

  public static void test(boolean condition) {
    // assertions are disabled by default in java, mimic their behaviour here
    if (!condition) {
      throw new AssertionError("invalid test");
    }
  }
}
