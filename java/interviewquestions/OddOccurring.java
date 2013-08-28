package interviewquestions.java;

import java.util.HashMap;
import java.util.Map;

/**
 * Given an integer array, one element occurs odd number of times and all
 * others have even occurrences. Find the element with odd occurrences.
 */
public class OddOccurring {
  public static Character occurring(String string) {
    Map<Character, Integer> occurrences = new HashMap<Character, Integer>();
    for (char ch : string.toCharArray()) {
      occurrences.put(ch, occurrences.get(ch) == null ? 1 : occurrences.get(ch) + 1);
    }
    for (char ch : string.toCharArray()) {
      if ((occurrences.get(ch) % 2) != 0) {
        return ch;
      }
    }
    return null;
  }

  public static void main(String[] args) {
    test(occurring("aabbccddeeffggh") == 'h');
    test(occurring("aaabbbcccdddeeeabde") == 'c');
    test(occurring("aabbcc") == null);
  }

  public static void test(boolean condition) {
    // assertions are disabled by default in java, mimic their behaviour here
    if (!condition) {
      throw new AssertionError("invalid test");
    }
  }
}
