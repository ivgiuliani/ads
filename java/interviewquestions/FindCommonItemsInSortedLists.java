package interviewquestions;

import java.util.Arrays;

/**
 * Given two arrays whose items are already sorted, find the common items
 * without using additional memory (the result in a separate list is fine,
 * it's not fine to store intermediate results in, for example, a second
 * array).
 *
 * The arrays do not necessarily have the same number of items.
 * There can be duplicate items in the arrays.
 *
 * Example:
 *
 * a: 1 3 4 5 9 12 18 25
 * b: 1 2 5 6 18 19
 * result: 1 5 18
 *
 * a: 1 2 2 2 3 3 3
 * b: 1 1 2 2 3 3
 * result: 1 2 2 3 3
 */
public class FindCommonItemsInSortedLists extends TestCase {
  public static int[] commons(int[] a1, int[] a2) {
    int[] res = new int[Math.max(a1.length, a2.length)];
    int resIdx = 0;

    int c1 = 0, c2 = 0;
    while (c1 < a1.length && c2 < a2.length) {
      if (a1[c1] == a2[c2]) {
        res[resIdx++] = a1[c1];
        c1++;
        c2++;
      } else if (a1[c1] < a2[c2]) {
        c1++;
      } else if (a1[c1] > a2[c2]) {
        c2++;
      }
    }

    return Arrays.copyOfRange(res, 0, resIdx);
  }

  public static void main(String[] args) {
    assertEquals(new int[] {},
                 commons(new int[] {}, new int[] {}));
    assertEquals(new int[] {},
                 commons(new int[] {1, 2, 3}, new int[] {4, 5, 6}));
    assertEquals(new int[] {1, 5, 18},
                 commons(new int[] {1, 3, 4, 5, 9, 12, 18, 25}, new int[] {1, 2, 5, 6, 18, 19}));
    assertEquals(new int[] {5},
                 commons(new int[] {1, 2, 3, 4, 5}, new int[] {5, 6, 7}));
    assertEquals(new int[] {1, 2, 2, 3, 3},
                 commons(new int[] {1, 2, 2, 2, 3, 3, 3}, new int[] {1, 1, 2, 2, 3, 3}));
  }
}
