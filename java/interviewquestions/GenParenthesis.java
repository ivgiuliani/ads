package interviewquestions;

import java.util.HashSet;
import java.util.Set;

/**
 * Implement an algorithm to print all valid (e.g., properly opened and
 * closed) combinations of n-pairs of parentheses.
 * EXAMPLE:
 *   input: 3 (e.g., 3 pairs of parentheses)
 *   output: ()()(), ()(()), (())(), ((()))
 */
public class GenParenthesis extends TestCase {
  private static Set<String> gen(String current, int level) {
    Set<String> tmp = new HashSet<String>();
    Set<String> set = new HashSet<String>();
    if (level == 0) return set;

    tmp.add("()" + current);
    tmp.add(current + "()");
    tmp.add("(" + current + ")");

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
    assertEquals(5, s3.size());
    assertTrue(s3.contains("()()()"));
    assertTrue(s3.contains("()(())"));
    assertTrue(s3.contains("(())()"));
    assertTrue(s3.contains("(()())"));
    assertTrue(s3.contains("((()))"));

    Set<String> s1 = gen(1);
    assertEquals(1, s1.size());
    assertTrue(s1.contains("()"));

    Set<String> s0 = gen(0);
    assertTrue(s0.isEmpty());
  }
}
