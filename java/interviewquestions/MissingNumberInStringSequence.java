package interviewquestions;

/**
 * Given a string of numbers in sequence order, find the missing number.
 * Note:
 *   1) range is not given
 *   2) only one number is missing
 *   3) the sequence is contiguous if not for the missing number
 *   4) a number can only be missing in the middle of the sequence and you're
 *      guaranteed to have at least two numbers
 *   5) the sequence is guaranteed to be valid
 */
public class MissingNumberInStringSequence extends TestCase {
  public static int findMissing(String str) {
    for (int length = 1; length <= str.length(); length++) {
      String startString = str.substring(0, length);
      int startNum = Integer.valueOf(startString);
      StopPoint pt = getStopPoint(str, startNum); // 123567 -> (4, 3) (index of next item in sequence after the missing item)
      if (isContiguous(str, pt.idx, pt.missing + 1)) {
        return pt.missing;
      }
    }
    return -1;
  }

  private static class StopPoint {
    int missing;
    int idx;
  }

  private static StopPoint getStopPoint(String str, int startNum) {
    int num = startNum;
    int start = 0;
    String n = String.valueOf(num);

    while ((start + n.length()) < str.length() && Integer.valueOf(str.substring(start, start + n.length())) == num) {
      start += n.length();
      num++;
      n = String.valueOf(num);
    }

    StopPoint pt = new StopPoint();
    pt.missing = num;
    pt.idx = start;
    return pt;
  }

  private static boolean isContiguous(String str, int startPos, int nextNum) {
    for (int i = startPos; i < str.length(); i += String.valueOf(nextNum).length()) {
      String next = String.valueOf(nextNum);

      String val = str.substring(i, i + next.length());
      if (val.compareTo(next) != 0) return false;
      nextNum++;
    }
    return true;
  }

  public static void main(String[] args) {
    assertEquals(4, findMissing("123567"));
    assertEquals(15, findMissing("101112131416"));
    assertEquals(100, findMissing("9899101102"));
    assertEquals(10, findMissing("911"));
  }
}
