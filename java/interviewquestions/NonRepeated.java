package interviewquestions;

import java.util.HashMap;
import java.util.Map;

/**
 * Find the first non-repeated (unique) character in a given string.
 */
public class NonRepeated extends TestCase {
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
    assertEquals('a', (char) nonRepeated("abcdefghijklmnopqrstuvwxyz"));
    assertEquals('h', (char) nonRepeated("aabbccddeeffggh"));
    assertEquals('z', (char) nonRepeated("abcdefgabcdefgzabcdefgabcdefg"));
    assertNull(nonRepeated("aabbccddee"));
  }
}
