package interviewquestions;

/**
 * Given a string 'S' and a character 't'. Print out the position of the rightmost
 * occurrence of 't' (case matters) in 'S' or -1 if there is none.
 */
public class RightMostCharacter {
  public static int rightmost(String string, Character ch) {
    for (int idx = string.length() - 1; idx >= 0; --idx) {
      if (string.charAt(idx) == ch) return idx;
    }
    return -1;
  }

  public static void main(String[] args) {
    test(rightmost("Hello World", 'r') == 8);
    test(rightmost("Hello World", 'd') == 10);
    test(rightmost("Hello WOrld", 'o') == 4);
    test(rightmost("Hello WOrld", 'O') == 7);
    test(rightmost("aaaaaaaaaaa", 'a') == 10);
    test(rightmost("", 'a') == -1);
    test(rightmost("How to learn to Cook", 'C') == 16);
    test(rightmost("What is your name", 'm') == 15);
    test(rightmost("lKHFLOpCmu0bxE0AooKQ8gYcrvf7Go22pMVKjFCc7DU9qIpcd55 Px00SdUCV4fzSq", 'W') == -1);
  }

  public static void test(boolean condition) {
    // assertions are disabled by default in java, mimic their behaviour here
    if (!condition) {
      throw new AssertionError("invalid test");
    }
  }
}
