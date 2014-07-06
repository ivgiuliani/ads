package interviewquestions;

/**
 * Design an algorithm to remove the duplicate characters in a string without
 * using any additional buffer.
 */
public class RemoveDuplicates extends TestCase {
  public static String removeDuplicates(String string) {
    char[] ch = string.toCharArray();
    if (ch.length <= 1) {
      return string;
    }

    int length = string.length();
    int tail = 1;

    for (int i = 1; i < length; i++) {
      int j;
      boolean duplicate = false;
      for (j = 0; j < i && !duplicate; j++) {
        if (ch[i] == ch[j]) {
          duplicate = true;
        }
      }

      ch[i] = ch[tail];
      if (!duplicate) {
        tail++;
      }
    }

    return new String(ch, 0, tail);
  }

  public static void main(String[] args) {
    assertEquals("a", removeDuplicates("aaaa"));
    assertEquals("", removeDuplicates(""));
    assertEquals("abcd", removeDuplicates("abcd"));
    assertEquals("ab", removeDuplicates("abaa"));
    assertEquals("abcd", removeDuplicates("abcdabcd"));
    assertEquals("ab", removeDuplicates("ababab"));
  }
}
