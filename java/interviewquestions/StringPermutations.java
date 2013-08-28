package interviewquestions;

import java.util.HashSet;
import java.util.Set;

/**
 * Compute all permutations of a string.
 */
public class StringPermutations {
  public static Set<String> permutations(String str) {
    Set<String> perms = new HashSet<String>();
    Set<String> tmp;
    if (str.length() == 0) return perms;

    perms.add(str);

    Character start = str.charAt(0);
    tmp = permutations(str.substring(1));

    for (String permutation : tmp) {
      for (int i = 0; i <= permutation.length(); i++) {
        perms.add(insertAt(permutation, start, i));
      }
    }

    return perms;
  }

  private static String insertAt(String string, Character ch, int pos) {
    return string.substring(0, pos) + ch + string.substring(pos, string.length());
  }

  public static void main(String[] args) {
    Set<String> perm3 = permutations("abc");
    test(perm3.size() == 3 * 2); // 3!
    test(perm3.contains("abc"));
    test(perm3.contains("acb"));
    test(perm3.contains("bac"));
    test(perm3.contains("bca"));
    test(perm3.contains("cab"));
    test(perm3.contains("cba"));

    Set<String> perm4 = permutations("abcd");
    test(perm4.size() == 4 * 3 * 2); // 4!
    test(perm4.contains("abcd"));
    test(perm4.contains("abdc"));
    test(perm4.contains("acbd"));
    test(perm4.contains("acdb"));
    test(perm4.contains("adcb"));
    test(perm4.contains("adbc"));
    test(perm4.contains("bacd"));
    test(perm4.contains("badc"));
    test(perm4.contains("bcad"));
    test(perm4.contains("bcda"));
    test(perm4.contains("bdac"));
    test(perm4.contains("bdca"));
    test(perm4.contains("cabd"));
    test(perm4.contains("cadb"));
    test(perm4.contains("cbad"));
    test(perm4.contains("cbda"));
    test(perm4.contains("cdab"));
    test(perm4.contains("cdba"));
    test(perm4.contains("dabc"));
    test(perm4.contains("dacb"));
    test(perm4.contains("dbac"));
    test(perm4.contains("dbca"));
    test(perm4.contains("dcab"));
    test(perm4.contains("dcba"));


    Set<String> perm5 = permutations("abcde");
    test(perm5.size() == 5 * 4 * 3 * 2); // 5!

    Set<String> perm6 = permutations("abcdef");
    test(perm6.size() == 6 * 5 * 4 * 3 * 2); // 6!

    Set<String> perm7 = permutations("abcdefg");
    test(perm7.size() == 7 * 6 * 5 * 4 * 3 * 2); // 7!
  }

  public static void test(boolean condition) {
    // assertions are disabled by default in java, mimic their behaviour here
    if (!condition) {
      throw new AssertionError("invalid test");
    }
  }
}
