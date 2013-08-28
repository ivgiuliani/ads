package interviewquestions;

import java.util.Arrays;

/**
 * Determine if a string has all unique characters without
 * using additional data structures
 */
public class StringUniqueCharacters {
  public static boolean unique(String string) {
    // we could use a set to store each single character and fail if the
    // character we're about to add is already in the set, leading to
    // a O(n) solution, but we're seeking an answer that doesn't use any
    // additional data structures, hence either we check every character
    // with every other character (O(n^2)) or we sort the string beforehand
    // and then scan it linearly (O(nlogn)). The "right" solution depends on
    // if we can destroy the original string or not; in this case we're going
    // with the sort.

    char[] str = string.toCharArray();
    Arrays.sort(str);

    char prev = '\0';
    for (char ch : str) {
      if (ch == prev) return false;
      prev = ch;
    }

    return true;
  }

  public static void main(String args[]) {
    test(!unique("aaaaa"));
    test(unique("abcdef"));
    test(unique("AaBbCcDd"));
    test(!unique("           "));
    test(unique("123456"));
    test(unique("abcdefABCDEF"));
    test(!unique("ABCDEFGHIJKLMNOPQRSTUVWXYZA"));
    test(unique("a"));
    test(unique(""));
  }

  public static void test(boolean condition) {
    // assertions are disabled by default in java, mimic their behaviour here
    if (!condition) {
      throw new AssertionError("invalid test");
    }
  }
}
