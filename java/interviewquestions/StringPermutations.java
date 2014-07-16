package interviewquestions;

import java.util.HashSet;
import java.util.Set;

/**
 * Compute all permutations of a string.
 */
public class StringPermutations extends TestCase {
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
    assertEquals(3 * 2, perm3.size()); // 3!
    assertTrue(perm3.contains("abc"));
    assertTrue(perm3.contains("acb"));
    assertTrue(perm3.contains("bac"));
    assertTrue(perm3.contains("bca"));
    assertTrue(perm3.contains("cab"));
    assertTrue(perm3.contains("cba"));

    Set<String> perm4 = permutations("abcd");
    assertEquals(4 * 3 * 2, perm4.size()); // 4!
    assertTrue(perm4.contains("abcd"));
    assertTrue(perm4.contains("abdc"));
    assertTrue(perm4.contains("acbd"));
    assertTrue(perm4.contains("acdb"));
    assertTrue(perm4.contains("adcb"));
    assertTrue(perm4.contains("adbc"));
    assertTrue(perm4.contains("bacd"));
    assertTrue(perm4.contains("badc"));
    assertTrue(perm4.contains("bcad"));
    assertTrue(perm4.contains("bcda"));
    assertTrue(perm4.contains("bdac"));
    assertTrue(perm4.contains("bdca"));
    assertTrue(perm4.contains("cabd"));
    assertTrue(perm4.contains("cadb"));
    assertTrue(perm4.contains("cbad"));
    assertTrue(perm4.contains("cbda"));
    assertTrue(perm4.contains("cdab"));
    assertTrue(perm4.contains("cdba"));
    assertTrue(perm4.contains("dabc"));
    assertTrue(perm4.contains("dacb"));
    assertTrue(perm4.contains("dbac"));
    assertTrue(perm4.contains("dbca"));
    assertTrue(perm4.contains("dcab"));
    assertTrue(perm4.contains("dcba"));


    Set<String> perm5 = permutations("abcde");
    assertEquals(5 * 4 * 3 * 2, perm5.size()); // 5!

    Set<String> perm6 = permutations("abcdef");
    assertEquals(6 * 5 * 4 * 3 * 2, perm6.size()); // 6!

    Set<String> perm7 = permutations("abcdefg");
    assertEquals(7 * 6 * 5 * 4 * 3 * 2, perm7.size()); // 7!
  }
}
