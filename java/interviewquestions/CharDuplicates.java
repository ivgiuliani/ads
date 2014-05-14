package interviewquestions;

import java.util.HashSet;
import java.util.Set;

/**
 * Remove duplicate characters in a given string keeping only the first occurrence.
 * For example, if the input is "tree traversal" the output will be "tre avsl".
 */
public class CharDuplicates extends TestCase {
  public static String remdup(String input) {
    Set<Character> characterSet = new HashSet<Character>();
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < input.length(); i++) {
      char ch = input.charAt(i);
      if (!characterSet.contains(ch)) {
        characterSet.add(ch);
        builder.append(ch);
      }
    }
    return builder.toString();
  }

  /*
   * The same as above, but without using additional data structures
   * We iterate for every character over every other previous character
   * to check if we already encountered it, leading to an O(n^2) solution
   */
  public static String remdupNoDS(String input) {
    StringBuilder builder = new StringBuilder();
    boolean encountered;
    char ch;

    for (int i = 0; i < input.length(); i++) {
      ch = input.charAt(i);
      encountered = false;

      for (int j = 0; j < i && !encountered; j++) {
        if (ch == input.charAt(j)) {
          encountered = true;
        }
      }

      if (!encountered) builder.append(ch);
    }

    return builder.toString();
  }

  public static void main(String[] args) {
    assertEquals("string", remdup("stringstring"));
    assertEquals("tre avsl", remdup("tree traversal"));
    assertEquals("no duplicates", remdup("no duplicates"));
    assertEquals("123456", remdup("123456123451234"));
    assertEquals("", remdup(""));

    assertEquals("string", remdupNoDS("stringstring"));
    assertEquals("tre avsl", remdupNoDS("tree traversal"));
    assertEquals("no duplicates", remdupNoDS("no duplicates"));
    assertEquals("123456", remdupNoDS("123456123451234"));
    assertEquals("", remdupNoDS(""));
  }
}
