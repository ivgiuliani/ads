package interviewquestions;

/**
 * You are given two Strings: init and goal. Both init and goal contain lowercase
 * letters only. Additionally, init does not contain the character 'z'.
 * Your goal is to transform init into goal. The only operation you are allowed
 * to do is to insert the character 'z' anywhere into init. You can repeat the
 * operation as many times as you want, and each time you can choose any position
 * where to insert the 'z'.
 * For example, if init="fox", you can transform it to "fzox" in one operation.
 * Alternately, you can transform "fox" into "zzzfoxzzz" in six operations.
 * It is not possible to transform "fox" into any of the strings "fx", "foz",
 * "fxo", "foxy".
 * Return "Yes" (quotes for clarity) if it is possible to transform init into goal.
 * Otherwise, return "No".
 *
 * SRM587/DIV2 -- 250
 */
public class InsertZ extends TestCase {
  public static final String YES = "Yes";
  public static final String NO = "No";

  public String canTransform(String init, String goal) {
    int min = Integer.MAX_VALUE;
    int dist;
    String newInit = init;
    String tmp;
    while (init.length() < goal.length()) {
      for (int i = 0; i <= init.length(); i++) {
        tmp = insertAt(init, 'z', i);
        dist = ldistance(tmp, goal);
        if (dist < min) {
          newInit = tmp;
          min = dist;
        }
      }

      init = newInit;
    }

    if (init.equals(goal)) return YES;
    return NO;
  }

  private static String insertAt(String string, Character ch, int pos) {
    return string.substring(0, pos) + ch + string.substring(pos, string.length());
  }

  /* Any other edit distance will work, I choose the levenshtein distance
   * because that was the only one I had written already
   */
  public int ldistance(CharSequence str1,
                       CharSequence str2) {
    int[][] distance = new int[str1.length() + 1][str2.length() + 1];

    for (int i = 0; i <= str1.length(); i++) distance[i][0] = i;
    for (int j = 1; j <= str2.length(); j++) distance[0][j] = j;

    for (int i = 1; i <= str1.length(); i++) {
      for (int j = 1; j <= str2.length(); j++) {
        distance[i][j] = min3(distance[i - 1][j] + 1,
            distance[i][j - 1] + 1,
            distance[i - 1][j - 1] + ((str1.charAt(i - 1) == str2.charAt(j - 1)) ? 0 : 1));
      }
    }

    return distance[str1.length()][str2.length()];
  }

  private static int min3(int a, int b, int c) {
    return Math.min(Math.min(a, b), c);
  }

  public static void main(String[] args) {
    InsertZ z = new InsertZ();

    assertEquals(InsertZ.YES, z.canTransform("fox", "fozx"));
    assertEquals(InsertZ.YES, z.canTransform("fox", "zfzoxzz"));
    assertEquals(InsertZ.YES, z.canTransform("foon", "foon"));
    assertEquals(InsertZ.NO, z.canTransform("topcoder", "zopzoder"));
    assertEquals(InsertZ.NO, z.canTransform("aaaaaaaaaa", "a"));
    assertEquals(InsertZ.YES, z.canTransform("mvixrdnrpxowkasufnvxaq", "mvizzxzzzrdzznzrpxozzwzzkazzzszzuzzfnvxzzzazzq"));
    assertEquals(InsertZ.NO, z.canTransform("opdlfmvuicjsierjowdvnx", "zzopzdlfmvzuicjzzsizzeijzowvznxzz"));
    assertEquals(InsertZ.YES, z.canTransform("", ""));
    assertEquals(InsertZ.YES, z.canTransform("", "zzzzzz"));
  }
}
