package interviewquestions;

/**
 * You are given an array of integers (both positive and negative). Find the continuous
 * sequence with the largest sum. Return the sum.
 * Ex:
 * Input: {2, -8, 3, -2, 4, -10}
 * Output: 5 (i.e., {3, -2, 4} )
 */
public class LargestSubsequenceSum {
  public static class Subsequence {
    public int start;
    public int stop;
    public int sum;
  }

  public static Subsequence largestSubsequenceSum(int[] array) {
    Subsequence seq = new Subsequence();
    seq.start = 0;
    seq.stop = 0;
    seq.sum = 0;
    int currentSum = 0;
    int currentStart = 0;

    for (int i = 0; i < array.length; i++) {
      currentSum += array[i];
      if (currentSum > seq.sum) {
        seq.sum = currentSum;
        seq.start = currentStart;
        seq.stop = i;
      }
      if (currentSum < 0) {
        currentSum = 0;
        currentStart = i + 1;
      }
    }

    // if there's no largest sum, the answer is open to interpretation.
    // I decided to just return an invalid interval but maybe an exception
    // should be thrown?
    if (seq.sum == 0) {
      seq.start = -1;
      seq.stop = -1;
    }
    return seq;
  }

  public static void main(String[] args) {
    test(largestSubsequenceSum(new int[] {2, -8, 3, -2, 4, -10}).sum == 5);
    test(largestSubsequenceSum(new int[] {2, -8, 3, -2, 4, -10}).start == 2);
    test(largestSubsequenceSum(new int[] {2, -8, 3, -2, 4, -10}).stop == 4);

    test(largestSubsequenceSum(new int[] {-1, -2, -3, -4, -5, -6}).sum == 0);
    test(largestSubsequenceSum(new int[] {-1, -2, -3, -4, -5, -6}).start == -1);
    test(largestSubsequenceSum(new int[] {-1, -2, -3, -4, -5, -6}).stop == -1);

    test(largestSubsequenceSum(new int[] {1, -2, -3, -4, -5, -6}).sum == 1);
    test(largestSubsequenceSum(new int[] {1, -2, -3, -4, -5, -6}).start == 0);
    test(largestSubsequenceSum(new int[] {1, -2, -3, -4, -5, -6}).stop == 0);
  }

  public static void test(boolean condition) {
    // assertions are disabled by default in java, mimic their behaviour here
    if (!condition) {
      throw new AssertionError("invalid test");
    }
  }
}
