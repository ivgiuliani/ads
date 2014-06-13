package interviewquestions;

/**
 * You are given an array of integers (both positive and negative). Find the continuous
 * sequence with the largest sum. Return the sum.
 * Ex:
 * Input: {2, -8, 3, -2, 4, -10}
 * Output: 5 (i.e., {3, -2, 4} )
 */
public class LargestSubsequenceSum extends TestCase {
  public static class Subsequence {
    public int start;
    public int stop;
    public int sum;
  }

  public static Subsequence largestSubsequenceSum(int[] array) {
    Subsequence seq = new Subsequence();
    seq.start = 0;
    seq.stop = 0;
    seq.sum = Integer.MIN_VALUE;
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

    return seq;
  }

  // this version returns only the sum, without the boundaries
  public static int largestSubsequenceSum_simple(int[] array) {
    int sum = 0;
    int max = Integer.MIN_VALUE;

    for (int item : array) {
      sum += item;
      if (sum > max) {
        max = sum;
      }
      if (sum < 0) {
        sum = 0;
      }
    }

    return max;
  }

  public static void main(String[] args) {
    assertEquals(5, largestSubsequenceSum(new int[] {2, -8, 3, -2, 4, -10}).sum);
    assertEquals(2, largestSubsequenceSum(new int[] {2, -8, 3, -2, 4, -10}).start);
    assertEquals(4, largestSubsequenceSum(new int[] {2, -8, 3, -2, 4, -10}).stop);

    assertEquals(-1, largestSubsequenceSum(new int[] {-1, -2, -3, -4, -5, -6}).sum);
    assertEquals(0, largestSubsequenceSum(new int[] {-1, -2, -3, -4, -5, -6}).start);
    assertEquals(0, largestSubsequenceSum(new int[] {-1, -2, -3, -4, -5, -6}).stop);

    assertEquals(1, largestSubsequenceSum(new int[] {1, -2, -3, -4, -5, -6}).sum);
    assertEquals(0, largestSubsequenceSum(new int[] {1, -2, -3, -4, -5, -6}).start);
    assertEquals(0, largestSubsequenceSum(new int[] {1, -2, -3, -4, -5, -6}).stop);

    assertEquals(5, largestSubsequenceSum_simple(new int[] {2, -8, 3, -2, 4, -10}));
    assertEquals(-1, largestSubsequenceSum_simple(new int[] {-1, -2, -3, -4, -5, -6}));
    assertEquals(1, largestSubsequenceSum_simple(new int[] {1, -2, -3, -4, -5, -6}));
  }
}
