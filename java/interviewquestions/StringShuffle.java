package interviewquestions;

/**
 * We are given 3 strings: str1, str2, and str3. Str3 is said to be a shuffle of str1 and str2
 * if it can be formed by interleaving the characters of str1 and str2 in a way that maintains
 * the left to right ordering of the characters from each string. For example, given str1="abc"
 * and str2="def", str3="dabecf" is a valid shuffle since it preserves the character ordering
 * of the two strings. So, given these 3 strings write a function that detects whether str3
 * is a valid shuffle of str1 and str2.
 */
public class StringShuffle {
  public static boolean validShuffling(String str1, String str2, String str3) {
    int s1 = 0, s2 = 0, s3 = 0;

    int totallength = str1.length() + str2.length();
    if (totallength != str3.length())
      return false;

    while (s3 < totallength) {
      if (s1 < str1.length() && str3.charAt(s3) == str1.charAt(s1)) {
        s3++;
        s1++;
      } else if (s2 < str2.length() && str3.charAt(s3) == str2.charAt(s2)) {
        s3++;
        s2++;
      } else return false;
    }

    return true;
  }

  public static void main(String[] args) {
    test(validShuffling("abc", "def", "abcdef"));
    test(validShuffling("abc", "def", "abdefc"));
    test(validShuffling("abc", "def", "defabc"));
    test(validShuffling("abc", "def", "adbecf"));
    test(!validShuffling("abc", "def", "adcdfe"));
    test(!validShuffling("abc", "def", "fedcba"));
    test(!validShuffling("abc", "def", "acbdef"));
    test(validShuffling("aaa", "aaa", "aaaaaa"));
    test(validShuffling("aaa", "bbb", "abaabb"));
    test(validShuffling("abc", "abc", "aabbcc"));
  }

  public static void test(boolean condition) {
    // assertions are disabled by default in java, mimic their behaviour here
    if (!condition) {
      throw new AssertionError("invalid test");
    }
  }
}
