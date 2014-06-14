package interviewquestions;

import java.util.ArrayList;
import java.util.List;

/**
 * Given an array of numbers containing both odd and even numbers,
 * partition the array so that all the even numbers appear on the together
 * from the left and all the odd numbers appear together on the right.
 *
 * Bonus points if_
 * - you can preserve the original order of the numbers
 * - you can do that in place
 *
 * Examples:
 * 1 2 3 4 5 6 7 8 9 -> 2 4 6 8 1 3 5 7 9
 * 4 5 3 2 1 4 5 3 2 1 -> 4 2 4 2 5 3 1 5 3 1
 * 1 1 1 1 1 1 1 1 1 -> 1 1 1 1 1 1 1 1 1
 * 1 2 1 2 1 2 1 2 1 -> 2 2 2 2 1 1 1 1 1
 */
public class PartitionOddNumbers extends TestCase {
  // O(n) time + O(n) space
  public static int[] partition(int[] a) {
    List<Integer> even = new ArrayList<Integer>();
    List<Integer> odd = new ArrayList<Integer>();
    int res[] = new int[a.length];

    // O(n)
    for (int item : a) {
      if ((item % 2) == 0) {
        even.add(item);
      } else {
        odd.add(item);
      }
    }

    // O(n)
    for (int i = 0; i < even.size() + odd.size(); i++) {
      if (i < even.size()) {
        res[i] = even.get(i);
      } else {
        res[i] = odd.get(i - even.size());
      }
    }
    return res;
  }

  // this will partition the array in place, but won't retain the original
  // item's ordering (this algorithm works in O(n^2) time)
  public static void partition_inplace(int[] a) {
    int last = a.length - 1;

    for (int i = 0; i < last; i++) {
      while ((a[i] % 2) != 0 && last >= 0) {
        swap(a, i, last);
        last--;
      }
    }
  }

  private static void swap(int[] a, int i1, int i2) {
    int tmp = a[i1];
    a[i1] = a[i2];
    a[i2] = tmp;
  }

  public static void main(String[] args) {
    int[] a1 = new int [] {1, 2, 3, 4, 5, 6, 7, 8, 9};
    int[] a2 = new int [] {1, 1, 1, 1, 1, 1, 1, 1, 1};
    int[] a3 = new int [] {1, 2, 1, 2, 1, 2, 1, 2, 1};
    int[] a4 = new int [] {4, 5, 3, 2, 1, 4, 5, 3, 2, 1};
    int[] a5 = new int [] {1};

    assertEquals(new int[] {2, 4, 6, 8, 1, 3, 5, 7, 9}, partition(a1));
    assertEquals(new int[] {1, 1, 1, 1, 1, 1, 1, 1, 1}, partition(a2));
    assertEquals(new int[] {2, 2, 2, 2, 1, 1, 1, 1, 1}, partition(a3));
    assertEquals(new int[] {4, 2, 4, 2, 5, 3, 1, 5, 3, 1}, partition(a4));
    assertEquals(new int[] {1}, partition(a5));

    partition_inplace(a1);
    partition_inplace(a2);
    partition_inplace(a3);
    partition_inplace(a4);
    partition_inplace(a5);

    // we can't rely on ordering (we'd need something like guava's multiset
    // to properly test this), so just check that the array is correctly
    // partitioned
    assertTrue(isPartitioned(a1));
    assertTrue(isPartitioned(a2));
    assertTrue(isPartitioned(a3));
    assertTrue(isPartitioned(a4));
    assertTrue(isPartitioned(a5));
  }

  private static boolean isPartitioned(int[] a) {
    boolean even = true;
    int i = 0;
    while (i < a.length && even) {
      if ((a[i] %=2 ) != 0) {
        // transition point
        even = false;
      }
      i++;
    }

    while (i < a.length && !even) {
      if ((a[i] %= 2) == 0) {
        // another even item, array is not correctly partitioned
        even = true;
      }
      i++;
    }

    return !even;
  }
}
