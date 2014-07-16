package interviewquestions;

/**
 * Given a string 'S' and a character 't'. Print out the position of the rightmost
 * occurrence of 't' (case matters) in 'S' or -1 if there is none.
 */
public class RightMostCharacter extends TestCase {
  public static int rightmost(String string, Character ch) {
    for (int idx = string.length() - 1; idx >= 0; --idx) {
      if (string.charAt(idx) == ch) return idx;
    }
    return -1;
  }

  public static void main(String[] args) {
    assertEquals(8, rightmost("Hello World", 'r'));
    assertEquals(10, rightmost("Hello World", 'd'));
    assertEquals(4, rightmost("Hello WOrld", 'o'));
    assertEquals(7, rightmost("Hello WOrld", 'O'));
    assertEquals(10, rightmost("aaaaaaaaaaa", 'a'));
    assertEquals(-1, rightmost("", 'a'));
    assertEquals(16, rightmost("How to learn to Cook", 'C'));
    assertEquals(15, rightmost("What is your name", 'm'));
    assertEquals(-1, rightmost("lKHFLOpCmu0bxE0AooKQ8gYcrvf7Go22pMVKjFCc7DU9qIpcd55 Px00SdUCV4fzSq", 'W'));
  }
}
