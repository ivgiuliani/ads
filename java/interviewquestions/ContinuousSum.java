package interviewquestions;

/**
 * Given an array of integers (positive and negative) find the
 * largest continuous sum
 */
public class ContinuousSum {
  public static int largestSum(int[] array) {
    assert(array.length > 0);
    int maxsum = array[0];
    int current = array[0];
    for (int i = 1; i < array.length; i++) {
      current = Math.max(current + array[i], array[i]);
      maxsum = Math.max(current, maxsum);
    }
    return maxsum;
  }

  public static void main(String[] args) {
    test(largestSum(new int[] { 1, 2, 3, 4, 5, 6 }) == 21);
    test(largestSum(new int[] { 1, 2, -3, 4, 5, 6 }) == 15);
    test(largestSum(new int[] { 100, 2, -300, 4, 5, 6 }) == 102);
    test(largestSum(new int[] { -1, -2, -3, 1, -5, -6 }) == 1);
  }

  public static void test(boolean condition) {
    // assertions are disabled by default in java, mimic their behaviour here
    if (!condition) {
      throw new AssertionError("invalid test");
    }
  }
}
