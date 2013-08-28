package interviewquestions.java;

import java.util.Arrays;

/**
 * Given a number, insert into the correct position in an already
 * sorted array
 */
public class IntoSortedArray {
  public static void add(int[] items, int usedCount, int item) {
    int start = 0, end = usedCount;
    int middle = 0;

    while (start <= end) {
      middle = (start + end) / 2;
      if (items[middle] == item) {
        break;
      } else if (items[middle] < item) {
        start = middle + 1;
      } else {
        // items[middle] > item
        end = middle - 1;
      }
    }

    // shift everything to the right
    for (int i = usedCount; i > middle; i--) {
      swap(items, i, i - 1);
    }
    items[middle] = item;
  }

  private static void swap(int[] items, int x, int y) {
    int tmp;
    tmp = items[x];
    items[x] = items[y];
    items[y] = tmp;
  }

  public static void main(String[] args) {
    int[] items = new int[1024];

    add(items, 0, 100);
    add(items, 1, 50);
    add(items, 2, 150);
    add(items, 3, 1);
    add(items, 4, 2);
    add(items, 5, 2);
    add(items, 6, 99);
    add(items, 7, 98);
    add(items, 8, 1);

    System.out.println(Arrays.toString(Arrays.copyOf(items, 9)));
    test(items[0] == 1);
    test(items[1] == 1);
    test(items[2] == 2);
    test(items[3] == 2);
    test(items[4] == 50);
    test(items[5] == 98);
    test(items[6] == 99);
    test(items[7] == 100);
    test(items[8] == 150);

    items = new int[1024];
    for (int i = 0; i < 100; i++) {
      add(items, i, 100 - i);
    }
    for (int i = 0; i < 100; i++) {
      test(items[i] == i + 1);
    }
  }

  public static void test(boolean condition) {
    // assertions are disabled by default in java, mimic their behaviour here
    if (!condition) {
      throw new AssertionError("invalid test");
    }
  }
}
