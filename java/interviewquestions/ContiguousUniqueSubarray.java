package interviewquestions;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Given an array of n numbers with repetition of numbers. You need to find
 * the max length of continuous sub array with at max 3 unique elements.
 * For example:
 *   array: 1 2 3 1 4 3 4 1 2
 *   ans: 6    (3 1 4 3 4 1)
 *
 * Find a solution with time complexity O(n) and extra Space O(1)
 */
public class ContiguousUniqueSubarray extends TestCase {
  public static int[] find(int[] array) {
    int start = 0;
    int[] res = new int[0];

    // a dictionary of fixed size k (equals to 4 in this case) is still O(1)
    Map<Integer, Integer> count = new HashMap<Integer, Integer>(4);
    Integer tmp;
    for (int i = 0; i < array.length; i++) {
      tmp = count.get(array[i]);
      count.put(array[i], tmp == null ? 1 : tmp + 1);

      while (count.size() > 3) {
        count.put(array[start], count.get(array[start]) - 1);
        if (count.get(array[start]) == 0) {
          count.remove(array[start]);
        }
        start++;
      }

      if ((1 + i - start) > res.length) {
        res = Arrays.copyOfRange(array, start, i + 1);
      }
    }

    return res;
  }

  public static void main(String[] args) {
    assertEquals(6, find(new int[]{1, 2, 3, 1, 4, 3, 4, 1, 2}).length);
    assertEquals(find(new int[] { 1, 2, 3, 1, 4, 3, 4, 1, 2}),
                 new int[] { 3, 1, 4, 3, 4, 1 });

    assertEquals(0, find(new int[] { }).length);
    assertEquals(find(new int[] { }), new int[] { });

    assertEquals(7, find(new int[] { 4, 8, 2, 1, 3, 4, 3, 4, 1, 3, 2, 5 }).length);
    assertEquals(find(new int[] { 4, 8, 2, 1, 3, 4, 3, 4, 1, 3, 2, 5 }),
                 new int[] { 1, 3, 4, 3, 4, 1, 3 });

    assertEquals(3, find(new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9}).length);
    assertEquals(find(new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9}),
                 new int[] { 1, 2, 3 });

    assertEquals(10, find(new int[] { 1, 1, 1, 1, 2, 3, 1, 1, 1, 1}).length);
    assertEquals(find(new int[] { 1, 1, 1, 1, 2, 3, 1, 1, 1, 1 }),
                 new int[] { 1, 1, 1, 1, 2, 3, 1, 1, 1, 1 });

    assertEquals(8, find(new int[] { 1, 1, 1, 1, 1, 1, 1, 1}).length);
    assertEquals(find(new int[] { 1, 1, 1, 1, 1, 1, 1, 1 }),
                 new int[] { 1, 1, 1, 1, 1, 1, 1, 1 });
  }
}
