package interviewquestions.java;

import java.util.HashMap;
import java.util.Map;

/**
 * Find the first non-repeated (unique) character in a given string.
 */
public class NonRepeated {
  public static Character nonRepeated(String string) {
    Map<Character, Integer> repetitions = new HashMap<Character, Integer>();
    for (char ch : string.toCharArray()) {
      repetitions.put(ch, repetitions.get(ch) == null ? 1 : repetitions.get(ch) + 1);
    }

    for (char ch : string.toCharArray()) {
      if (repetitions.get(ch) == 1) return ch;
    }

    return null;
  }

  public static void main(String[] args) {
    test(nonRepeated("abcdefghijklmnopqrstuvwxyz") == 'a');
    test(nonRepeated("aabbccddeeffggh") == 'h');
    test(nonRepeated("abcdefgabcdefgzabcdefgabcdefg") == 'z');
    test(nonRepeated("aabbccddee") == null);
  }

  public static void test(boolean condition) {
    // assertions are disabled by default in java, mimic their behaviour here
    if (!condition) {
      throw new AssertionError("invalid test");
    }
  }
}
