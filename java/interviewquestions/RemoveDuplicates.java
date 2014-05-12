package interviewquestions;

/**
 * Design an algorithm to remove the duplicate characters in a string without
 * using any additional buffer.
 */
public class RemoveDuplicates {

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
    test(removeDuplicates("aaaa").equals("a"));
    test(removeDuplicates("").equals(""));
    test(removeDuplicates("abcd").equals("abcd"));
    test(removeDuplicates("abaa").equals("ab"));
    test(removeDuplicates("abcdabcd").equals("abcd"));
    test(removeDuplicates("ababab").equals("ab"));
  }

  public static void test(boolean condition) {
    // assertions are disabled by default in java, mimic their behaviour here
    if (!condition) {
      throw new AssertionError("invalid test");
    }
  }
}
