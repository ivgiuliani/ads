package interviewquestions;

import java.util.HashSet;
import java.util.Set;

/**
 * Remove duplicate characters in a given string keeping only the first occurrence.
 * For example, if the input is "tree traversal" the output will be "tre avsl".
 */
public class CharDuplicates {
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
    test(remdup("stringstring").equals("string"));
    test(remdup("tree traversal").equals("tre avsl"));
    test(remdup("no duplicates").equals("no duplicates"));
    test(remdup("123456123451234").equals("123456"));
    test(remdup("").equals(""));

    test(remdupNoDS("stringstring").equals("string"));
    test(remdupNoDS("tree traversal").equals("tre avsl"));
    test(remdupNoDS("no duplicates").equals("no duplicates"));
    test(remdupNoDS("123456123451234").equals("123456"));
    test(remdupNoDS("").equals(""));
  }

  public static void test(boolean condition) {
    // assertions are disabled by default in java, mimic their behaviour here
    if (!condition) {
      throw new AssertionError("invalid test");
    }
  }
}
