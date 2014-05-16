package interviewquestions;

/**
 * Given an array of integers (positive and negative) find the
 * largest continuous sum
 */
public class ContinuousSum extends TestCase {
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
    assertEquals(21, largestSum(new int[] { 1, 2, 3, 4, 5, 6 }));
    assertEquals(15, largestSum(new int[] { 1, 2, -3, 4, 5, 6 }));
    assertEquals(102, largestSum(new int[] { 100, 2, -300, 4, 5, 6 }));
    assertEquals(1, largestSum(new int[] { -1, -2, -3, 1, -5, -6 }));
  }
}
